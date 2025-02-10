package com.ResQ.StaffService.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table (name = "staff")
public class Staff {
    @Id
    private Integer staff_id;
    private String national_id;
    private String first_name;
    private String last_name;
    private int age;
    private String occupation;
    private String speciality;
    private int assignedPatientCount = 0;
    @ElementCollection
    List <Integer> patientIds = new ArrayList<>();  //assigned patients
}
