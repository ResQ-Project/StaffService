package com.ResQ.StaffService.controllers;

import com.ResQ.StaffService.dtos.ResponseDto;
import com.ResQ.StaffService.dtos.StaffDto;
import com.ResQ.StaffService.services.StaffServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {

    @Autowired
    private StaffServices staffServices;

    @Autowired
    private ResponseDto responseDto;


    @PostMapping("/createStaffMember")
    public ResponseDto createStaffMember(@RequestBody StaffDto staffDto){
        String res = staffServices.createStaffMember(staffDto);
        if(res.equals("00")){
            responseDto.setStatus_code("201");
            responseDto.setMessage("Staff member saved successfully");
            responseDto.setData(staffDto);
        }else if(res.equals("02")){
            responseDto.setStatus_code("400");
            responseDto.setMessage("Staff member Already Exists with that National ID");
            responseDto.setData(staffDto);
        } else{
            responseDto.setStatus_code("400");
            responseDto.setMessage("Error");
            responseDto.setData(null);
        }

        return responseDto;
    }

    @PutMapping("/updateStaffMember/{staffId}")
    public ResponseDto updateStaffMember(@PathVariable Integer staffId, @RequestBody Map<String, Object> updates){
        String res = staffServices.updateStaffMember(staffId, updates);
        if(res.equals("00")){
            responseDto.setStatus_code("201");
            responseDto.setMessage("Staff member saved successfully");
            responseDto.setData(null);
        }else if(res.equals("02")){
            responseDto.setStatus_code("400");
            responseDto.setMessage("Staff member Already Exists with that National ID");
            responseDto.setData(null);
        } else{
            responseDto.setStatus_code("400");
            responseDto.setMessage("Error");
            responseDto.setData(null);
        }
        return responseDto;
    }
}
