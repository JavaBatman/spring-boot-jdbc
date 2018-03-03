package com.romanov.springjdbcdemo;


import com.romanov.springjdbcdemo.model.City;
import com.romanov.springjdbcdemo.repositories.CityRepository;
import com.romanov.springjdbcdemo.repositories.CountryLanguageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringJdbcDemoApplication {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringJdbcDemoApplication.class, args);
        CityRepository cityRepository = applicationContext.getBean(CityRepository.class);
        CountryLanguageRepository countryLanguageRepository = applicationContext.getBean(CountryLanguageRepository.class);

        System.out.println("Все города мира");
        cityRepository.getAllCities().forEach(System.out::println);

        System.out.println("Все города России");
        cityRepository.getCitiesFromCountry("RUS").forEach(System.out::println);
        
        long mozhgaId = cityRepository.addCity(new City("Mozhga", "RUS", "Udmurtia", 90000L));
        System.out.println("Добавили Можгу");

        City mozhga = cityRepository.getCityById(mozhgaId);
        mozhga.setDistrict("Udmurt Republic");

        cityRepository.updateCity(mozhga);
        System.out.println("Обновили Можгу");

        cityRepository.removeCityByName("Mozhga");
        System.out.println("Удалили Можгу");

        System.out.println("Все языки на территории России");
        countryLanguageRepository.getCountryLanguage("RUS").forEach(System.out::println);
    }
}
