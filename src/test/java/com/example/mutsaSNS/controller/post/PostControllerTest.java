package com.example.mutsaSNS.controller.post;

import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class})
@Transactional
@SpringBootTest
class PostControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

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

    @DisplayName("게시글 작성 API 테스트 (이미지 O)")
    @Test
    void createPostWithImage() throws Exception {
        // given
        createUser();
        MockMultipartFile image = new MockMultipartFile("images", "image.jpg", "image/jpeg", new byte[]{}); // 예시 이미지
        MockMultipartFile anotherImage = new MockMultipartFile("images", "another_image.jpg", "image/jpeg", new byte[]{}); // 추가 예시 이미지
        List<MultipartFile> files = new ArrayList<>();
        files.add(image);
        files.add(anotherImage);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "title");
        params.add("content", "content");

        String url = "http://localhost:8080/post";

        // when
        ResultActions perform = mvc.perform(multipart(url)
                .file(anotherImage)
                .file(image)
                .queryParams(params)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .principal(authentication));

        // then
        perform.andExpect(status().isOk())
                .andDo(document("/create-post",
                        requestParts(
                                partWithName("images").description("업로드할 이미지 파일들")
                        ),
                        queryParameters(
                                parameterWithName("title").description("gd"),
                                parameterWithName("content").description("gd")
                        )));
    }

    private User createUser() {
        User user = userRepository.save(User.builder()
                .username("아이디")
                .password("비밀번호")
                .build());
        setAuthentication(user);
        return user;
    }

    private void setAuthentication(final User user) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), new ArrayList<>()));

        authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("" + user.getId());
    }

}