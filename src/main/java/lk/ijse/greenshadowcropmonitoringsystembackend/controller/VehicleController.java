package lk.ijse.greenshadowcropmonitoringsystembackend.controller;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.VehicleStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.VehicleDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTO> saveVehicle(@RequestBody VehicleDTO vehicleDTO){
        try {
            VehicleDTO savedVehicle = vehicleService.saveVehicle(vehicleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{vehicleCode}",produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleStatus getSelectedVehicle(@PathVariable("vehicleCode") String vehicleCode){
        return vehicleService.getVehicle(vehicleCode);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDTO> getAllVehicles(){
        return vehicleService.getAllVehicles();
    }
}
