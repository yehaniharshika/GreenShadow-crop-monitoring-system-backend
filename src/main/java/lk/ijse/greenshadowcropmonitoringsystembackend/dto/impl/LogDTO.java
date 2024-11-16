package lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl;

import jakarta.persistence.Column;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.LogStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogDTO implements LogStatus {
    private String logCode;
    private Date logDate;
    private String logDetails;
    private String observedImage;
    private List<StaffDTO> staffLogs;
    private List<FieldDTO> fieldLogs;
    private List<CropDTO> cropLogs;
}
