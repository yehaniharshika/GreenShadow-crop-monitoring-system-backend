package lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.SuperEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.CropEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.FieldEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "logs")
public class LogsEntity implements SuperEntity {
    @Id
    private String logCode;
    private Date logDate;
    private String logDetails;

    @Column(columnDefinition = "LONGTEXT")
    private String observedImage;

    @ManyToMany
    @JoinTable(
            name = "staff_logs_details",
            joinColumns = @JoinColumn(name = "log_code"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private Set<StaffEntity> staffLogs = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "field_logs_details",
            joinColumns = @JoinColumn(name = "log_id"),
            inverseJoinColumns = @JoinColumn(name = "field_code")
    )
    private Set<FieldEntity> fieldLogs = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "crop_logs_details",
            joinColumns = @JoinColumn(name = "log_id"),
            inverseJoinColumns = @JoinColumn(name = "crop_code")
    )
    private Set<CropEntity> cropLogs = new HashSet<>();
}
