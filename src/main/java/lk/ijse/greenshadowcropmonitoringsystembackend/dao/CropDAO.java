package lk.ijse.greenshadowcropmonitoringsystembackend.dao;

import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropDAO extends JpaRepository<CropEntity,String> {
}
