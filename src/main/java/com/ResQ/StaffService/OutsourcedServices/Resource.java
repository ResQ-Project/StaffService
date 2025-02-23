package com.ResQ.StaffService.OutsourcedServices;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table (name = "resources")

public class Resource {
    @Id
    @Column(name = "resource_id")
    private Integer resource_id;
    private String category;
    private Integer fullCount;
    private Integer availableUnits;
}
