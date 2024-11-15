package lk.ijse.greenshadowcropmonitoringsystembackend.service;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.FieldStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.FieldDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.StaffDTO;

import java.util.List;

public interface FieldService {
    FieldDTO saveField(FieldDTO fieldDTO);
    List<FieldDTO> getAllFields();
    FieldStatus getField(String fieldCode);
    void updateField(String fieldCode,FieldDTO fieldDTO);
    void deleteField(String fieldCode);
    List<StaffDTO> getStaffIdsByFieldCode(String fieldCode);
}
