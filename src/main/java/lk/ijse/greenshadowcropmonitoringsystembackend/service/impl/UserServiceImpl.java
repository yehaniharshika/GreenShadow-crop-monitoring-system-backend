package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.customStatusCodes.SelectedCustomErrorStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.UserDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.UserStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.UserDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.UserEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.UserNotFoundException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.UserService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Mapping userMapping;
    @Override
    public void saveUser(UserDTO userDTO) {
        UserEntity saveUser = userDAO.save(userMapping.toUserEntity(userDTO));

        if (saveUser == null){
            throw  new DataPersistException("User not saved");
        }

    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> allUsers = userDAO.findAll();
        return userMapping.asUserDTOList(allUsers);
    }

    @Override
    public UserStatus getUser(String email) {
        if (userDAO.existsById(email)){
            UserEntity selectedUser = userDAO.getReferenceById(email);
            return userMapping.toUserDTO(selectedUser);
        }else {
            //custom error status
            return new SelectedCustomErrorStatus(2,"User not found");
        }
    }

    @Override
    public void updateUser(String email, UserDTO userDTO) {
        Optional<UserEntity> tmpUser = userDAO.findById(email);

        if (tmpUser.isPresent()){
            tmpUser.get().setName(userDTO.getName());
            tmpUser.get().setEmail(userDTO.getEmail());
            tmpUser.get().setPassword(userDTO.getPassword());
            tmpUser.get().setRole(userDTO.getRole());
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userDAO.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
