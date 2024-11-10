package lk.ijse.greenshadowcropmonitoringsystembackend.customStatusCodes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelectedCustomErrorStatus {
    private int statusCode;
    private String statusMessage;
}
