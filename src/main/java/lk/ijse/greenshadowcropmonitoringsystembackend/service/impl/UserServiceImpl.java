package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.customStatusCodes.SelectedCustomErrorStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.UserDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.UserStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.UserDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.UserEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.UserService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

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
        List<UserEntity> allUsers = userDAO.findAll();
        return mapping.asUserDTOList(allUsers);
    }

    @Override
    public UserStatus getUser(String userId) {
        if (userDAO.existsById(userId)){
            UserEntity selectedUser = userDAO.getReferenceById(userId);
            return mapping.toUserDTO(selectedUser);
        }else {
            //custom error status
            return new SelectedCustomErrorStatus(2,"User- "+userId+" not found");
        }
    }

    @Override
    public void updateUser(String userId, UserDTO userDTO) {
        Optional<UserEntity> tmpUser = userDAO.findById(userId);

        if (tmpUser.isPresent()){
            tmpUser.get().setName(userDTO.getUserId());
            tmpUser.get().setEmail(userDTO.getEmail());
            tmpUser.get().setPassword(userDTO.getPassword());
            tmpUser.get().setRole(userDTO.getRole());
        }
    }

    /*@Override
    public UserDetailsService userDetailsService() {
        return null;
    }*/
}
