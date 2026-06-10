package com.ssafy.planb.post.model.mapper;

import com.ssafy.planb.post.model.Post;
import com.ssafy.planb.post.model.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    int createPost(Post post);
    List<Post> findPostListByWriterName(String writerName);
    Post findPostById(Long id);
    List<Post> findPostListByWriterId(Long writerId);
    List<Post> findPostListByTitle(String title);
    int updatePost(Post post);
    int deletePost(Long id);
}
