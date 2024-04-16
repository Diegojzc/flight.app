package com.tokioschool.flight.app.repository;

import com.tokioschool.flight.app.domain.Airport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest(
    properties = {
      "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MYSQL;DATABASE_TO_LOWER=TRUE;",
      "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
      "spring.jpa.hibernate.ddl-auto:create-drop"
    })
class AirportRepositoryTest {

  @Autowired private AirportRepository airportDao;

  @BeforeEach
  public void BeforeEach() {

    Airport bcn = Airport.builder().acronym("BCN").name("Barcelona").country("Spain").build();

    Airport gla =
        Airport.builder().acronym("GLA").name("Glasgow Airport").country("United Kingdom").build();
    airportDao.save(bcn);
    airportDao.save(gla);
  }

  @Test
  void givenAirports_whenFindAll_thenReturnOk() {
    List<Airport> airports = airportDao.findAll();

    Assertions.assertEquals(2, airports.size());
  }
  @Test
  void givenAiports_whenFindBcnAiport_thenReturnOk(){

    Optional<Airport> maybeBcn= airportDao.findByAcronym("BCN");

    Assertions.assertTrue(maybeBcn.isPresent());
    Assertions.assertNotNull(maybeBcn.get().getAcronym());
  }

}
