package lk.ijse.greenshadowcropmonitoringsystembackend.dto.impl;

import lk.ijse.greenshadowcropmonitoringsystembackend.dto.UserStatus;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO implements UserStatus {
    private String email;
    private String name;
    private String password;
    private Role role;
}
