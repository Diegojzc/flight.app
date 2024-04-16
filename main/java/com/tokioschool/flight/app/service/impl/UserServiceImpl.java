package com.tokioschool.flight.app.service.impl;

import com.tokioschool.flight.app.dto.UserDTO;
import com.tokioschool.flight.app.repository.UserRepository;
import com.tokioschool.flight.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Override
  public Optional<Pair<UserDTO, String>> findUserAndPasswordByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .map(user -> Pair.of(modelMapper.map(user, UserDTO.class), user.getPassword()));
  }

  @Override
  public Optional<UserDTO> findByEmail(String email) {
    return userRepository.findByEmail(email).map(user -> modelMapper.map(user, UserDTO.class));
  }
}
