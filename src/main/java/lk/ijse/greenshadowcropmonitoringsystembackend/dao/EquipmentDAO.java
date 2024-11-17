package lk.ijse.greenshadowcropmonitoringsystembackend.dao;

import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EquipmentDAO extends JpaRepository<EquipmentEntity, String> {
    @Query(value = "SELECT e.equipmentId FROM EquipmentEntity e ORDER BY e.equipmentId DESC LIMIT 1")
    String getLastEquipmentId();
}
