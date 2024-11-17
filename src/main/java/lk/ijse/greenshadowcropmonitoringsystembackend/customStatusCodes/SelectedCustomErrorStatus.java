package lk.ijse.greenshadowcropmonitoringsystembackend.customStatusCodes;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelectedCustomErrorStatus implements UserStatus , FieldStatus , StaffStatus , CropStatus , LogStatus , VehicleStatus , EquipmentStatus {
    private int statusCode;
    private String statusMessage;
}
