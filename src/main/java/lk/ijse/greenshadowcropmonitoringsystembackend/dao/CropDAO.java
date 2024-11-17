package lk.ijse.greenshadowcropmonitoringsystembackend.dao;

import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CropDAO extends JpaRepository<CropEntity,String> {
    @Query(value = "SELECT c.cropCode FROM CropEntity c ORDER BY c.cropCode DESC LIMIT 1")
    String getLastCropCode();
}
