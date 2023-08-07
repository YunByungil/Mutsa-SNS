package com.example.mutsaSNS.service.post;

import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.post.PostImage;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.post.PostImageRepository;
import com.example.mutsaSNS.domain.repository.post.PostRepository;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import com.example.mutsaSNS.dto.post.request.PostCreateRequestDto;
import com.example.mutsaSNS.dto.post.request.PostUpdateRequestDto;
import com.example.mutsaSNS.dto.post.response.PostCreateResponseDto;
import com.example.mutsaSNS.dto.post.response.PostListResponseDto;
import com.example.mutsaSNS.dto.post.response.PostOneResponseDto;
import com.example.mutsaSNS.dto.post.response.PostUpdateResponseDto;
import com.example.mutsaSNS.exception.MutsaSnsAppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.mutsaSNS.exception.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository imageRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostCreateResponseDto createPost(final PostCreateRequestDto postCreateDto, final Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        // 1. 이미지가 없으면 기본 이미지 설정을 위해 draft 값 true 게시글 저장
        if (postCreateDto.getImages() == null || postCreateDto.getImages().isEmpty()) {
            Post post = postRepository.save(postCreateDto.toEntity(user, true));
            String imageUrl = String.format("/static/%d/%s", 0, "base.png");
            imageRepository.save(PostImage.builder()
                    .post(post)
                    .image(imageUrl)
                    .build());
            return new PostCreateResponseDto(post);
        }

        // 2. 이미지가 존재하면 기본 이미지는 false, 게시글 저장
        Post post = postRepository.save(postCreateDto.toEntity(user, false));

        // 저장된 게시글 ID의 값을 사용해서 images 저장
        List<MultipartFile> images = postCreateDto.getImages();
        String postDir = String.format("post/%d/", post.getId());

        try {
            Files.createDirectories(Path.of(postDir));
        } catch (IOException e) {
            log.error("IOException = {}", e);
            throw new MutsaSnsAppException(SERVER_ERROR, SERVER_ERROR.getMessage());
        }

        for (MultipartFile image : images) {
            String postFilename = generatePostFilename(image);

            String postPath = postDir + postFilename;
            Path path = Path.of(postPath);

            try {
                image.transferTo(path);
            } catch (IOException e) {
                log.error("IOException = {}", e);
                throw new MutsaSnsAppException(SERVER_ERROR, SERVER_ERROR.getMessage());
            }

            String imageUrl = String.format("/static/%d/%s", post.getId(), postFilename);
            imageRepository.save(PostImage.builder()
                    .post(post)
                    .image(imageUrl)
                    .build());
        }

        return new PostCreateResponseDto(post);
    }

    public List<PostListResponseDto> readAllPost(final String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        List<Post> allByUserId = postRepository.findAllByUserId(user.getId());

        List<PostListResponseDto> listResponseDto = allByUserId.stream()
                .map(post -> new PostListResponseDto(post, user))
                .collect(Collectors.toList());

        return listResponseDto;
    }

    public PostOneResponseDto readOnePost(final Long postId, final Long userId) {
        Post post = postRepository.findAllByPostId(postId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUNT_POST, NOT_FOUNT_POST.getMessage()));

        return new PostOneResponseDto(post);
    }

    public PostUpdateResponseDto updatePost(final PostUpdateRequestDto updateDto, final Long postId, final Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUNT_POST, NOT_FOUNT_POST.getMessage()));

        if (post.getUser().getId() != userId) {
            throw new MutsaSnsAppException(NOT_MATCH_POST_USER, NOT_MATCH_POST_USER.getMessage());
        }

        if (updateDto.getImages() == null || updateDto.getImages().isEmpty()) {
            return new PostUpdateResponseDto(post);
        }

        // 저장된 게시글 ID의 값을 사용해서 images 저장
        List<MultipartFile> images = updateDto.getImages();
        String postDir = String.format("post/%d/", post.getId());

        try {
            Files.createDirectories(Path.of(postDir));
        } catch (IOException e) {
            log.error("IOException = {}", e);
            throw new MutsaSnsAppException(SERVER_ERROR, SERVER_ERROR.getMessage());
        }

        for (MultipartFile image : images) {
            String postFilename = generatePostFilename(image);

            String postPath = postDir + postFilename;
            Path path = Path.of(postPath);

            try {
                image.transferTo(path);
            } catch (IOException e) {
                log.error("IOException = {}", e);
                throw new MutsaSnsAppException(SERVER_ERROR, SERVER_ERROR.getMessage());
            }

            String imageUrl = String.format("/static/%d/%s", post.getId(), postFilename);
            imageRepository.save(PostImage.builder()
                    .post(post)
                    .image(imageUrl)
                    .build());
        }

        // TODO: Response 에서 Select 쿼리 한 번 더 나가는데 수정하자.
        return new PostUpdateResponseDto(post);
    }

    private void deleteFilesInProfileDirectory(String profileDir) {
        File directory = new File(profileDir);
        if (!(directory.exists() && directory.isDirectory())) {
            return;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    private String generatePostFilename(final MultipartFile image) {
        String originalFilename = image.getOriginalFilename();

        String[] fileNameSplit = originalFilename.split("\\.");

        String extension = fileNameSplit[fileNameSplit.length - 1];

        String uuid = UUID.randomUUID().toString() + ".";
        return uuid + extension;
    }
}
