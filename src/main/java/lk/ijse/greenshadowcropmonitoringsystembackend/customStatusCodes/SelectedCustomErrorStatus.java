package lk.ijse.greenshadowcropmonitoringsystembackend.customStatusCodes;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.FieldStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelectedCustomErrorStatus implements UserStatus , FieldStatus {
    private int statusCode;
    private String statusMessage;
}
