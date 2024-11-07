package lk.ijse.greenshadowcropmonitoringsystembackend.entity.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
public class LogsEntity {
    @Id
    private String logCode;
    private Date logDate;
    private String logDetails;
    private String observedImage;
    @ManyToMany(mappedBy = "logs")
    private List<CropEntity> crops;

    @ManyToMany(mappedBy = "logs")
    private List<FieldEntity> fields;

    @ManyToMany(mappedBy = "logs")
    private List<StaffEntity> staffEntities;
}
