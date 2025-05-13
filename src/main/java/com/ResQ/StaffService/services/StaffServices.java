package com.ResQ.StaffService.services;

import com.ResQ.StaffService.OutsourcedServices.ResourceDto;
import com.ResQ.StaffService.OutsourcedServices.ResourceInterface;
import com.ResQ.StaffService.dtos.StaffDto;
import com.ResQ.StaffService.entities.Staff;
import com.ResQ.StaffService.repo.StaffRepo;
import com.ResQ.StaffService.utils.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.*;

@Service
@Transactional
public class StaffServices {

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ResourceInterface resourceInterface;

    //get list of all available staff
    public List<StaffDto> getAllStaff(){
        List<Staff> staffList = staffRepo.findAll();
        return modelMapper.map(staffList, new TypeToken<ArrayList<StaffDto>>(){}.getType());
    }
    //get all available doctors
    public List<StaffDto> getAllDoctors(){
        List<Staff> doctorsList = staffRepo.getAllDoctors();
        return modelMapper.map(doctorsList, new TypeToken<ArrayList<StaffDto>>(){}.getType());
    }

    //create a staff member
    public String createStaffMember(StaffDto staffData){
        if(staffRepo.existsById(staffData.getStaff_id())){
            return "Staff_Member_Exist_With_That_ID";
        }else{
            //updating the resource table
            String occupation = staffData.getOccupation();
            Object existingResource = resourceInterface.getResourceByName(occupation).getData();

            if(existingResource == null){
                //if that occupation category does not exist in the resource table create that resource category (HR)
                ResourceDto newResource = new ResourceDto();
                newResource.setResource_id((int)(Math.random() * 101));  //set a new id
                newResource.setAvailableUnits(1);
                newResource.setFullCount(1);
                newResource.setCategory(occupation);
                resourceInterface.saveResource(newResource);

                //saving the staff member in the staff database
                staffRepo.save(modelMapper.map(staffData, Staff.class));
                return "SUCCESS";

            }else{
                //if that resource is existing just update it
                LinkedHashMap<String, Object> mappedData = (LinkedHashMap<String, Object>) existingResource;
                if(mappedData.containsKey("fullCount")){
                    int count = (Integer) mappedData.get("fullCount");
                    mappedData.put("fullCount", count + 1);  //increase the count when adding a HR
                    resourceInterface.updateResource((Integer) mappedData.get("resource_id"), mappedData);

                    //saving the staff member in the staff database
                    staffRepo.save(modelMapper.map(staffData, Staff.class));
                    return "SUCCESS";
                }else{
                    return "Error";
                }
            }
        }
    }

    //update staff member [patch update]
    public String updateStaffMember(Integer staffId, Map<String, Object> updates){
        if(staffRepo.existsById(staffId)){
            Staff existingStaffMember = staffRepo.findById(staffId).get();

            if(existingStaffMember == null){
                return "Staff_Member_with_that_ID_is_not_exists";
            }else{
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
                return "SUCCESS";
            }



        }else{
            //no staff member associated with that entity
            return "Error";
        }
    }


}
