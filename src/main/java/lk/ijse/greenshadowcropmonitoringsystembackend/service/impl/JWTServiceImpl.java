package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
    @Value("${spring.jwtKey}")
    private String jwtKey;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    //generate token
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }

    @Override
    public String refreshToken(UserDetails userDetails) {
        return refreshToken(new HashMap<>(),userDetails);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        var username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);

    }

    //actual process
    private <T> T extractClaim(String token, Function<Claims,T> claimResolve) {
        final Claims claims = getAllClaims(token);
        return claimResolve.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey(){
        byte[] decode = Decoders.BASE64.decode(jwtKey);
        return Keys.hmacShaKeyFor(decode);
    }

    private String generateToken(Map<String,Object> extractClaims, UserDetails userDetails){
        extractClaims.put("role",userDetails.getAuthorities());
        Date now = new Date();
        //set expiration to 30 days (1 month)
        Date expire = new Date(now.getTime() + 1000L * 60 * 60 * 24 * 30);

        return Jwts.builder().setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

    }

    private String refreshToken(Map<String,Object> extractClaims,UserDetails userDetails){
        extractClaims.put("role",userDetails.getAuthorities());
        Date now = new Date();
        Date expire = new Date(now.getTime() + 1000 * 600);
        Date refreshExpire = new Date(now.getTime() + 1000L * 60 * 60 * 24 * 30);

        return Jwts.builder().setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setExpiration(refreshExpire)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


}
