package com.example.mutsaSNS.controller.user;

import com.example.mutsaSNS.domain.Response;
import com.example.mutsaSNS.dto.user.request.UserJoinRequestDto;
import com.example.mutsaSNS.dto.user.response.UserJoinResponseDto;
import com.example.mutsaSNS.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponseDto> createUser(@RequestBody UserJoinRequestDto jonDto) {
        return Response.success(userService.createUser(jonDto));
    }
}
