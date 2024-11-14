package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.customStatusCodes.SelectedCustomErrorStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.StaffDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.FieldStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.StaffStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.FieldDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.StaffDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.FieldEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.StaffEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.StaffNotFoundException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.StaffService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private Mapping staffMapping;

    @Override
    public void saveStaff(StaffDTO staffDTO) {
        StaffEntity saveStaff = staffDAO.save(staffMapping.toStaffEntity(staffDTO));
        if (saveStaff == null){
            throw new DataPersistException("Staff not saved");
        }
    }

    @Override
    public List<StaffDTO> getAllStaff() {
        List<StaffEntity> allStaff = staffDAO.findAll();
        return staffMapping.asStaffDTOList(allStaff);
    }

    @Override
    public StaffStatus getStaff(String staffId) {
        if (staffDAO.existsById(staffId)){
            StaffEntity selectedStaff = staffDAO.getReferenceById(staffId);
            return staffMapping.toStaffDTO(selectedStaff);
        }
        return new SelectedCustomErrorStatus(2,"selected staff not found");
    }


    @Override
    public void updateStaff(String staffId, StaffDTO staffDTO) {
        Optional<StaffEntity> tmpStaff = staffDAO.findById(staffId);

        if (tmpStaff.isPresent()){
            tmpStaff.get().setFirstName(staffDTO.getFirstName());
            tmpStaff.get().setLastName(staffDTO.getLastName());
            tmpStaff.get().setDOB(staffDTO.getDOB());
            tmpStaff.get().setGender(staffDTO.getGender());
            tmpStaff.get().setDesignation(staffDTO.getDesignation());
            tmpStaff.get().setJoinedDate(staffDTO.getJoinedDate());
            tmpStaff.get().setAddressLine1(staffDTO.getAddressLine1());
            tmpStaff.get().setAddressLine2(staffDTO.getAddressLine2());
            tmpStaff.get().setAddressLine3(staffDTO.getAddressLine3());
            tmpStaff.get().setAddressLine4(staffDTO.getAddressLine4());
            tmpStaff.get().setAddressLine5(staffDTO.getAddressLine5());
            tmpStaff.get().setEmail(staffDTO.getEmail());
            tmpStaff.get().setRole(staffDTO.getRole());
            tmpStaff.get().setContactNumber(staffDTO.getContactNumber());
        }

    }

    @Override
    public void deleteStaff(String staffId) {
        Optional<StaffEntity> existedStaff = staffDAO.findById(staffId);
        if (!existedStaff.isPresent()){
            throw new StaffNotFoundException("Staff Id: "+staffId+" staff not found");
        }else {
            staffDAO.deleteById(staffId);
        }

    }
}