package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.customStatusCodes.SelectedCustomErrorStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.EquipmentDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.FieldDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.StaffDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.EquipmentStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.EquipmentDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.EquipmentEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.FieldEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.StaffEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.EquipmentNotFoundException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.EquipmentService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentDAO equipmentDAO;

    @Autowired
    private Mapping equipmentMapping;

    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private FieldDAO fieldDAO;

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR')")
    @Override
    public EquipmentDTO saveEquipment(EquipmentDTO equipmentDTO) {
        EquipmentEntity equipmentEntity = equipmentMapping.toEquipmentEntity(equipmentDTO);

        //maintain field and staff relationships
        if (equipmentDTO.getFieldCode() != null) {
            FieldEntity fieldEntity = fieldDAO.findById(equipmentDTO.getFieldCode()).orElseThrow(() ->
                    new DataPersistException("Field not found")
            );
            equipmentEntity.setField(fieldEntity);
        }
        if (equipmentDTO.getStaffId() != null) {
            StaffEntity staffEntity = staffDAO.findById(equipmentDTO.getStaffId()).orElseThrow(() ->
                    new DataPersistException("Staff not found")
            );
            equipmentEntity.setStaff(staffEntity);
        }

        EquipmentEntity savedEntity = equipmentDAO.save(equipmentEntity);
        if (savedEntity == null) {
            throw new DataPersistException("Equipment not saved");
        }
        return equipmentMapping.toEquipmentDTO(savedEntity);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    @Override
    public List<EquipmentDTO> getAllEquipments() {
        List<EquipmentEntity> allEquipments = equipmentDAO.findAll();
        return equipmentMapping.asEquipmentDTOList(allEquipments);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    @Override
    public EquipmentStatus getEquipment(String equipmentId) {
        if (equipmentDAO.existsById(equipmentId)){
            EquipmentEntity selectedEquipment = equipmentDAO.getReferenceById(equipmentId);
            return equipmentMapping.toEquipmentDTO(selectedEquipment);
        }else {
            return new SelectedCustomErrorStatus(2,"Equipment Id "+equipmentId+" Equipment not found");
        }
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR')")
    @Override
    public void deleteEquipment(String equipmentId) {
        Optional<EquipmentEntity> existedEquipment = equipmentDAO.findById(equipmentId);
        if (!existedEquipment.isPresent()){
            throw new EquipmentNotFoundException("Equipment not found to delete");
        }else {
            equipmentDAO.deleteById(equipmentId);
        }
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR')")
    @Override
    public void updateEquipment(String equipmentId, EquipmentDTO equipmentDTO) {
        Optional<EquipmentEntity> findEquipment = equipmentDAO.findById(equipmentId);

        if (findEquipment.isPresent()){
            findEquipment.get().setEquipmentName(equipmentDTO.getEquipmentName());
            findEquipment.get().setType(equipmentDTO.getType());
            findEquipment.get().setStatus(equipmentDTO.getStatus());

            StaffEntity staff = staffDAO.findById(equipmentDTO.getStaffId())
                    .orElseThrow(() -> new IllegalArgumentException("staff not found"));
            findEquipment.get().setStaff(staff);

            FieldEntity field = fieldDAO.findById(equipmentDTO.getFieldCode())
                    .orElseThrow(() -> new IllegalArgumentException("field not found"));
            findEquipment.get().setField(field);
        }else {
            throw new EquipmentNotFoundException("Equipment not found to update");
        }

    }

    @Override
    public String generateNextEquipmentId() {
        try {
            String lastEquipmentId = equipmentDAO.getLastEquipmentId();
            if (lastEquipmentId != null){
                int nextEquipmentId = Integer.parseInt(lastEquipmentId.split("-")[1]) + 1;
                return String.format("E00-%03d",nextEquipmentId);
            }else {
                return "E00-001";
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to generate next equipment Id",e);
        }
    }
}
