package com.ResQ.StaffService.services;

import com.ResQ.StaffService.dtos.StaffDto;
import com.ResQ.StaffService.entities.Staff;
import com.ResQ.StaffService.repo.StaffRepo;
import com.ResQ.StaffService.utils.VarList;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class StaffServices {

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private ModelMapper modelMapper;

    //create a staff member
    public String createStaffMember(StaffDto staffData){
        if(staffRepo.existsById(staffData.getStaff_id())){
            return VarList.RSP_DUPLICATE;
        }else{
            staffRepo.save(modelMapper.map(staffData, Staff.class));
            return VarList.RSP_SUCCESS;
        }
    }

    //update staff member [patch update]
    public String updateStaffMember(Integer staffId, Map<String, Object> updates){
        if(staffRepo.existsById(staffId)){
            Staff existingStaffMember = staffRepo.findById(staffId).get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "national_id" -> existingStaffMember.setNational_id((String) value);
                    case "first_name" -> existingStaffMember.setFirst_name((String) value);
                    case "last_name" -> existingStaffMember.setLast_name((String) value);
                    case "age" -> existingStaffMember.setAge((Integer) value);
                    case "occupation" -> existingStaffMember.setOccupation((String) value);
                    case "speciality" -> existingStaffMember.setSpeciality((String) value);
                    case "patientIds" -> {
                        if (value instanceof List<?>) {
                            //patientIDs coming with the update request
                            List<Integer> newPatientIds = ((List<?>) value).stream()
                                    .filter(obj -> obj instanceof Integer)  // Ensure only integers
                                    .map(obj -> (Integer) obj)
                                    .toList();

                            // Retrieve existing patient IDs of that particular doctor
                            List<Integer> existingPatientIds = existingStaffMember.getPatientIds();

                            // Append new IDs without duplicates to existing patients array
                            for (Integer newId : newPatientIds) {
                                if (!existingPatientIds.contains(newId)) {
                                    existingPatientIds.add(newId);
                                }
                            }

                            // Update staff entity
                            existingStaffMember.setPatientIds(existingPatientIds);
                            existingStaffMember.setAssignedPatientCount(existingPatientIds.size()); // Update patient count
                        }
                    }

                }
            });

            staffRepo.save(existingStaffMember);
            return VarList.RSP_SUCCESS;

        }else{
            //no staff member associated with that entity
            return VarList.RSP_ERROR;
        }
    }


}
