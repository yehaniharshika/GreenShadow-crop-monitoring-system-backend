package lk.ijse.greenshadowcropmonitoringsystembackend.util;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.*;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
        return modelMapper.map(fieldEntities,new TypeToken<List<FieldDTO>>() {}.getType());
    }

    //For Staff-mapping
    public StaffEntity toStaffEntity(StaffDTO staffDTO){
        return modelMapper.map(staffDTO,StaffEntity.class);
    }

    public StaffDTO toStaffDTO(StaffEntity staffEntity){
        return modelMapper.map(staffEntity,StaffDTO.class);
    }

    public List<StaffDTO> asStaffDTOList(List<StaffEntity> staffEntities){
        return modelMapper.map(staffEntities,new TypeToken<List<StaffDTO>>() {}.getType());
    }


    //For Crop-mapping
    public CropEntity toCropEntity(CropDTO cropDTO){
        return modelMapper.map(cropDTO,CropEntity.class);
    }

    public CropDTO toCropDTO(CropEntity cropEntity){
        return modelMapper.map(cropEntity,CropDTO.class);
    }

    public List<CropDTO> asCropDTOList(List<CropEntity> cropEntities){
        return modelMapper.map(cropEntities,new TypeToken<List<CropDTO>>() {}.getType());
    }

    //For Log-mapping
    public LogsEntity toLogEntity(LogDTO logDTO){
        return modelMapper.map(logDTO,LogsEntity.class);
    }

    public LogDTO toLogDTO(LogsEntity logsEntity){
        return modelMapper.map(logsEntity,LogDTO.class);
    }

    public List<LogDTO> asLogDTOList(List<LogsEntity> logsEntities){
        return modelMapper.map(logsEntities,new TypeToken<List<LogDTO>>() {}.getType());
    }

    //For Vehicle-mapping
    public VehicleEntity toVehicleEntity(VehicleDTO vehicleDTO){
        return modelMapper.map(vehicleDTO,VehicleEntity.class);
    }

    public VehicleDTO toVehicleDTO(VehicleEntity vehicleEntity){
        return modelMapper.map(vehicleEntity,VehicleDTO.class);
    }

    public List<VehicleDTO> asVehicleDTOList(List<VehicleEntity> vehicleEntities){
        return modelMapper.map(vehicleEntities,new TypeToken<List<VehicleDTO>>() {}.getType());
    }



}
