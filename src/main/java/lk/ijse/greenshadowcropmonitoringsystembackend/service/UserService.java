package lk.ijse.greenshadowcropmonitoringsystembackend.service;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.UserStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserStatus getUser(String userId);
    void updateUser(String userId,UserDTO userDTO);

    //To Authorization part
    UserDetailsService userDetailsService();
}
