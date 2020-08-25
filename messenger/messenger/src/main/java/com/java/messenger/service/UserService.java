package com.java.messenger.service;

import com.java.messenger.dao.UserDAO;
import com.java.messenger.dto.AuthEnum;
import com.java.messenger.dto.AuthenticatedDTO;
import com.java.messenger.dto.Credentials;
import com.java.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public AuthenticatedDTO authenticate(Credentials credentials) {
        log.info("Requested registration with credentials: {}", credentials);
        var user = checkAccount(credentials);
        if (user.isEmpty()) {
            log.info("User with credentials: {} not found!", credentials);
            var userDAO = UserDAO.builder()
                    .userName(credentials.getUsername())
                    .password(credentials.getPassword())
                    .registeredDate(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
            var saved = userRepository.saveAndFlush(userDAO);
            log.info("User {} successfully created", credentials);
            return AuthenticatedDTO.builder()
                    .userId(saved.getId())
                    .status(AuthEnum.REGISTERED)
                    .build();
        }
        return AuthenticatedDTO.builder()
                .userId(user.get().getId())
                .status(AuthEnum.AUTHENTICATED)
                .build();
    }


    public AuthEnum checkIfAccountExist(Credentials credentials) {
        var account = checkAccount(credentials);
        if (account.isEmpty()) {
            return AuthEnum.UNREGISTERED;
        } else return AuthEnum.REGISTERED;
    }

    private Optional<UserDAO> checkAccount(Credentials credentials) {
        log.info("Requested login with credentials: {}", credentials);
        var userName = credentials.getUsername();
        var pass = credentials.getPassword();
        return userRepository.findByUserNameAndPassword(userName, pass);
    }
}
