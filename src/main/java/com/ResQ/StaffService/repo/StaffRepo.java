package com.ResQ.StaffService.repo;

import com.ResQ.StaffService.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StaffRepo extends JpaRepository<Staff, Integer> {
    //find all doctors
    @Query("SELECT s FROM Staff s WHERE s.occupation = 'doctor'")
    List<Staff> getAllDoctors();
}
