package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.dao.UserDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.UserStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.UserDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.UserEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.UserService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Mapping mapping;
    @Override
    public void saveUser(UserDTO userDTO) {
        UserEntity saveUser = userDAO.save(mapping.toUserEntity(userDTO));

        if (saveUser == null){
            throw  new DataPersistException("User not saved");
        }

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
