package com.tokioschool.flight.app.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {

    private String id;
    private String name;
    private String surname;
    private String email;
    private LocalDateTime lastLogin;
    private List<String> roles;
}
