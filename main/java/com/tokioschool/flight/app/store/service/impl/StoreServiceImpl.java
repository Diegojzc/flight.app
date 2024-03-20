package com.tokioschool.flight.app.store.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokioschool.flight.app.store.config.StoreConfigurationProperties;
import com.tokioschool.flight.app.store.domain.ResourceDescription;
import com.tokioschool.flight.app.store.dto.ResourceIdDTO;
import com.tokioschool.flight.app.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        ResourceDescription resourceDescription = ResourceDescription.builder()
                .description(description)
                .contentType(multipartFile.getContentType())
                .filename(multipartFile.getOriginalFilename())
                .size((int) multipartFile.getSize())
                .build();
        ResourceIdDTO resourceIdDTO = ResourceIdDTO.builder().resourceId(UUID.randomUUID()).build();
        Path pathToContent= storeConfigurationProperties.getPath(resourceIdDTO.getResourceId().toString());
        Path pathToDescription= storeConfigurationProperties.getPath(resourceIdDTO.getResourceId() + ".json");

        try{
            Files.write(pathToContent,multipartFile.getBytes());

        }catch (Exception e){
            return Optional.empty();
        }

        try{
            objectMapper.writeValue(pathToDescription.toFile(), resourceDescription);

        }catch (Exception e){
            return Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public Optional<ResourceIdDTO> findResource(UUID resourceId) {
        return Optional.empty();
    }

    @Override
    public void deleteResource(UUID resourceId) {

    }
}
