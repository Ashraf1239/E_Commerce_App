package com.ecommerce.library.service;

import com.ecommerce.library.model.City;
import com.ecommerce.library.repository.CityRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private  CityRepositry cityRepositry;
    public List<City> findAll(){
        return cityRepositry.findAll();
    }

}
