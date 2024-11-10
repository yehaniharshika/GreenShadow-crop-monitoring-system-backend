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
        return extractClaim(token, Claims::getSubject);
    }

    //actual process
    private <T> T extractClaim(String token, Function<Claims,T> claimResolve){
        final Claims claims = getAllClaims(token);
        return claimResolve.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] decode = Decoders.BASE64.decode(jwtKey);
        return Keys.hmacShaKeyFor(decode);
    }

    //generate token process
    @Override
    public String generateToken(UserDetails userDetails) {
        return ToGenerateToken(new HashMap<>(),userDetails);
    }

    private String ToGenerateToken(Map<String,Object> extractClaims, UserDetails userDetails){
        extractClaims.put("role",userDetails.getAuthorities());
        Date now = new Date();
        Date expire = new Date(now.getTime() + 1000 * 600);

        return Jwts.builder().setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public String refreshToken(UserDetails userDetails) {
        return null;
    }
}
