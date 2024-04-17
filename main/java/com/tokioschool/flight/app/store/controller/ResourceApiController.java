package com.tokioschool.flight.app.store.controller;

import com.tokioschool.flight.app.core.exception.InternalErrorException;
import com.tokioschool.flight.app.core.exception.NotFoundException;
import com.tokioschool.flight.app.store.dto.ResourceContentDTO;
import com.tokioschool.flight.app.store.dto.ResourceCreateRequestDTO;
import com.tokioschool.flight.app.store.dto.ResourceIdDTO;
import com.tokioschool.flight.app.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/api/resources")
@Validated
@Tag(name = "resources", description = "resource operations")
public class ResourceApiController {

    private final StoreService storeService;


    @GetMapping("/{resourceId}")
    @Operation(
            summary = "Get reaource by resourceid",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "resource",
                            content = @Content(schema= @Schema(implementation= ResourceContentDTO.class)))
            }
    )
    public ResponseEntity<ResourceContentDTO> getResourceById(
            @PathVariable(name = "resourceId") UUID resourceId)
    {
        ResourceContentDTO resourceContentDTO = storeService.findResource(resourceId).orElseThrow(
                () -> new NotFoundException("Resource with id:%:s not fount".formatted(resourceId)));

        return ResponseEntity.ok(resourceContentDTO);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResourceIdDTO> createREsource(
            @RequestPart("description")ResourceCreateRequestDTO resourceCreateRequestDTO,
            @RequestPart("content")MultipartFile multipartFile){
        ResourceIdDTO resourceIdDTO = storeService
                .saveResource(multipartFile, resourceCreateRequestDTO.getDescription())
                .orElseThrow(() -> new InternalErrorException("There's been an error, try it again later"));

        return ResponseEntity.status(HttpStatus.CREATED).body(resourceIdDTO);
    }

}
