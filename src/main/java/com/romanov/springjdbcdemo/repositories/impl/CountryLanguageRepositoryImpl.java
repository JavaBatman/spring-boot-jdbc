package com.romanov.springjdbcdemo.repositories.impl;

import com.romanov.springjdbcdemo.model.CountryLanguage;
import com.romanov.springjdbcdemo.repositories.CountryLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CountryLanguageRepositoryImpl implements CountryLanguageRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public List<CountryLanguage> getCountryLanguage(String countryCode) {
        String sqlQuery = "Select CountryCode, Language, IsOfficial, Percentage From countryLanguage Where countryCode = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query(sqlQuery, new Object[]{countryCode}, new RowMapper<CountryLanguage>() {
            @Override
            public CountryLanguage mapRow(ResultSet rs, int i) throws SQLException {
                CountryLanguage countryLanguage = new CountryLanguage();
                countryLanguage.setCountryCode(rs.getString("CountryCode"));
                countryLanguage.setLanguage(rs.getString("Language"));
                countryLanguage.setOfficial(isOfficial(rs.getString("IsOfficial")));
                countryLanguage.setPercentage(rs.getDouble("Percentage"));
                return countryLanguage;
            }
        });
    }

    private boolean isOfficial(String isOfficial) {
        switch (isOfficial) {
            case "T": {
                return true;
            }
            case "F": {
                return false;
            }
            default: {
                return false;
            }
        }
    }
}
