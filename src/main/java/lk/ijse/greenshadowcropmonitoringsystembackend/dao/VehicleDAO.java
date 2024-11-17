package lk.ijse.greenshadowcropmonitoringsystembackend.dao;

import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleDAO extends JpaRepository<VehicleEntity, String> {
    @Query(value = "SELECT v.vehicleCode FROM VehicleEntity v ORDER BY v.vehicleCode DESC LIMIT 1")
    String getLastVehicleCode();
}
