package com.tokioschool.flight.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@DataJdbcTest
class AirportDaoTest {

    @Autowired
    private AirportDao airportDao;
}