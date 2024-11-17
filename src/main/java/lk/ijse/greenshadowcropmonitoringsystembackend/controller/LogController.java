package lk.ijse.greenshadowcropmonitoringsystembackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.LogStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.CropDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.FieldDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.LogDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.StaffDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.LogNotFoundException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.LogService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/logs")
public class LogController {

    @Autowired
    private LogService logService;

    /*@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveLogs(@RequestBody LogDTO logDTO){
        try {
            logService.saveLog(logDTO);
            return new ResponseEntity<>("Logs saved successfully", HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>("Log not saved",HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveLogs(
            @RequestPart("logCode") String logCode,
            @RequestPart("logDate") String logDate,
            @RequestPart("logDetails") String logDetails,
            @RequestPart("observedImage") MultipartFile observedImage,
            @RequestPart("staffLogs") String staffLogsJson,
            @RequestPart("fieldLogs") String fieldLogsJson,
            @RequestPart("cropLogs") String cropLogsJson
    ){
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            List<StaffDTO> staffLogs = objectMapper.readValue(staffLogsJson, new TypeReference<List<StaffDTO>>() {});
            List<FieldDTO> fieldLogs = objectMapper.readValue(fieldLogsJson, new TypeReference<List<FieldDTO>>() {});
            List<CropDTO> cropLogs = objectMapper.readValue(cropLogsJson, new TypeReference<List<CropDTO>>() {});

            String base64ObservedImage = AppUtil.LogObservedImageToBase64(observedImage.getBytes());

            //build the LogDTO
            LogDTO buildLogDTO = new LogDTO();
            buildLogDTO.setLogCode(logCode);
            buildLogDTO.setLogDate(Date.valueOf(logDate));
            buildLogDTO.setLogDetails(logDetails);
            buildLogDTO.setObservedImage(base64ObservedImage);
            buildLogDTO.setStaffLogs(staffLogs);
            buildLogDTO.setCropLogs(cropLogs);
            buildLogDTO.setFieldLogs(fieldLogs);

            logService.saveLog(buildLogDTO);
            return new ResponseEntity<>("Logs saved successfully", HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>("Log not saved",HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{logCode}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateLogs(
            @RequestPart("logCode") String logCode,
            @RequestPart("logDate") String logDate,
            @RequestPart("logDetails") String logDetails,
            @RequestPart("observedImage") MultipartFile observedImage,
            @RequestPart("staffLogs") String staffLogsJson,
            @RequestPart("fieldLogs") String fieldLogsJson,
            @RequestPart("cropLogs") String cropLogsJson
    ){
        try {
            ObjectMapper objectMapper= new ObjectMapper();
            List<StaffDTO> staffLogs = objectMapper.readValue(staffLogsJson, new TypeReference<List<StaffDTO>>() {});
            List<FieldDTO> fieldLogs = objectMapper.readValue(fieldLogsJson, new TypeReference<List<FieldDTO>>() {});
            List<CropDTO> cropLogs = objectMapper.readValue(cropLogsJson, new TypeReference<List<CropDTO>>() {});

            String base64ObservedImage = AppUtil.LogObservedImageToBase64(observedImage.getBytes());

            //build the LogDTO
            LogDTO buildLogDTO = new LogDTO();
            buildLogDTO.setLogCode(logCode);
            buildLogDTO.setLogDate(Date.valueOf(logDate));
            buildLogDTO.setLogDetails(logDetails);
            buildLogDTO.setObservedImage(base64ObservedImage);
            buildLogDTO.setStaffLogs(staffLogs);
            buildLogDTO.setCropLogs(cropLogs);
            buildLogDTO.setFieldLogs(fieldLogs);

            logService.updateLog(logCode,buildLogDTO);
            return new ResponseEntity<>("Logs update successfully",HttpStatus.OK);
        }catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Get selected log
    @GetMapping(value = "/{logCode}",produces = MediaType.APPLICATION_JSON_VALUE)
    public LogStatus getSelectedLog(@PathVariable("logCode") String logCode){
        return logService.getLog(logCode);
    }

    //Get all logs
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LogDTO> getAllLogs(){
        return logService.getAllLogs();
    }

    @DeleteMapping(value = "/{logCode}")
    public ResponseEntity<String> deleteLog(@PathVariable("logCode") String logCode){
        try {
            logService.deleteLog(logCode);
            return new ResponseEntity<>("Log deleted successfully",HttpStatus.OK);
        }catch (LogNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/generate-next-log-code",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> generateNextLogCode(){
        try {
            String nextLogCode = logService.generateNextLogCode();
            Map<String, String> response = new HashMap<>();
            response.put("logCode",nextLogCode);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
