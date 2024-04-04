package com.tokioschool.flight.app.service;

import com.tokioschool.flight.app.dto.UserDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public interface UserService {
    Optional<Pair<UserDTO, String>> findUserAndPasswordByEmail(String email);

    Optional<UserDTO> findByEmail(String email);

}
