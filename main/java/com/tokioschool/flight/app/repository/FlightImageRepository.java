package com.tokioschool.flight.app.repository;

import com.tokioschool.flight.app.domain.FlightImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightImageRepository extends CrudRepository<FlightImage, Long> {

}
