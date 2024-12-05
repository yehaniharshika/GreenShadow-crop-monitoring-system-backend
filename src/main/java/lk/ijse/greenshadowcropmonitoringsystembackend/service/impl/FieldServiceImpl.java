package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.customStatusCodes.SelectedCustomErrorStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.FieldDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.StaffDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.FieldStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.FieldDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.StaffDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.FieldEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.StaffEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.FieldNotFoundException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.FieldService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldDAO fieldDAO;

    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private Mapping fieldMapping;

    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public void saveField(FieldDTO fieldDTO) {
        //Convert DTO to entity
        FieldEntity fieldEntity = fieldMapping.toFieldEntity(fieldDTO);

        List<StaffEntity> staffEntities = fieldDTO.getStaff().stream()
                .map(staffId -> staffDAO.getReferenceById(staffId)) // uses a proxy to avoid unsaved entities
                .collect(Collectors.toList());
        fieldEntity.setStaff(staffEntities);

        // Save fieldEntity which now has managed StaffEntity references
        fieldDAO.save(fieldEntity);

    }


    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    @Override
    public List<FieldDTO> getAllFields() {
        List<FieldEntity> allFields = fieldDAO.findAll();
        return fieldMapping.asFieldDTOList(allFields);
    }

    @Override
    public FieldStatus getField(String fieldCode) {
        if (fieldDAO.existsById(fieldCode)){
            FieldEntity selectedField = fieldDAO.getReferenceById(fieldCode);
            return fieldMapping.toFieldDTO(selectedField);

        }else {
            return new SelectedCustomErrorStatus(2,"FieldId: "+fieldCode+" field not found");
        }
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    @Override
    public void updateField(String fieldCode, FieldDTO fieldDTO) {
        // Find the existing field by fieldCode
        Optional<FieldEntity> findField = fieldDAO.findById(fieldCode);

        if (!findField.isPresent()) {
            throw new FieldNotFoundException("field not found");
        } else {

            findField.get().setFieldName(fieldDTO.getFieldName());
            findField.get().setExtentSize(fieldDTO.getExtentSize());
            findField.get().setFieldLocation(fieldDTO.getFieldLocation());
            findField.get().setFieldImage1(fieldDTO.getFieldImage1());
            findField.get().setFieldImage2(fieldDTO.getFieldImage2());

            List<StaffEntity> staffEntities = fieldDTO.getStaff().stream()
                    .map(staffId -> staffDAO.getReferenceById(staffId)) // uses a proxy to avoid unsaved entities
                    .collect(Collectors.toList());
            findField.get().setStaff(staffEntities);

        }
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    @Override
    public void deleteField(String fieldCode) {
        Optional<FieldEntity> existedField = fieldDAO.findById(fieldCode);
        if (!existedField.isPresent()){
            throw new FieldNotFoundException("FieldCode: "+fieldCode+" Field not found");
        }else {
            fieldDAO.deleteById(fieldCode);
        }
    }

    @Override
    public List<StaffDTO> getStaffIdsByFieldCode(String fieldCode) {
        FieldEntity field = fieldDAO.findById(fieldCode)
                .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + fieldCode));

        return fieldMapping.asStaffDTOList(new ArrayList<>(field.getStaff()));
    }

    @Override
    public String generateNextFieldCode() {
        try {
            String lastFieldCode = fieldDAO.getLastFieldCode();
            if (lastFieldCode != null){
                int nextFieldCode = Integer.parseInt(lastFieldCode.split("-")[1]) + 1;
                return String.format("F00-%03d",nextFieldCode);
            }else {
                return "F00-001";
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to generate next field code",e);
        }

    }
}
