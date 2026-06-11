package com.ssafy.planb.post.model.service;

import com.ssafy.planb.post.model.Post;
import com.ssafy.planb.post.model.dto.PostDto;
import com.ssafy.planb.post.model.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostMapper postMapper;

    @Override
    public int createPost(PostDto.Info postInfo) {
        Post newPost = Post.builder()
                .title(postInfo.getTitle())
                .content(postInfo.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .writerId(postInfo.getWriterId())
                .build();

        return postMapper.createPost(newPost);
    }

    @Override
    public List<Post> findPostListByWriterName(String writerName) {
        return postMapper.findPostListByWriterName(writerName);
    }

    @Override
    public Post findPostById(Long id) {
        return postMapper.findPostById(id);
    }

    @Override
    public List<Post> findPostListByWriterId(Long writerId) {
        return postMapper.findPostListByWriterId(writerId);
    }

    @Override
    public List<Post> findPostListByTitle(String title) {
        return postMapper.findPostListByTitle(title);
    }

    @Override
    public int updatePost(PostDto.Info postInfo) {
        return 0;
    }

    @Override
    public int deletePost(Long id) {
        return 0;
    }
}
