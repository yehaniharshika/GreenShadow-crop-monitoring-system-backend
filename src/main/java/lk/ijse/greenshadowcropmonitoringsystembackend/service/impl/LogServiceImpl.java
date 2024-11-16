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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void saveLog(LogDTO logDTO) {
        LogsEntity savedLog = logDAO.save(logMapping.toLogEntity(logDTO));
        if (savedLog == null){
            throw new DataPersistException("Log not saved");
        }

    }

    @Override
    public List<LogDTO> getAllLogs() {
        return logMapping.asLogDTOList(logDAO.findAll());
    }

    @Override
    public LogStatus getLog(String logCode) {
        if (logDAO.existsById(logCode)){
            LogsEntity selectedLog = logDAO.getReferenceById(logCode);
            return logMapping.toLogDTO(selectedLog);
        }else {
            return new SelectedCustomErrorStatus(2,"selected log not found");
        }
    }

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

    @Override
    public void deleteLog(String logCode) {


    }


}
