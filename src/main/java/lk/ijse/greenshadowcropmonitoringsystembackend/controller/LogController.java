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
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveLogs(
            @RequestPart("logCode") String logCode,
            @RequestPart("logDate") String logDate,
            @RequestPart("logDetails") String logDetails,
            @RequestPart("observedImage") MultipartFile observedImage,
            @RequestPart(value = "staffLogs", required = false) String staffLogsJson,
            @RequestPart(value = "fieldLogs", required = false) String fieldLogsJson,
            @RequestPart(value = "cropLogs", required = false) String cropLogsJson
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            //Parse JSON data
            List<StaffDTO> staffLogs = staffLogsJson != null && !staffLogsJson.isEmpty()
                    ? objectMapper.readValue(staffLogsJson, new TypeReference<List<StaffDTO>>() {})
                    : new ArrayList<>();

            List<FieldDTO> fieldLogs = fieldLogsJson != null && !fieldLogsJson.isEmpty()
                    ? objectMapper.readValue(fieldLogsJson, new TypeReference<List<FieldDTO>>() {})
                    : new ArrayList<>();

            List<CropDTO> cropLogs = cropLogsJson != null && !cropLogsJson.isEmpty()
                    ? objectMapper.readValue(cropLogsJson, new TypeReference<List<CropDTO>>() {})
                    : new ArrayList<>();

            // Ensure at least one category is provided
            if (staffLogs.isEmpty() && fieldLogs.isEmpty() && cropLogs.isEmpty()) {
                return ResponseEntity.badRequest().body("At least one log category (staffLogs, fieldLogs, cropLogs) must be provided.");
            }

            // Convert observed image to Base64
            String base64ObservedImage = AppUtil.LogObservedImageToBase64(observedImage.getBytes());

            // Create LogDTO
            LogDTO logDTO = new LogDTO();
            logDTO.setLogCode(logCode);
            logDTO.setLogDate(Date.valueOf(logDate));
            logDTO.setLogDetails(logDetails);
            logDTO.setObservedImage(base64ObservedImage);
            logDTO.setStaffLogs(staffLogs);
            logDTO.setFieldLogs(fieldLogs);
            logDTO.setCropLogs(cropLogs);

            // Save the log
            logService.saveLog(logDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body("Log saved successfully");
        } catch (JsonParseException e) {
            return ResponseEntity.badRequest().body("Invalid JSON format in staffLogs, fieldLogs, or cropLogs");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the log");
        }
    }


    @PutMapping(value = "/{logCode}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateLogs(
            @PathVariable("logCode") String logCode,
            @RequestPart("logDate") String logDate,
            @RequestPart("logDetails") String logDetails,
            @RequestPart("observedImage") MultipartFile observedImage,
            @RequestPart(value = "staffLogs", required = false) String staffLogsJson,
            @RequestPart(value = "fieldLogs", required = false) String fieldLogsJson,
            @RequestPart(value = "cropLogs", required = false) String cropLogsJson
    ){
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            //Parse JSON data
            List<StaffDTO> staffLogs = staffLogsJson != null && !staffLogsJson.isEmpty()
                    ? objectMapper.readValue(staffLogsJson, new TypeReference<List<StaffDTO>>() {})
                    : new ArrayList<>();

            List<FieldDTO> fieldLogs = fieldLogsJson != null && !fieldLogsJson.isEmpty()
                    ? objectMapper.readValue(fieldLogsJson, new TypeReference<List<FieldDTO>>() {})
                    : new ArrayList<>();

            List<CropDTO> cropLogs = cropLogsJson != null && !cropLogsJson.isEmpty()
                    ? objectMapper.readValue(cropLogsJson, new TypeReference<List<CropDTO>>() {})
                    : new ArrayList<>();

            // Ensure at least one category is provided
            if (staffLogs.isEmpty() && fieldLogs.isEmpty() && cropLogs.isEmpty()) {
                return ResponseEntity.badRequest().body("At least one log category (staffLogs, fieldLogs, cropLogs) must be provided.");
            }

            // Convert observed image to Base64
            String base64ObservedImage = AppUtil.LogObservedImageToBase64(observedImage.getBytes());

            //Create LogDTO
            LogDTO updateLogDTO = new LogDTO();
            updateLogDTO.setLogCode(logCode);
            updateLogDTO.setLogDate(Date.valueOf(logDate));
            updateLogDTO.setLogDetails(logDetails);
            updateLogDTO.setObservedImage(base64ObservedImage);
            updateLogDTO.setStaffLogs(staffLogs);
            updateLogDTO.setFieldLogs(fieldLogs);
            updateLogDTO.setCropLogs(cropLogs);

            logService.updateLog(logCode,updateLogDTO);
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
