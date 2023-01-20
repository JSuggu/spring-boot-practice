package com.example.customerservice.controller;

import com.example.customerservice.entity.Customer;
import com.example.customerservice.model.Bike;
import com.example.customerservice.model.Car;
import com.example.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAll(){
        List<Customer> customers = customerService.getAll();
        if(customers.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable("id") int id) {
        Customer customer = customerService.getById(id);
        if(customer == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(customer);
    }

    @PostMapping()
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        Customer customerNew = customerService.save(customer);
        return ResponseEntity.ok(customerNew);
    }

    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId){
        Customer customer = customerService.getById(userId);
        if(customer == null)
            return ResponseEntity.notFound().build();
        List<Car> cars = customerService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userId){
        Customer customer = customerService.getById(userId);
        if(customer == null)
            return ResponseEntity.notFound().build();
        List<Bike> bikes = customerService.getBikes(userId);
        return ResponseEntity.ok(bikes);
    }

    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId, @RequestBody Car car){
        if(customerService.getById(userId) == null)
            return ResponseEntity.notFound().build();
        Car carNew = customerService.saveCar(userId, car);
        return ResponseEntity.ok(carNew);
    }

    @PostMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId, @RequestBody Bike bike){
        if(customerService.getById(userId) == null)
            return ResponseEntity.notFound().build();
        Bike bikeNew = customerService.saveBike(userId, bike);
        return ResponseEntity.ok(bikeNew);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable("userId") int userId){
        Map<String, Object> result = customerService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);
    }

}
