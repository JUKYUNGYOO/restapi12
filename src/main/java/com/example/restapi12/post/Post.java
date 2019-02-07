package com.example.restapi12.post;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
builder를 사용하면 모든 args값들을 public으로 받기 어려움, 그래서 기본생성자 + 모든 Args생성자 만드는 어노테이션을 붙여 줌.
 */

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity //Post를 entity로 만들어서 DB에 저장
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private String title;


    private String content;


    private String code;

    @Enumerated(EnumType.STRING) //기본값 string으로 바꾸기
    private PostStatus status;

    private LocalDateTime regDate;



}
