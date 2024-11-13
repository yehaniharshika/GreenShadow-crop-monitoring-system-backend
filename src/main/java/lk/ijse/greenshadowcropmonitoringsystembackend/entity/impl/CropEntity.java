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
@Table(name = "crop")
public class CropEntity implements SuperEntity {
    @Id
    private String cropCode;
    private String cropCommonName;
    private String scientificName;
    private String category;
    private String cropSeason;

    @Column(columnDefinition = "LONGTEXT")
    private String CropImage;

    @ManyToMany
    @JoinTable(
            name = "cropLogs_details",
            joinColumns = @JoinColumn(name = "cropCode"),
            inverseJoinColumns = @JoinColumn(name = "logCode")
    )
    private List<LogsEntity> logs;
    
}
