package lk.ijse.greenshadowcropmonitoringsystembackend.service;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.FieldStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.FieldDTO;

import java.util.List;

public interface FieldService {
    void saveField(FieldDTO fieldDTO);
    List<FieldDTO> getAllFields();
    FieldStatus getField(String fieldCode);
    void updateField(String fieldCode,FieldDTO fieldDTO);
    void deleteField(String fieldCode);
}
