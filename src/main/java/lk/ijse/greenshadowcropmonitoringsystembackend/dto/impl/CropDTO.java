package lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.CropStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CropDTO implements CropStatus {
    private String cropCode;
    private String cropCommonName;
    private String scientificName;
    private String category;
    private String cropSeason;
    private String CropImage;
    private String fieldCode;
}
