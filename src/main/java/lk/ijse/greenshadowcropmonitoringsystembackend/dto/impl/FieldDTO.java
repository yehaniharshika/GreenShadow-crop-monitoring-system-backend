package lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.FieldStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldDTO implements FieldStatus {
    private String fieldCode;
    private String fieldName;
    private double extentSize;
    private String fieldLocation;
    private String fieldImage1;
    private String fieldImage2;
    private List<String> staff; //Many-To-Many relationship with Staff
}
