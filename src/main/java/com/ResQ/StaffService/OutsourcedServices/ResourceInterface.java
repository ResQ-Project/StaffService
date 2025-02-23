package com.ResQ.StaffService.OutsourcedServices;

import com.ResQ.StaffService.dtos.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@FeignClient("RESOURCESERVICE")
public interface ResourceInterface {
    @GetMapping("api/v1/resource/getResourceByName/{resourceName}")
    public ResponseDto getResourceByName(@PathVariable String resourceName);

    //update the resources
    @PutMapping("api/v1/resource/updateResource/{resource_id}")
    public ResponseDto updateResource(@PathVariable Integer resource_id, @RequestBody LinkedHashMap<String, Object> resourceDto);

    //create that HR in the resource DB
    @PostMapping("api/v1/resource/saveResource")
    public ResponseDto saveResource(@RequestBody ResourceDto resourceDto);
}

