package lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldDTO {
    private String fieldCode;
    private String fieldName;
    private Double extentSize;
    private String fieldLocation;
    private String fieldImage1;
    private String fieldImage2;
}