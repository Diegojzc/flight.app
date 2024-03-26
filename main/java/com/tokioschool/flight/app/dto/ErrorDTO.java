package com.tokioschool.flight.app.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

    private String url;
    private String exception;

}
