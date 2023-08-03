package com.example.mutsaSNS.controller.user;

import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import com.example.mutsaSNS.dto.user.request.UserJoinRequestDto;
import com.example.mutsaSNS.dto.user.request.UserUpdateRequestDto;
import com.example.mutsaSNS.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class})
@Transactional
@SpringBootTest
class UserControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private ObjectMapper objectMapper = new ObjectMapper();
    Authentication authentication;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    @DisplayName("회원가입 API 테스트")
    @Test
    void createUserApi() throws Exception {
        // given
        final String username = "아이디";
        final String password = "비밀번호";
        UserJoinRequestDto joinDto = createUserJoinDtoWithUsernameAndPassword(username, password);
        String url = "http://localhost:8080/join";

        // when
        ResultActions perform = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(joinDto)));

        // then
        perform.andExpect(status().isOk())
                .andDo(document("/join",
                        requestFields(
                                fieldWithPath("username").description("아이디"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phone").description("번호")

                        )));
    }

    @DisplayName("회원 정보 수정(온리 이미지) Api 테스트")
    @Test
    void updateUser() throws Exception {
        // given
        final String username = "아이디";
        final String password = "비밀번호";
        User user = createUser(username, password);
        UserUpdateRequestDto dto = UserUpdateRequestDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        String url = "http://localhost:8080/image";

        // when
        ResultActions perform = mvc.perform(multipart(url)
                .file(new MockMultipartFile("updateDto", "updateDto.jpg", "updateDto/jpg", "<<jpg data>>".getBytes(StandardCharsets.UTF_8)))
//                        .file(images)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .principal(authentication));

        // then
        perform.andExpect(status().isOk())
                .andDo(document("/update-user"));
    }



    private UserJoinRequestDto createUserJoinDtoWithUsernameAndPassword(final String username, final String password) {
        return UserJoinRequestDto.builder()
                .username(username)
                .password(password)
                .email("이메일")
                .phone("번호")
                .build();
    }

    private User createUser(final String username, final String password) {
        User user = userRepository.save(User.builder()
                .username(username)
                .password(password)
                .build());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), new ArrayList<>()));

        authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("" + user.getId());
        return user;
    }

}