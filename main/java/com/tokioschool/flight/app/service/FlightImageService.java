package com.tokioschool.flight.app.service;

import com.tokioschool.flight.app.domain.FlightImage;
import com.tokioschool.flight.app.dto.FlightImageResourceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FlightImageService {

    FlightImage saveImage(MultipartFile multipartFile);

    FlightImageResourceDTO getImage(UUID resourceId);

    void deleteImage(UUID resourceId);
}
