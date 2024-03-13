package com.tokioschool.flight.app.repository;



import com.tokioschool.flight.app.domain.Airport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AirportDao extends JpaRepository<Airport,String> {
}
