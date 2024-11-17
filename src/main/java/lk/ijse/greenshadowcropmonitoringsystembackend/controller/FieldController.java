package lk.ijse.greenshadowcropmonitoringsystembackend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.FieldStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.FieldDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.StaffDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.FieldNotFoundException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.FieldService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/fields")
public class FieldController {

    @Autowired
    private FieldService fieldService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveField(
            @RequestPart("fieldCode") String fieldCode,
            @RequestPart("fieldName") String fieldName,
            @RequestPart("extentSize") String extentSize,
            @RequestPart("fieldLocation") String fieldLocation,
            @RequestPart("fieldImage1") MultipartFile fieldImage1,
            @RequestPart("fieldImage2") MultipartFile fieldImage2,
            @RequestPart("staff") String staffJson
    ) {

        try {
            //convert images to Base64 strings
            String base64FieldImage1 = AppUtil.fieldImageToBase64(fieldImage1.getBytes());
            String base64FieldImage2 = AppUtil.fieldImageToBase64(fieldImage2.getBytes());

            //parse the staff JSON into a list of StaffDTO objects
            ObjectMapper objectMapper = new ObjectMapper();
            List<StaffDTO> staffList = objectMapper.readValue(staffJson, new TypeReference<List<StaffDTO>>() {});

            //extract staff IDs from StaffDTO objects
            List<String> staffIds = staffList.stream()
                    .map(StaffDTO::getStaffId) // Extract staffId
                    .collect(Collectors.toList());

            //build the FieldDTO
            FieldDTO buildFieldDTO = new FieldDTO();
            buildFieldDTO.setFieldCode(fieldCode);
            buildFieldDTO.setFieldName(fieldName);
            buildFieldDTO.setExtentSize(Double.parseDouble(extentSize));
            buildFieldDTO.setFieldLocation(fieldLocation);
            buildFieldDTO.setFieldImage1(base64FieldImage1);
            buildFieldDTO.setFieldImage2(base64FieldImage2);
            buildFieldDTO.setStaff(staffIds);

            //save the field
            fieldService.saveField(buildFieldDTO);

            return new ResponseEntity<>("Field saved successfully", HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while saving the field: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{fieldCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateField(
            @PathVariable("fieldCode") String fieldCode,
            @RequestParam("fieldName") String fieldName,
            @RequestParam("extentSize") String extentSize,
            @RequestParam("fieldLocation") String fieldLocation,
            @RequestPart("fieldImage1") MultipartFile fieldImage1,
            @RequestPart("fieldImage2") MultipartFile fieldImage2,
            @RequestPart("staff") String staffJson
    ) {
        try {
            //convert images to Base64 strings
            String base64FieldImage1 = AppUtil.fieldImageToBase64(fieldImage1.getBytes());
            String base64FieldImage2 = AppUtil.fieldImageToBase64(fieldImage2.getBytes());

            //parse the staff JSON into a list of StaffDTO objects
            ObjectMapper objectMapper = new ObjectMapper();
            List<StaffDTO> staffList = objectMapper.readValue(staffJson, new TypeReference<List<StaffDTO>>() {});

            //extract staff IDs from StaffDTO objects
            List<String> staffIds = staffList.stream()
                    .map(StaffDTO::getStaffId) // Extract staffId
                    .collect(Collectors.toList());

            //build the FieldDTO
            FieldDTO buildFieldDTO = new FieldDTO();
            buildFieldDTO.setFieldCode(fieldCode);
            buildFieldDTO.setFieldName(fieldName);
            buildFieldDTO.setExtentSize(Double.parseDouble(extentSize));
            buildFieldDTO.setFieldLocation(fieldLocation);
            buildFieldDTO.setFieldImage1(base64FieldImage1);
            buildFieldDTO.setFieldImage2(base64FieldImage2);
            buildFieldDTO.setStaff(staffIds);

            fieldService.updateField(fieldCode, buildFieldDTO);

            return new ResponseEntity<>("Field update successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Not update Field: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldStatus getSelectedField(@PathVariable("fieldCode") String fieldCode){
        return fieldService.getField(fieldCode);
    }

    //get all fields
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAllFields(){
        return  fieldService.getAllFields();
    }

    //delete field
    @DeleteMapping(value = "/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteField(@PathVariable("fieldCode") String fieldCode){
        try {
            fieldService.deleteField(fieldCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (FieldNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{fieldCode}/staff")
    public ResponseEntity<List<StaffDTO>> getStaffByFieldCode(@PathVariable("fieldCode") String fieldCode){
        List<StaffDTO> staffList = fieldService.getStaffIdsByFieldCode(fieldCode);
        return ResponseEntity.ok(staffList);
    }

    @GetMapping(value ="/generate-next-field-code",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> generateNextFieldCode(){
        try {
            String nextFieldCode = fieldService.generateNextFieldCode();
            Map<String, String> response = new HashMap<>();
            response.put("fieldCode",nextFieldCode);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
