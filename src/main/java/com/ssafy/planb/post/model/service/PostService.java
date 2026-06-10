package com.ssafy.planb.post.model.service;

import com.ssafy.planb.post.model.Post;
import com.ssafy.planb.post.model.dto.PostDto;
import java.util.List;

public interface PostService {
    int createPost(PostDto.Info postInfo);
    List<Post> findPostListByWriterName(String writerName);
    Post findPostById(Long id);
    List<Post> findPostListByWriterId(Long writerId);
    List<Post> findPostListByTitle(String title);
    int updatePost(PostDto.Info postInfo);
    int deletePost(Long id);
}
