package lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "vehicle")
public class VehicleEntity {
    @Id
    private String vehicleCode;
    private String licensePlateNumber;
    private String category;
    private String fuelType;
    private String remarks;
    @ManyToOne
    @JoinColumn(name = "staffId",nullable = false)
    private StaffEntity staff;
}