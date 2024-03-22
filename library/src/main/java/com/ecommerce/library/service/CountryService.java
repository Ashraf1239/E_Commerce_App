package com.ecommerce.library.service;

import com.ecommerce.library.model.Country;
import com.ecommerce.library.repository.CountryRepostry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CountryService {
    @Autowired
private CountryRepostry countryRepostry;

public List<Country> findAll(){

    return countryRepostry.findAll();
}

}
