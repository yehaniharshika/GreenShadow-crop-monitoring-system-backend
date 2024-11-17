package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.EquipmentStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.EquipmentDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.EquipmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Override
    public void saveEquipment(EquipmentDTO equipmentDTO) {

    }

    @Override
    public List<EquipmentDTO> getAllEquipments() {
        return null;
    }

    @Override
    public EquipmentStatus getEquipment(String equipmentId) {
        return null;
    }

    @Override
    public void deleteEquipment(String equipmentId) {

    }

    @Override
    public void updateEquipment(String equipmentId, EquipmentDTO equipmentDTO) {

    }
}
