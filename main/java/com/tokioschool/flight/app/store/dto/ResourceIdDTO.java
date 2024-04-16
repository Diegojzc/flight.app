package com.tokioschool.flight.app.store.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ResourceIdDTO {
    UUID resourceId;
}
