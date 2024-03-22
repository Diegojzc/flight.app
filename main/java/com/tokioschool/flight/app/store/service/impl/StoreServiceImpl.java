package com.tokioschool.flight.app.store.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokioschool.flight.app.store.config.StoreConfigurationProperties;
import com.tokioschool.flight.app.store.domain.ResourceDescription;
import com.tokioschool.flight.app.store.dto.ResourceContentDTO;

import com.tokioschool.flight.app.store.dto.ResourceIdDTO;
import com.tokioschool.flight.app.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

  private final StoreConfigurationProperties storeConfigurationProperties;
  private final ObjectMapper objectMapper;


  @Override
  public Optional<ResourceIdDTO> saveResource(MultipartFile multipartFile, String description) {
      ResourceDescription resourceDescription =
              ResourceDescription.builder()
                      .description(description)
                      .contentType(multipartFile.getContentType())
                      .filename(multipartFile.getOriginalFilename())
                      .size((int) multipartFile.getSize())
                      .build();
      ResourceIdDTO resourceIdDTO = ResourceIdDTO.builder().resourceId(UUID.randomUUID()).build();
      Path pathToContent =
              storeConfigurationProperties.getPath(resourceIdDTO.getResourceId().toString());
      Path pathToDescription =
              storeConfigurationProperties.getPath(resourceIdDTO.getResourceId() + ".json");

      try {
        Files.write(pathToContent, multipartFile.getBytes());

      } catch (IOException e) {
        return Optional.empty();
      }

      try {
        objectMapper.writeValue(pathToDescription.toFile(), resourceDescription);

      } catch (IOException e) {
        return Optional.empty();
      }

      return Optional.of(resourceIdDTO);
    }


  @Override
  public Optional<ResourceContentDTO> findResource(UUID resourceId) {
    Path pathToContent = storeConfigurationProperties.getPath(resourceId.toString());
    Path pathTodescription = storeConfigurationProperties.getPath(resourceId + ".json");

    if (!Files.exists(pathToContent)) {
      return Optional.empty();
    }
    byte[] bytes;
    try {
      bytes = Files.readAllBytes(pathToContent);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ResourceDescription resourceDescription;
    try {
      resourceDescription =
          objectMapper.readValue(pathTodescription.toFile(), ResourceDescription.class);

    } catch (IOException e) {
      return Optional.empty();
    }

    return Optional.of(
        ResourceContentDTO.builder()
            .resourceId(resourceId)
            .content(bytes)
            .contentType(resourceDescription.getContentType())
            .description(resourceDescription.getDescription())
            .filename(resourceDescription.getFilename())
            .size(resourceDescription.getSize())
            .build());
  }

  @Override
  public void deleteResource(UUID resourceId) {
    Path pathFromContent = storeConfigurationProperties.getPath(resourceId.toString());
    Path pathFromDescription = storeConfigurationProperties.getPath(resourceId + ".json");
     try{
       Files.delete(pathFromContent);
       Files.delete(pathFromDescription);
     }catch (IOException e){

     }

  }
}
