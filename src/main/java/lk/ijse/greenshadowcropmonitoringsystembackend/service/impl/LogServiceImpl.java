package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.customStatusCodes.SelectedCustomErrorStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.CropDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.FieldDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.LogDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.StaffDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.LogStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.CropDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.FieldDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.LogDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.StaffDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.CropEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.FieldEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.LogsEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.StaffEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.LogNotFoundException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.LogService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDAO logDAO;

    @Autowired
    private Mapping logMapping;

    @Autowired
    private FieldDAO fieldDAO;

    @Autowired
    private CropDAO cropDAO;

    @Autowired
    private StaffDAO staffDAO;

    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    @Override
    public void saveLog(LogDTO logDTO) {
        LogsEntity logsEntity = logMapping.toLogEntity(logDTO);

        // Fetch and set Staff Entities
        if (logDTO.getStaffLogs() != null && !logDTO.getStaffLogs().isEmpty()) {
            List<StaffEntity> staffEntities = new ArrayList<>();
            for (StaffDTO staffDTO : logDTO.getStaffLogs()) {
                StaffEntity staffEntity = staffDAO.findById(staffDTO.getStaffId())
                        .orElseThrow(() -> new DataPersistException("Staff not found with ID: " + staffDTO.getStaffId()));
                staffEntities.add(staffEntity);
            }
            logsEntity.setStaffLogs(staffEntities);
        }

        // Fetch and set Field Entities
        if (logDTO.getFieldLogs() != null && !logDTO.getFieldLogs().isEmpty()) {
            List<FieldEntity> fieldEntities = new ArrayList<>();
            for (FieldDTO fieldDTO : logDTO.getFieldLogs()) {
                FieldEntity fieldEntity = fieldDAO.findById(fieldDTO.getFieldCode())
                        .orElseThrow(() -> new DataPersistException("Field not found with Code: " + fieldDTO.getFieldCode()));
                fieldEntities.add(fieldEntity);
            }
            logsEntity.setFieldLogs(fieldEntities);
        }

        // Fetch and set Crop Entities
        if (logDTO.getCropLogs() != null && !logDTO.getCropLogs().isEmpty()) {
            List<CropEntity> cropEntities = new ArrayList<>();
            for (CropDTO cropDTO : logDTO.getCropLogs()) {
                CropEntity cropEntity = cropDAO.findById(cropDTO.getCropCode())
                        .orElseThrow(() -> new DataPersistException("Crop not found with Code: " + cropDTO.getCropCode()));
                cropEntities.add(cropEntity);
            }
            logsEntity.setCropLogs(cropEntities);
        }
        LogsEntity savedLog = logDAO.save(logsEntity);
        if (savedLog == null){
            throw new DataPersistException("Log not saved");
        }

    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    @Override
    public List<LogDTO> getAllLogs() {
        return logMapping.asLogDTOList(logDAO.findAll());
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    @Override
    public LogStatus getLog(String logCode) {
        if (logDAO.existsById(logCode)){
            LogsEntity selectedLog = logDAO.getReferenceById(logCode);
            return logMapping.toLogDTO(selectedLog);
        }else {
            return new SelectedCustomErrorStatus(2,"selected log not found");
        }
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    @Override
    public void updateLog(String logCode, LogDTO logDTO) {
        Optional<LogsEntity> findLog = logDAO.findById(logCode);
        if (!findLog.isPresent()){
            throw new LogNotFoundException(("Log not found"));
        }else {
            findLog.get().setLogDate(logDTO.getLogDate());
            findLog.get().setLogDetails(logDTO.getLogDetails());
            findLog.get().setObservedImage(logDTO.getObservedImage());

            List<StaffEntity> staffEntities = new ArrayList<>();
            for (StaffDTO staff : logDTO.getStaffLogs()){
                StaffEntity staffEntity = staffDAO.findById(staff.getStaffId())
                        .orElseThrow(() -> new DataPersistException("Staff not found"));
                staffEntities.add(staffEntity);
            }
            findLog.get().setStaffLogs(staffEntities);

            List<FieldEntity> fieldEntities = new ArrayList<>();
            for (FieldDTO field : logDTO.getFieldLogs()){
                FieldEntity fieldEntity = fieldDAO.findById(field.getFieldCode())
                        .orElseThrow(() -> new DataPersistException("Field not found"));
                fieldEntities.add(fieldEntity);
            }
            findLog.get().setFieldLogs(fieldEntities);

            List<CropEntity> cropEntities = new ArrayList<>();
            for (CropDTO crop : logDTO.getCropLogs()){
                CropEntity cropEntity = cropDAO.findById(crop.getCropCode())
                        .orElseThrow(() -> new DataPersistException("Crop not found"));
                cropEntities.add(cropEntity);
            }
            findLog.get().setCropLogs(cropEntities);
        }
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    @Override
    public void deleteLog(String logCode) {
        Optional<LogsEntity> existedLog = logDAO.findById(logCode);
        if (!existedLog.isPresent()){
            throw new LogNotFoundException("Log not found to delete");
        }else {
            logDAO.deleteById(logCode);
        }
    }

    @Override
    public String generateNextLogCode() {
        try {
            String lastLogCode = logDAO.getLastLogCode();
            if (lastLogCode != null){
                int nextLogCode = Integer.parseInt(lastLogCode.split("-")[1]) + 1;
                return String.format("L00-%03d",nextLogCode);
            }else {
                return "L00-001";
            }
        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException("Failed to generate next field code",e);
        }
    }

    @Override
    public Map<String, Object> getRelatedEntitiesAsDtos(String logCode) {
        Map<String, Object> relatedEntities = new HashMap<>();
        List<FieldDTO> fieldDtos = null;
        List<CropDTO> cropDtos = null;
        List<StaffDTO> staffDtos = null;
        Optional<LogsEntity> logEntity = logDAO.findById(logCode);

        if (logEntity.isPresent()){
            LogsEntity log = logEntity.get();

            //convert PersistentSet to List
            List<FieldEntity> fieldEntities = new ArrayList<>(log.getFieldLogs());
            List<CropEntity> cropEntities = new ArrayList<>(log.getCropLogs());
            List<StaffEntity> staffEntities = new ArrayList<>(log.getStaffLogs());

            if (!fieldEntities.isEmpty()){
                fieldDtos =  logMapping.asFieldDTOList(fieldEntities);
            }
            if ((!cropEntities.isEmpty())){
                cropDtos = logMapping.asCropDTOList(cropEntities);
            }
            if (!staffEntities.isEmpty()){
                staffDtos = logMapping.asStaffDTOList(staffEntities);
            }

        }

        relatedEntities.put("fields", fieldDtos);
        relatedEntities.put("crops", cropDtos);
        relatedEntities.put("staff", staffDtos);

        return relatedEntities;
    }
}
