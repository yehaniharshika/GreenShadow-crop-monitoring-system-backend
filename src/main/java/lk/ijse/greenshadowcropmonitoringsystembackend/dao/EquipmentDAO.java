package lk.ijse.greenshadowcropmonitoringsystembackend.dao;

import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentDAO extends JpaRepository<EquipmentEntity, String> {
}
