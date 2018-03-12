package com.romanov.springjdbcdemo.repositories.impl;


import com.romanov.springjdbcdemo.model.City;
import com.romanov.springjdbcdemo.repositories.CityRepository;
import com.romanov.springjdbcdemo.util.mappers.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * created by Kirill_Romanov1 on 02/28/2018
 */
@Repository
public class CityRepositoryImpl implements CityRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<City> getAllCities() {
        return jdbcTemplate.query("Select * From city", new CityMapper());
    }

    @Override
    public City getCityById(long id) {
        return jdbcTemplate.queryForObject("Select * From city Where id = ?", new Object[]{id}, new CityMapper());
    }

    @Override
    public long addCity(City city) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", city.getName())
                .addValue("countryCode", city.getCountryCode())
                .addValue("district", city.getDistrict())
                .addValue("population", city.getPopulation());
        namedParameterJdbcTemplate.update("Insert into world.city (Name, CountryCode, District, Population) Values (:name,:countryCode,:district,:population)", sqlParameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void updateCity(City city) {
        jdbcTemplate.update("Update city Set Name = ?, CountryCode = ?, District = ?, Population = ? Where ID = ?",
                new Object[]{city.getName(), city.getCountryCode(), city.getDistrict(), city.getPopulation(), city.getId()});
    }

    @Override
    public void removeCityById(long id) {
        jdbcTemplate.update("Delete From city Where id = ?", id);
    }

    @Override
    public void removeCityByName(String name) {
        jdbcTemplate.update("Delete From city Where Name = ?", name);
    }

    @Override
    public List<City> getCitiesFromCountry(String countryCode) {
        String sqlQuery = "Select ID, city.Name, city.CountryCode, city.District,city.Population " +
                "From city join country on city.CountryCode = country.Code " +
                "Where city.countryCode = :countryCode";
        SqlParameterSource namedParameters = new MapSqlParameterSource("countryCode", countryCode);
        return namedParameterJdbcTemplate.query(sqlQuery, namedParameters, new CityMapper());
    }


}
