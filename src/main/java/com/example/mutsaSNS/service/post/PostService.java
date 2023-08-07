package com.example.mutsaSNS.service.post;

import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.post.PostImage;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.post.PostImageRepository;
import com.example.mutsaSNS.domain.repository.post.PostRepository;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import com.example.mutsaSNS.dto.post.request.PostCreateRequestDto;
import com.example.mutsaSNS.dto.post.request.PostUpdateRequestDto;
import com.example.mutsaSNS.dto.post.response.*;
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
import java.util.ArrayList;
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
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_POST, NOT_FOUND_POST.getMessage()));

        return new PostOneResponseDto(post);
    }

    @Transactional
    public PostUpdateResponseDto updatePost(final PostUpdateRequestDto updateDto, final Long postId, final Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_POST, NOT_FOUND_POST.getMessage()));

        if (post.getUser().getId() != userId) {
            throw new MutsaSnsAppException(NOT_MATCH_POST_USER, NOT_MATCH_POST_USER.getMessage());
        }

        List<String> resultUrl = new ArrayList<>();
        if (updateDto.getImages() == null || updateDto.getImages().isEmpty()) {
            return new PostUpdateResponseDto(post, resultUrl);
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
            resultUrl.add(imageUrl);
            imageRepository.save(PostImage.builder()
                    .post(post)
                    .image(imageUrl)
                    .build());
        }

        return new PostUpdateResponseDto(post, resultUrl);
    }

    @Transactional
    public PostUpdateResponseDto deleteImages(final Long postId, final Long imageId, final Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_POST, NOT_FOUND_POST.getMessage()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        if (post.getUser().getId() != userId) {
            throw new MutsaSnsAppException(NOT_MATCH_POST_USER, NOT_MATCH_POST_USER.getMessage());
        }

        PostImage postImage = imageRepository.findById(imageId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_IMAGE, NOT_FOUND_IMAGE.getMessage()));

        List<String> filename = new ArrayList<>();

        String[] originalFilename = postImage.getImage().split("static");
        String postDir = String.format("post%s", originalFilename[1]);
        filename.add(postDir);

        deleteFilesInPostDirectory(postDir);
        imageRepository.deleteById(imageId);

        List<PostImage> postImageByPostId = imageRepository.findAllByPostId(postId);
        if (postImageByPostId.size() == 0) {
            String imageUrl = String.format("/static/%d/%s", 0, "base.png");
            imageRepository.save(PostImage.builder()
                    .post(post)
                    .image(imageUrl)
                    .build());
            post.updateDraft(true);
        }

        return new PostUpdateResponseDto(post, filename);
    }

    @Transactional
    public PostDeleteResponseDto deletePost(final Long postId, final Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_POST, NOT_FOUND_POST.getMessage()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        if (post.getUser().getId() != userId) {
            throw new MutsaSnsAppException(NOT_MATCH_POST_USER, NOT_MATCH_POST_USER.getMessage());
        }

        postRepository.delete(post);

        return new PostDeleteResponseDto(post);
    }

    private void deleteFilesInPostDirectory(String postDir) {
        File file = new File(postDir);
        file.delete();
    }

    private String generatePostFilename(final MultipartFile image) {
        String originalFilename = image.getOriginalFilename();

        String[] fileNameSplit = originalFilename.split("\\.");

        String extension = fileNameSplit[fileNameSplit.length - 1];

        String uuid = UUID.randomUUID().toString() + ".";
        return uuid + extension;
    }
}
