package com.ssafy.planb.post.model;

import com.ssafy.planb.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class Post {
    private Long id;
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String content;
    private List<Comment> comments;
    private List<String> images;

    @Builder
    public Post(Long writerId, LocalDateTime createdAt, LocalDateTime updatedAt, String title, String content) {
        this.writerId = writerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.title = title;
        this.content = content;
    }
}
