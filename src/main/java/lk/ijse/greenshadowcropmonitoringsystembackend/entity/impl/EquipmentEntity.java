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
@Table(name = "equipment")
public class EquipmentEntity implements SuperEntity {
    @Id
    private String equipmentId;
    private String equipmentName;
    private String type;
    private String status;

    @ManyToOne
    @JoinColumn(name = "staff_id",nullable = false)
    private StaffEntity staff;

    @ManyToOne
    @JoinColumn(name = "field_code",nullable = false)
    private FieldEntity field;

}
