package com.ssafy.planb.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
public class Comment {
    private Long id;
    private Long postId;
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String content;
}
