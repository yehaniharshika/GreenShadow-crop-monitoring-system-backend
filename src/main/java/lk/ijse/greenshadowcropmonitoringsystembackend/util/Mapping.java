package lk.ijse.greenshadowcropmonitoringsystembackend.util;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.FieldDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.UserDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.FieldEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    //For User-mapping
    public UserEntity toUserEntity(UserDTO userDTO){
        return modelMapper.map(userDTO,UserEntity.class);
    }

    public UserDTO toUserDTO(UserEntity userEntity){
        return modelMapper.map(userEntity,UserDTO.class);
    }
    public List<UserDTO> asUserDTOList(List<UserEntity> userEntities){
        return modelMapper.map(userEntities,List.class);
    }

    //For Field-mapping
    public FieldEntity toFieldEntity(FieldDTO fieldDTO){
        return modelMapper.map(fieldDTO,FieldEntity.class);
    }

    public FieldDTO toFieldDTO(FieldEntity fieldEntity){
        return modelMapper.map(fieldEntity,FieldDTO.class);
    }

    public List<FieldDTO> asFieldDTOList(List<FieldEntity> fieldEntities){
        return modelMapper.map(fieldEntities,List.class);
    }
}
