package com.tokioschool.flight.app.repository;

import com.tokioschool.flight.app.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {

    Optional<Airport> findByAcronym(String acronym);
}

