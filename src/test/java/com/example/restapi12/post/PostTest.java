package com.example.restapi12.post;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {

    @Test
    public void builder(){
        Post post = Post.builder()
                .title("one")
                .content("two")
                .build();
        assertThat(post).isNotNull();


    }
    @Test
    public void javaBean(){
        //Given
        String title = "posttitle";
        String content = "postcontent";
        //When
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        //Then
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);

    }


}