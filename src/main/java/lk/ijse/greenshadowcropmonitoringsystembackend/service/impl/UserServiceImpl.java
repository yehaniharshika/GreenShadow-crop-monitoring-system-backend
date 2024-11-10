package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.UserStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.UserDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Override
    public void saveUser(UserDTO userDTO) {

    }

    @Override
    public List<UserDTO> getAllUsers() {
        return null;
    }

    @Override
    public UserStatus getUser(String userId) {
        return null;
    }

    @Override
    public void updateUser(String userId, UserDTO userDTO) {

    }

    @Override
    public UserDetailsService userDetailsService() {
        return null;
    }
}
