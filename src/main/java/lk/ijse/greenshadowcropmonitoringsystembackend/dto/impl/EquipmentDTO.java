package lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.EquipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentDTO implements EquipmentStatus {
    private String equipmentId;
    private String equipmentName;
    private String type;
    private String status;
    private String staffId;
    private String fieldCode;
}
