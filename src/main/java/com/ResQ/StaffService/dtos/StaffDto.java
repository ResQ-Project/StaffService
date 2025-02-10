package com.ResQ.StaffService.dtos;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {
    private Integer staff_id;
    private String national_id;
    private String first_name;
    private String last_name;
    private int age;
    private String occupation;
    private String speciality;
    private int assignedPatientCount;
    List<Integer> patientIds;  //assigned patients
}
