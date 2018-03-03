package com.romanov.springjdbcdemo.repositories;



import com.romanov.springjdbcdemo.model.City;

import java.util.List;

public interface CityRepository {
    List<City> getAllCities();

    City getCityById(long id);

    long addCity(City city);

    void updateCity(City city);

    void removeCityById(long id);

    void removeCityByName(String name);

    List<City> getCitiesFromCountry(String countryCode);
}
