package lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "vehicle")
public class VehicleEntity implements SuperEntity {
    @Id
    private String vehicleCode;
    private String licensePlateNumber;
    private String category;
    private String fuelType;
    private String status;
    private String remarks;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "staffId",nullable = false)
    private StaffEntity staff;
}