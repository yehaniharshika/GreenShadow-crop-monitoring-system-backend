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

    /*@Override
    public FieldDTO saveField(FieldDTO fieldDTO) {
        // Convert DTO to Entity
        FieldEntity saveField = fieldMapping.toFieldEntity(fieldDTO);

        try {
            // Fetch staff entities from database and associate with the field
            List<StaffEntity> staffEntities = new ArrayList<>();
            if (fieldDTO.getStaffIds() != null) {
                for (String staffId : fieldDTO.getStaffIds()) {
                    StaffEntity staff = staffDAO.findById(staffId)
                            .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + staffId));
                    staffEntities.add(staff);
                }
            }

            // Set the staff entities list in FieldEntity
            saveField.setStaff(staffEntities);

            // Persist FieldEntity (will also save relationships in field_staff_details)
            FieldEntity savedFieldEntity = fieldDAO.save(saveField);

            // Return the saved field as DTO
            return fieldMapping.toFieldDTO(savedFieldEntity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save field with staff details", e);
        }
    }*/


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
        return null;
    }
}
