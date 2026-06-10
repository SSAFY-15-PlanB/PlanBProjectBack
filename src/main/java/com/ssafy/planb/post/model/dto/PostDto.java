package com.ssafy.planb.post.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PostDto {
    @Getter
    @NoArgsConstructor
    public static class Info {
        private Long id;
        private Long writerId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String title;
        private String content;

        @Builder
        public Info(Long writerId, LocalDateTime createdAt, LocalDateTime updatedAt, String title, String content) {
            this.writerId = writerId;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.title = title;
            this.content = content;
        }
    }

    public static class updateForm{
        private Long id;
        private String title;
        private String content;
    }

//    public static class info{
//        private String title;
//        private String content;
//    }
}
