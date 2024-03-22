package com.tokioschool.flight.app.service.impl;

import com.tokioschool.flight.app.domain.FlightImage;
import com.tokioschool.flight.app.dto.FlightImageResourceDTO;
import com.tokioschool.flight.app.service.FlightImageService;
import com.tokioschool.flight.app.store.dto.ResourceContentDTO;
import com.tokioschool.flight.app.store.dto.ResourceIdDTO;
import com.tokioschool.flight.app.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightImageServiceImpl implements FlightImageService {

  private final StoreService storeService;

  @Override
  public FlightImage saveImage(MultipartFile multipartFile) {
    ResourceIdDTO resourceIdDTO =
        storeService
            .saveResource(multipartFile, "app")
            .orElseThrow(() -> new IllegalStateException("Resource not saved in store"));

    return FlightImage.builder()
        .contentType(multipartFile.getContentType())
        .size((int) multipartFile.getSize())
        .resourceId(resourceIdDTO.getResourceId())
        .filename(multipartFile.getOriginalFilename())
        .build();
  }

  @Override
  public FlightImageResourceDTO getImage(UUID resourceId) {
    ResourceContentDTO resourceContentDTO =
        storeService
            .findResource(resourceId)
            .orElseThrow(() -> new IllegalStateException("Resource not found in store"));

    return FlightImageResourceDTO.builder()
        .content(resourceContentDTO.getContent())
        .contentType(resourceContentDTO.getContentType())
        .filename(resourceContentDTO.getFilename())
        .size(resourceContentDTO.getSize())
        .build();
  }

  @Override
  public void deleteImage(UUID resourceId) {
    storeService.deleteResource(resourceId);
  }
}
