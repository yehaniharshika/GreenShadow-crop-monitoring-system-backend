package lk.ijse.greenshadowcropmonitoringsystembackend.dao;

import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.LogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDAO extends JpaRepository<LogsEntity, String> {
    @Query(value = "SELECT l.logCode FROM LogsEntity l ORDER BY l.logCode DESC LIMIT 1")
    String getLastLogCode();
}
