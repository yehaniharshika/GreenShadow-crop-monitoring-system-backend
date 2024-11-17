package lk.ijse.greenshadowcropmonitoringsystembackend.controller;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.UserDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.Role;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.secure.JWTAuthResponse;
import lk.ijse.greenshadowcropmonitoringsystembackend.secure.SignIn;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.AuthService;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.UserService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    @PostMapping(value = "signup", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> saveUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO buildUserDTO = new UserDTO();
            buildUserDTO.setName(userDTO.getName());
            buildUserDTO.setEmail(userDTO.getEmail());
            buildUserDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            buildUserDTO.setRole(Role.valueOf(String.valueOf(userDTO.getRole())));

            userService.saveUser(buildUserDTO);
            return ResponseEntity.ok(authService.signUp(buildUserDTO));
        }catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(value = "signin",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignIn signIn){
        return ResponseEntity.ok(authService.signIn(signIn));
    }

    @PostMapping("refresh")
    public ResponseEntity<JWTAuthResponse> signIn(@RequestParam ("existingToken") String existingToken) {
        return ResponseEntity.ok(authService.refreshToken(existingToken));
    }
}
