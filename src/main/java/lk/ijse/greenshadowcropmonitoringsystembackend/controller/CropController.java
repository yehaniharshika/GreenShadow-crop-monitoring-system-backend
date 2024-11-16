package lk.ijse.greenshadowcropmonitoringsystembackend.controller;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.CropStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl.CropDTO;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.CropNotFoundException;
import lk.ijse.greenshadowcropmonitoringsystembackend.exception.DataPersistException;
import lk.ijse.greenshadowcropmonitoringsystembackend.service.CropService;
import lk.ijse.greenshadowcropmonitoringsystembackend.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/crops")
public class CropController {

    @Autowired
    private CropService cropService;

    /*@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCrop(@RequestBody CropDTO cropDTO){
        try {
            cropService.saveCrop(cropDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveCrop(
            @RequestPart("cropCode") String cropCode,
            @RequestPart("cropCommonName") String cropCommonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("cropSeason") String cropSeason,
            @RequestPart("cropImage") MultipartFile cropImage,
            @RequestPart("fieldCode") String fieldCode
    ){
        try {
            String base64CropImage = AppUtil.cropImageToBase64(cropImage.getBytes());

            //Build the CropDTO
            CropDTO buildCropDTO = new CropDTO();
            buildCropDTO.setCropCode(cropCode);
            buildCropDTO.setCropCommonName(cropCommonName);
            buildCropDTO.setScientificName(scientificName);
            buildCropDTO.setCategory(category);
            buildCropDTO.setCropSeason(cropSeason);
            buildCropDTO.setCropImage(base64CropImage);
            buildCropDTO.setFieldCode(fieldCode);

            cropService.saveCrop(buildCropDTO);
            return new ResponseEntity<>("crop saved successfully", HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>("Failed to save crop",HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get all crops
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllCrops(){
        return cropService.getAllCrops();
    }

    //get selected crop
    @GetMapping(value = "/{cropCode}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CropStatus getSelectedCrop(@PathVariable("cropCode") String cropCode){
        return cropService.getCrop(cropCode);
    }

    //delete crop
    @DeleteMapping("/{cropCode}")
    public ResponseEntity<String> deleteCrop(@PathVariable("cropCode") String cropCode){
        try {
            cropService.deleteCrop(cropCode);
            return new ResponseEntity<>("crop deleted successfully",HttpStatus.OK);
        }catch (CropNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
