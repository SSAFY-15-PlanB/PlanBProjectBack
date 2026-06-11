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
                .writerId(18L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .title("최성보작성2")
                .content("본문본문2")
                .build();

        int actual = postService.createPost(postInfo);

        Assertions.assertEquals(1, actual);
    }

    @Test
    public void findPostByIdTest() {
        Post actual = postService.findPostById(2L);

        Assertions.assertEquals(13L, actual.getWriterId());
        log.info(actual.toString());
    }

    @Test
    public void findPostListByWriterNameTest() {
        List<Post> actual = postService.findPostListByWriterName("성보");

        Assertions.assertEquals(18L, actual.get(0).getWriterId());
        Assertions.assertEquals("최성보작성", actual.get(0).getTitle());
        Assertions.assertEquals("본문본문", actual.get(0).getContent());
    }

    @Test
    public void findPostListByWriterIdTest() {
        List<Post> post4 = postService.findPostListByWriterId(18L);

        Assertions.assertEquals(4L, post4.get(0).getId());
        Assertions.assertEquals("최성보작성", post4.get(0).getTitle());
        Assertions.assertEquals("본문본문", post4.get(0).getContent());
        log.info(post4.get(0).toString());

        for(Post post : post4){
            Assertions.assertEquals(4L, post.getId());
            Assertions.assertEquals("최성보작성", post.getTitle());
            Assertions.assertEquals("본문본문", post.getContent());
            log.info(post.toString());
        }
    }

    @Test
    public void findPostListByTitleTest() {
        List<Post> actualList = postService.findPostListByTitle("성보");

        for(Post post : actualList){
            log.info(post.toString());
        }
    }
}