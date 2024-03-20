package com.tokioschool.flight.app.store.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ResourceContentDTO{

    UUID resourceId;
    byte[] content;
    String contentType;
    String filename;
    int size;
}
