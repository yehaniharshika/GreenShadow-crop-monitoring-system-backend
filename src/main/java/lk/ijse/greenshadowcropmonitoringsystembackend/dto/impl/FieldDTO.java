package lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.FieldStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldDTO implements FieldStatus {
    private String fieldCode;
    private String fieldName;
    private Double extentSize;
    private String fieldLocation;
    private String fieldImage1;
    private String fieldImage2;
    private Set<String> staffIds; //Many-To-Many relationship with Staff
}
