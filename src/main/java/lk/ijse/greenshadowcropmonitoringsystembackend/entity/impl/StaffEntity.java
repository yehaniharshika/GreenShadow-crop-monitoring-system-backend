package lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl;

import jakarta.persistence.*;

import lk.ijse.greenshadowcropmonitoringsystembackend.entity.Role;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class StaffEntity implements SuperEntity {
    @Id
    @Column(length = 20)
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

    @OneToOne(mappedBy = "staff")
    private UserEntity user;

    @OneToMany(mappedBy = "staff",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicleEntity> vehicles;

    @OneToMany(mappedBy = "staff")
    private List<EquipmentEntity> equipments;

    @ManyToMany
    @JoinTable(
            name = "staffFields_details",
            joinColumns = @JoinColumn(name = "staffId"),
            inverseJoinColumns = @JoinColumn(name = "fieldCode")
    )
    private List<FieldEntity> fields;

    @ManyToMany
    @JoinTable(
            name = "staffLogs_details",
            joinColumns = @JoinColumn(name = "staffId"),
            inverseJoinColumns = @JoinColumn(name = "logCode")
    )
    private List<LogsEntity> logs;
}