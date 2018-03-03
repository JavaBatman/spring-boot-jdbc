package com.romanov.springjdbcdemo.repositories;

import com.romanov.springjdbcdemo.model.CountryLanguage;

import java.util.List;

public interface CountryLanguageRepository {
    List<CountryLanguage> getCountryLanguage(String countryCode);
}
