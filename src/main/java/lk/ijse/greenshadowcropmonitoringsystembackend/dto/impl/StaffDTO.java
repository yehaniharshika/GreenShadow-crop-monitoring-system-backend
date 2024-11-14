package lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl;

import jakarta.persistence.*;
import lk.ijse.greenshadowcropmonitoringsystembackend.dto.StaffStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaffDTO implements StaffStatus {
    private String staffId;
    private String firstName;
    private String lastName;
    private Date DOB;
    private String gender;
    private String designation;
    private Date joinedDate;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String email;
    private Role role;
    private String contactNumber;
}
