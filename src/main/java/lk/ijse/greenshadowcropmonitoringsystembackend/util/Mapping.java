package lk.ijse.greenshadowcropmonitoringsystembackend.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    /*//or User Mapping
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

    //or Note Mapping
    public NoteEntity toNoteEntity(NoteDTO noteDTO){
        return modelMapper.map(noteDTO, NoteEntity.class);
    }

    public NoteDTO toNoteDTO(NoteEntity noteEntity){
        return modelMapper.map(noteEntity,NoteDTO.class);
    }
    public List<NoteDTO> asNoteDTOList(List<NoteEntity> noteEntities){
        return modelMapper.map(noteEntities,List.class);
    }
*/
}
