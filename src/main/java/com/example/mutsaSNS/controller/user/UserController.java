package com.example.mutsaSNS.controller.user;

import com.example.mutsaSNS.domain.Response;
import com.example.mutsaSNS.dto.user.request.UserJoinRequestDto;
import com.example.mutsaSNS.dto.user.request.UserUpdateRequestDto;
import com.example.mutsaSNS.dto.user.response.UserJoinResponseDto;
import com.example.mutsaSNS.dto.user.response.UserUpdateResponseDto;
import com.example.mutsaSNS.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponseDto> createUser(@RequestBody UserJoinRequestDto jonDto) {
        return Response.success(userService.createUser(jonDto));
    }

    /**
     * 유저 이미지 + 정보 같이 수정
     */
    @PutMapping("/user/image")
    public Response<UserUpdateResponseDto> updateProfileImage(final UserUpdateRequestDto updateDto,
                                                              final Authentication authentication) throws IOException {
        Long userId = Long.parseLong(authentication.getName());
        return Response.success(userService.updateUser(updateDto, userId));
    }

    /**
     * 유저 이미지만 수정 -> 테스트 코드 작성 완료
     */
    @RequestMapping(value = "/image", method = {RequestMethod.POST, RequestMethod.PUT})
    public Response<UserUpdateResponseDto> updateProfileImage(@RequestPart(name = "updateDto") MultipartFile updateDto,
                                                              final Authentication authentication) throws IOException {
        Long userId = Long.parseLong(authentication.getName());
        return Response.success(userService.updateImage(updateDto, userId));
    }
}
