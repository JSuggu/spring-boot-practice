package com.example.customerservice.service;

import com.example.customerservice.entity.Customer;
import com.example.customerservice.feignclient.BikeFeignClient;
import com.example.customerservice.feignclient.CarFeignClient;
import com.example.customerservice.model.Bike;
import com.example.customerservice.model.Car;
import com.example.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CarFeignClient carFeignClient;

    @Autowired
    BikeFeignClient bikeFeignClient;

    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    public Customer getById(int id){
        return customerRepository.findById(id).orElse(null);
    }

    public Customer save(Customer customer){
        Customer customerNew = customerRepository.save(customer);
        return customerNew;
    }

    public List<Car> getCars(int userId){
        List<Car> cars = restTemplate.getForObject("http://localhost:8002/car/byuser/"+userId, List.class);
        return cars;
    }

    public List<Bike> getBikes(int userId){
        List<Bike> bikes = restTemplate.getForObject("http://localhost:8003/bike/byuser/"+userId, List.class);
        return bikes;
    }

    public Car saveCar(int userId, Car car){
        car.setUserId(userId);
        Car carNew = carFeignClient.save(car);
        return carNew;
    }

    public Bike saveBike(int userId, Bike bike){
        bike.setUserId(userId);
        Bike bikeNew = bikeFeignClient.save(bike);
        return bikeNew;
    }

    public Map<String, Object> getUserAndVehicles(int userId){
        Map<String, Object> result = new HashMap<>();
        Customer customer = customerRepository.findById(userId).orElse(null);
        if(customer == null) {
            result.put("Mensaje", "No existe el usuario");
            return result;
        }
        result.put("Customer",customer);

        List<Car> cars = carFeignClient.getCars(userId);
        if(cars.isEmpty())
            result.put("Cars", "Ese usuario no tiene coches");
        else
            result.put("Cars", cars);

        List<Bike> bikes = bikeFeignClient.getBikes(userId);
        if(bikes.isEmpty())
            result.put("Bikes", "Ese usuario no tiene bicis");
        else
            result.put("Bikes", bikes);
        return result;
    }
}
