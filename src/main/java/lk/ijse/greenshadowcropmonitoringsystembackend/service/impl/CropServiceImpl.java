package lk.ijse.greenshadowcropmonitoringsystembackend.service.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.customStatusCodes.SelectedCustomErrorStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.CropDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dao.FieldDAO;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.CropStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.CropDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.CropEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.FieldEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.UserEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.CropNotFoundException;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.CropService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CropServiceImpl implements CropService {

    @Autowired
    private CropDAO cropDAO;

    @Autowired
    private Mapping cropMapping;

    @Autowired
    private FieldDAO fieldDAO;

    @Override
    public void saveCrop(CropDTO cropDTO) {
        CropEntity savedCrop = cropDAO.save(cropMapping.toCropEntity(cropDTO));
        if (savedCrop == null){
            throw new DataPersistException("crop not saved");
        }
    }

    @Override
    public List<CropDTO> getAllCrops() {
        List<CropEntity> allCrops = cropDAO.findAll();
        return cropMapping.asCropDTOList(allCrops);
    }

    @Override
    public CropStatus getCrop(String cropCode) {
        if (cropDAO.existsById(cropCode)){
            CropEntity selectedCrop = cropDAO.getReferenceById(cropCode);
            return cropMapping.toCropDTO(selectedCrop);
        }else {
            return new SelectedCustomErrorStatus(2,"Crop code: "+cropCode+" not found");
        }
    }

    @Override
    public void updateCrop(String cropCode, CropDTO cropDTO) {
        Optional<CropEntity> findCrop = cropDAO.findById(cropCode);

        if (findCrop.isPresent()){
            findCrop.get().setCropCommonName(cropDTO.getCropCommonName());
            findCrop.get().setScientificName(cropDTO.getCropCommonName());
            findCrop.get().setCategory(cropDTO.getCropCommonName());
            findCrop.get().setCropSeason(cropDTO.getCropCommonName());
            findCrop.get().setCropImage(cropDTO.getCropCommonName());

            FieldEntity field = fieldDAO.findById(cropDTO.getFieldCode())
                        .orElseThrow(() -> new IllegalArgumentException("Field not found"));
                findCrop.get().setField(field);
        }else{
            throw new CropNotFoundException("Crop not found to update");
        }
    }

    @Override
    public void deleteCrop(String cropCode) {
        Optional<CropEntity> existedCrop = cropDAO.findById(cropCode);
        if (!existedCrop.isPresent()){
            throw new CropNotFoundException("crop not found to delete");
        }else {
            cropDAO.deleteById(cropCode);
        }

    }

    @Override
    public String generateNextCropCode() {
        try {
            String lastCropCode = cropDAO.getLastCropCode();
            if (lastCropCode != null){
                int nextCropCode = Integer.parseInt(lastCropCode.split("-")[1]) + 1;
                return String.format("C00-%03d",nextCropCode);
            }else {
                return "C00-001";
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to generate Crop code");
        }
    }
}
