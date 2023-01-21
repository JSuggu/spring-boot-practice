package com.example.customerservice.feignclient;

import com.example.customerservice.model.Bike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="bike-service")
public interface BikeFeignClient {

    @PostMapping("/bike")
    Bike save(@RequestBody Bike bike);

    @GetMapping("/bike/byuser/{userId}")
    List<Bike> getBikes(@PathVariable("userId") int userId);
}
