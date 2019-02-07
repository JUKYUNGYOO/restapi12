package com.example.restapi12.post;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@WebMvcTest //웹 용 만 빈으로 생성해 주므로
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
//
//    @MockBean
//    PostRepository postRepository;
    //레포지터리를 따로 빈으로 등록 , mock 객체는 null을 반환하므로 stuffing을 해줘야됨

    @Test
    public void createPost() throws Exception{

        /*
        mocking을 하지 않으므로 test에 넣어준 값 들로 실행됨.
         */
        Post post = Post.builder()
                .id((long) 10)
                .title("제목1")
                .content("컨텐츠1")
                .code("마크다운1")
                .status(PostStatus.Y)
                .regDate(LocalDateTime.now())
                .build();
//Mockito의 save가 호출된면 post를 리턴하라
    //    post.setId((long) 10);
   //     Mockito.when(postRepository.save(post)).thenReturn(post); //mocking을 해주면 null값이 안들어감

        /*
        post 요청, json 넘김
         */
        mockMvc.perform(post("/api/posts/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)  //HAL_JSON을 원한다.
                .content(objectMapper.writeValueAsString(post))) //객체를 json문자열로 바꾸어 요청 본문에 넣어줌
                .andDo(print())  //응답 확인
                .andExpect(status().isCreated())
                //id가 있는지
                .andExpect( jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                ;
    }
}
