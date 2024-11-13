package lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Column(columnDefinition = "POINT")
    private String fieldLocation;

    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage1;

    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage2;

    @ManyToMany(mappedBy = "fields")
    private List<StaffEntity> staff;

    @ManyToMany
    @JoinTable(
            name = "fieldLogs_details",
            joinColumns = @JoinColumn(name = "fieldCode"),
            inverseJoinColumns = @JoinColumn(name = "logCode")
    )
    private List<LogsEntity> logs;

    @OneToMany(mappedBy = "field")
    private List<EquipmentEntity> equipments;
}
