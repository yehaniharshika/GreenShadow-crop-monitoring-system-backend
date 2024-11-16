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
import java.util.List;
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
            // Convert images to Base64 strings
            String base64FieldImage1 = AppUtil.fieldImageToBase64(fieldImage1.getBytes());
            String base64FieldImage2 = AppUtil.fieldImageToBase64(fieldImage2.getBytes());

            // Parse the staff JSON into a list of StaffDTO objects
            ObjectMapper objectMapper = new ObjectMapper();
            List<StaffDTO> staffList = objectMapper.readValue(staffJson, new TypeReference<List<StaffDTO>>() {});

            // Extract staff IDs from StaffDTO objects
            List<String> staffIds = staffList.stream()
                    .map(StaffDTO::getStaffId) // Extract staffId
                    .collect(Collectors.toList());

            // Build the FieldDTO
            FieldDTO buildFieldDTO = new FieldDTO();
            buildFieldDTO.setFieldCode(fieldCode);
            buildFieldDTO.setFieldName(fieldName);
            buildFieldDTO.setExtentSize(Double.parseDouble(extentSize));
            buildFieldDTO.setFieldLocation(fieldLocation);
            buildFieldDTO.setFieldImage1(base64FieldImage1);
            buildFieldDTO.setFieldImage2(base64FieldImage2);
            buildFieldDTO.setStaff(staffIds);

            // Save the field entity
            fieldService.saveField(buildFieldDTO);

            return new ResponseEntity<>("Field saved successfully", HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while saving the field: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{fieldCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateField(
            @PathVariable("fieldCode") String fieldCode,
            @RequestParam("fieldName") String fieldName,
            @RequestParam("extentSize") String extentSize,
            @RequestParam("fieldLocation") String fieldLocation,
            @RequestPart("fieldImage1") MultipartFile fieldImage1,
            @RequestPart("fieldImage2") MultipartFile fieldImage2
    ) {
        String base64FieldImage1 = "";
        String base64FieldImage2 = "";

        try {
            byte[] bytesFieldImage1 = fieldImage1.getBytes();
            byte[] bytesFieldImage2 = fieldImage2.getBytes();

            base64FieldImage1 = AppUtil.fieldImageToBase64(bytesFieldImage1);
            base64FieldImage2 = AppUtil.fieldImageToBase64(bytesFieldImage2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Build the object
        var buildFieldDTO = new FieldDTO();
        buildFieldDTO.setFieldCode(fieldCode);
        buildFieldDTO.setFieldName(fieldName);
        buildFieldDTO.setExtentSize(Double.valueOf(extentSize));
        buildFieldDTO.setFieldLocation(fieldLocation);
        buildFieldDTO.setFieldImage1(base64FieldImage1);
        buildFieldDTO.setFieldImage2(base64FieldImage2);

        fieldService.updateField(fieldCode, buildFieldDTO);
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
}
