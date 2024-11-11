package lk.ijse.greenshadowcropmonitoringsystembackend.dao;

import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDAO extends JpaRepository<FieldEntity,String> {
}
