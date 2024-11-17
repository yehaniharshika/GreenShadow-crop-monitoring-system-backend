package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.dao.VehicleDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.VehicleStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.VehicleDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleDAO vehicleDAO;
    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) {

    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return null;
    }

    @Override
    public VehicleStatus getVehicle(String vehicleCode) {
        return null;
    }

    @Override
    public void updateVehicle(String vehicleCode, VehicleDTO vehicleDTO) {

    }

    @Override
    public void deleteVehicle(String vehicleCode) {

    }
}
