package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.customStatusCodes.SelectedCustomErrorStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.StaffDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.VehicleDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.VehicleStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.VehicleDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.StaffEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.VehicleEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.FieldNotFoundException;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.VehicleNotFoundException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.VehicleService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private Mapping vehicleMapping;

    @Autowired
    private StaffDAO staffDAO;

    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) {
        VehicleEntity saveVehicle = vehicleDAO.save(vehicleMapping.toVehicleEntity(vehicleDTO));
        if (saveVehicle == null){
            throw new DataPersistException("Vehicle not saved");
        }
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        List<VehicleEntity> allVehicles = vehicleDAO.findAll();
        return vehicleMapping.asVehicleDTOList(allVehicles);
    }

    @Override
    public VehicleStatus getVehicle(String vehicleCode) {
        if (vehicleDAO.existsById(vehicleCode)){
            VehicleEntity selectedVehicle = vehicleDAO.getReferenceById(vehicleCode);
            return vehicleMapping.toVehicleDTO(selectedVehicle);
        }else {
            return new SelectedCustomErrorStatus(2,"Vehicle code "+vehicleCode+" not fount");
        }
    }

    @Override
    public void updateVehicle(String vehicleCode, VehicleDTO vehicleDTO) {
        Optional<VehicleEntity> findVehicle = vehicleDAO.findById(vehicleCode);

        if (findVehicle.isPresent()){
            findVehicle.get().setLicensePlateNumber(vehicleDTO.getLicensePlateNumber());
            findVehicle.get().setCategory(vehicleDTO.getCategory());
            findVehicle.get().setFuelType(vehicleDTO.getFuelType());
            findVehicle.get().setStatus(vehicleDTO.getStatus());
            findVehicle.get().setRemarks(vehicleDTO.getRemarks());

            StaffEntity staff = staffDAO.findById(vehicleDTO.getStaffId())
                    .orElseThrow(() -> new IllegalArgumentException("staff not found"));
            findVehicle.get().setStaff(staff);
        }else {
            throw new VehicleNotFoundException("vehicle not fount to update");
        }
    }

    @Override
    public void deleteVehicle(String vehicleCode) {
        Optional<VehicleEntity> existedVehicle = vehicleDAO.findById(vehicleCode);
        if (!existedVehicle.isPresent()){
            throw new VehicleNotFoundException("vehicle not found to delete");
        }else {
            vehicleDAO.deleteById(vehicleCode);
        }

    }
}
