package lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lk.ijse.greenshadowcropmonitoringsystembackend.entity.Role;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.SuperEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "staff")
public class StaffEntity implements SuperEntity {
    @Id
    private String staffId;

    private String firstName;
    private String lastName;

    @Temporal(TemporalType.DATE)
    private Date DOB;

    private String gender;
    private String designation;

    @Temporal(TemporalType.DATE)
    private Date joinedDate;

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String contactNumber;

    @OneToOne(mappedBy = "staff",cascade = CascadeType.ALL, orphanRemoval = true)
    private UserEntity user;

    @OneToMany(mappedBy = "staff",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleEntity> vehicles;

    @OneToMany(mappedBy = "staff",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<EquipmentEntity> equipments;


    @ManyToMany(mappedBy = "staff",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<FieldEntity> fields;

    @ManyToMany(mappedBy = "staffLogs",cascade = CascadeType.ALL)
    private List<LogsEntity> logs;
}