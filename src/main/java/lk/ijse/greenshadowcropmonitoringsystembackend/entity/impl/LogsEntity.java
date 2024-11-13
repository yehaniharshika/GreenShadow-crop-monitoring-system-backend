package lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.SuperEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.CropEntity;
import lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl.FieldEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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

    @ManyToMany(mappedBy = "logs",cascade = CascadeType.ALL)
    private List<CropEntity> crops;

    @ManyToMany(mappedBy = "logs",cascade = CascadeType.ALL)
    private List<FieldEntity> fields;

    @ManyToMany(mappedBy = "logs",cascade = CascadeType.ALL)
    private List<StaffEntity> staffEntities;

}
