package lk.ijse.greenshadowcropmonitoringsystembackend.dao;

import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffDAO extends JpaRepository<StaffEntity, String> {
    @Query(value = "SELECT s.staffId FROM StaffEntity s ORDER BY s.staffId DESC LIMIT 1")
    String getLastStaffId();
}
