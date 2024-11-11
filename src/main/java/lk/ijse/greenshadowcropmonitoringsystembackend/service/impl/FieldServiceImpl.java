package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.customStatusCodes.SelectedCustomErrorStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.FieldDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.FieldStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.FieldDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.FieldEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.FieldNotFoundException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.FieldService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldDAO fieldDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveField(FieldDTO fieldDTO) {
        FieldEntity saveField = fieldDAO.save(mapping.toFieldEntity(fieldDTO));

        if (saveField == null){
            throw  new DataPersistException("Field not found");
        }

    }

    @Override
    public List<FieldDTO> getAllFields() {
        List<FieldEntity> allFields = fieldDAO.findAll();
        return mapping.asFieldDTOList(allFields);
    }

    @Override
    public FieldStatus getField(String fieldCode) {
        if (fieldDAO.existsById(fieldCode)){
            FieldEntity selectedField = fieldDAO.getReferenceById(fieldCode);
            return mapping.toFieldDTO(selectedField);
        }else {
            return new SelectedCustomErrorStatus(2,"FieldId: "+fieldCode+" field not found");
        }
    }

    @Override
    public void updateField(String fieldCode, FieldDTO fieldDTO) {
        //find field = tmpField
        Optional<FieldEntity> tmpField = fieldDAO.findById(fieldCode);

        if (tmpField.isPresent()){
            tmpField.get().setFieldName(fieldDTO.getFieldName());
            tmpField.get().setExtentSize(fieldDTO.getExtentSize());
            tmpField.get().setFieldLocation(fieldDTO.getFieldLocation());
            tmpField.get().setFieldImage1(fieldDTO.getFieldImage1());
            tmpField.get().setFieldImage2(fieldDTO.getFieldImage2());
        }
    }

    @Override
    public void deleteField(String fieldCode) {
        Optional<FieldEntity> existedField = fieldDAO.findById(fieldCode);
        if (!existedField.isPresent()){
            throw new FieldNotFoundException("FieldCode: "+fieldCode+" Field not fount");
        }else {
            fieldDAO.deleteById(fieldCode);
        }
    }
}
