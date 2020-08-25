package com.java.messenger.api;

import com.java.messenger.dto.AuthEnum;
import com.java.messenger.dto.AuthenticatedDTO;
import com.java.messenger.dto.Credentials;
import com.java.messenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseBody
    @PostMapping
    public AuthenticatedDTO authenticate(@RequestBody @Validated Credentials credentials) throws EntityNotFoundException {
        return userService.authenticate(credentials);
    }
    @ResponseBody
    @PostMapping("/registration/check")
    public AuthEnum checkPresence(@RequestBody @Validated Credentials credentials) {
        return userService.checkIfAccountExist(credentials);
    }
}
