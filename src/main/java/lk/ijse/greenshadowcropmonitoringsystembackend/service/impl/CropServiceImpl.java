package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.CropStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.CropDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.CropService;

import java.util.List;

public class CropServiceImpl implements CropService {
    @Override
    public void saveCrop(CropDTO cropDTO) {
        
    }

    @Override
    public List<CropDTO> getAllCrops() {
        return null;
    }

    @Override
    public CropStatus getCrop(String cropCode) {
        return null;
    }

    @Override
    public void updateCrop(String cropCode, CropDTO cropDTO) {

    }

    @Override
    public void deleteCrop(String cropCode) {

    }
}
