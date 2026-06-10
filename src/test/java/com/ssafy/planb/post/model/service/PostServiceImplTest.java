package com.ssafy.planb.post.model.service;

import com.ssafy.planb.post.model.Post;
import com.ssafy.planb.post.model.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class PostServiceImplTest {
    @Autowired
    private PostService postService;

    @Test
    public void createPostTest(){
        PostDto.Info postInfo = PostDto.Info.builder()
                .writerId(13L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .title("gdgd")
                .content("content")
                .build();

        int actual = postService.createPost(postInfo);

        Assertions.assertEquals(1, actual);
    }

    @Test
    public void findPostByIdTest() {
        Post actual = postService.findPostById(2L);

        Assertions.assertEquals(1L, actual.getWriterId());
        Assertions.assertEquals("gdgd", actual.getTitle());
        Assertions.assertEquals("content", actual.getContent());

        Post actual2 = postService.findPostById(3L);

        Assertions.assertEquals(1L, actual2.getWriterId());
        Assertions.assertEquals("gdgd", actual2.getTitle());
        Assertions.assertEquals("content", actual2.getContent());
    }

    @Test
    public void findPostListByWriterNameTest() {
        List<Post> actual = postService.findPostListByWriterName("성보");

        Assertions.assertEquals(5L, actual.get(0).getWriterId());
        Assertions.assertEquals("gdgd", actual.get(0).getTitle());
        Assertions.assertEquals("content", actual.get(0).getContent());
        for(Post post : actual){
            Assertions.assertEquals(5L, post.getWriterId());
            Assertions.assertEquals("gdgd", post.getTitle());
            Assertions.assertEquals("content", post.getContent());
        }
    }

    @Test
    public void findPostListByWriterIdTest() {
        List<Post> post4 = postService.findPostListByWriterId(1L);
        List<Post> post5 = postService.findPostListByWriterId(13L);

        Assertions.assertEquals(4L, post4.get(0).getId());
        Assertions.assertEquals("gdgd", post4.get(0).getTitle());
        Assertions.assertEquals("content", post4.get(0).getContent());
        log.info(post4.get(0).toString());

        for(Post post : post4){
            Assertions.assertEquals(4L, post.getId());
            Assertions.assertEquals("gdgd", post.getTitle());
            Assertions.assertEquals("content", post.getContent());
            log.info(post.toString());
        }

        for(Post post : post5){
            Assertions.assertEquals(5L, post.getId());
            Assertions.assertEquals("gdgd", post.getTitle());
            Assertions.assertEquals("content", post.getContent());
            log.info(post.toString());
        }
    }
}