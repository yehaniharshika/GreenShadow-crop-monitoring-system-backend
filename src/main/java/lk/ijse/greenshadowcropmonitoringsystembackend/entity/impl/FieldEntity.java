package lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "field")
public class FieldEntity implements SuperEntity {
    @Id
    private String fieldCode;
    private String fieldName;
    private Double extentSize;


    private String fieldLocation;

    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage1;

    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage2;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "field_staff_details",
            joinColumns = @JoinColumn(name = "field_code"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private List<StaffEntity> staff;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CropEntity> crops ;

    @ManyToMany(mappedBy = "fieldLogs",cascade = CascadeType.ALL)
    private List<LogsEntity> logs;

    @OneToMany(mappedBy = "field",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipmentEntity> equipments;
}

