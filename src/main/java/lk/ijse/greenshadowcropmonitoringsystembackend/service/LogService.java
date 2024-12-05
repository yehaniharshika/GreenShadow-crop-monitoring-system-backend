package lk.ijse.greenshadowcropmonitoringsystembackend.service;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.LogStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.LogDTO;

import java.util.List;
import java.util.Map;

public interface LogService {
    void saveLog(LogDTO logDTO);
    List<LogDTO> getAllLogs();
    LogStatus getLog(String logCode);
    void deleteLog(String logCode);
    void updateLog(String logCode, LogDTO logDTO);
    String generateNextLogCode();

    Map<String, Object> getRelatedEntitiesAsDtos(String logCode);
}
