package com.ResQ.StaffService.repo;

import com.ResQ.StaffService.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepo extends JpaRepository<Staff, Integer> {
}
