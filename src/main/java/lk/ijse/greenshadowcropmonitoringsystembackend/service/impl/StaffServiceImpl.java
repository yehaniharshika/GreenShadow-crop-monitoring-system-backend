package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.dao.StaffDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.FieldStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.FieldDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.StaffDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.StaffEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.StaffService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private Mapping staffMapping;

    @Override
    public void saveStaff(StaffDTO staffDTO) {
        StaffEntity saveStaff = staffDAO.save(staffMapping.toFieldEntity(staffDTO))
    }

    @Override
    public List<FieldDTO> getAllStaff() {
        return null;
    }

    @Override
    public FieldStatus getStaff(String staffId) {
        return null;
    }

    @Override
    public void updateStaff(String staffId, StaffDTO staffDTO) {

    }

    @Override
    public void deleteStaff(String staffId) {

    }
}
