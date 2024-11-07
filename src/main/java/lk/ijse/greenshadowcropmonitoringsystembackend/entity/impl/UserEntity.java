package lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl;

import jakarta.persistence.*;

import lk.ijse.greenshadowcropmonitoringsystembackend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "staffId")
    private StaffEntity staff;
}
