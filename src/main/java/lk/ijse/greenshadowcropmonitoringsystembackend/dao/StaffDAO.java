package lk.ijse.greenshadowcropmonitoringsystembackend.dao;

import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffDAO extends JpaRepository<StaffEntity, String> {
}
