package com.tokioschool.flight.app.email.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDTO {
    String fileName;
    String contentType;
    byte[] content;
}
