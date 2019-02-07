package com.example.restapi12.post;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/*
입력 값을 제한하는 클래스
PostDto 안에 있는 값 들만 입력받을 수 있다.
 */

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PostDto {
  //  private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private String code;
}
