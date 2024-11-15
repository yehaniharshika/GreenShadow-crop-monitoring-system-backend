package lk.ijse.greenshadowcropmonitoringsystembackend.customStatusCodes;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.CropStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.FieldStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.StaffStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelectedCustomErrorStatus implements UserStatus , FieldStatus , StaffStatus , CropStatus {
    private int statusCode;
    private String statusMessage;
}
