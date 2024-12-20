package lk.ijse.greenshadowcropmonitoringsystembackend.controller;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.EquipmentStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.EquipmentDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.EquipmentNotFoundException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/equipments")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EquipmentDTO> saveEquipment(@RequestBody EquipmentDTO equipmentDTO){
        try {
            EquipmentDTO savedEquipment = equipmentService.saveEquipment(equipmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEquipment);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{equipmentId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentStatus getSelectedEquipment(@PathVariable("equipmentId") String equipmentId){
        return equipmentService.getEquipment(equipmentId);
    }

    //get all equipments
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO>  getAllEquipments(){
        return equipmentService.getAllEquipments();
    }

    //Equipment update
    @PutMapping(value = "/{equipmentId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateVehicle(@PathVariable("equipmentId") String equipmentId,@RequestBody EquipmentDTO updateEquipmentDTO){
        try {
            if (updateEquipmentDTO == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            equipmentService.updateEquipment(equipmentId,updateEquipmentDTO);
            return new ResponseEntity<>("Equipment updated successfully",HttpStatus.OK);
        }catch (EquipmentNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>("Equipment not updated",HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> deleteEquipment(@PathVariable("equipmentId") String equipmentId) {
        try {
            equipmentService.deleteEquipment(equipmentId);

            // Return a success message in JSON format
            Map<String, String> response = new HashMap<>();
            response.put("message", "Equipment deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EquipmentNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //generate next equipment Id
    @GetMapping(value = "/generate-next-equipment-id",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> generateNextEquipmentId(){
        try {
            String nextEquipmentId = equipmentService.generateNextEquipmentId();
            Map<String, String> response = new HashMap<>();
            response.put("equipmentId",nextEquipmentId);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
