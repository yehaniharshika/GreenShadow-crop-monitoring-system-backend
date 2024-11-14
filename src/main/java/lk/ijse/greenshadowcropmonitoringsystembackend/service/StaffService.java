package lk.ijse.greenshadowcropmonitoringsystembackend.service;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.FieldStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.FieldDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.StaffDTO;

import java.util.List;

public interface StaffService {
    void saveStaff(StaffDTO staffDTO);
    List<FieldDTO> getAllStaff();
    FieldStatus getStaff(String staffId);
    void updateStaff(String staffId,StaffDTO staffDTO);
    void deleteStaff(String staffId);
}
