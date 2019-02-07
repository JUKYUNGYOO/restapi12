package com.example.restapi12.post;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest //웹 용 만 빈으로 생성해 주므로
public class PostControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostRepository postRepository;
    //레포지터리를 따로 빈으로 등록 , mock 객체는 null을 반환하므로 stuffing을 해줘야됨

    @Test
    public void createPost() throws Exception{

        Post post = Post.builder()
                .title("제목1")
                .content("컨텐츠1")
                .code("마크다운1")
                .status(PostStatus.Y)
                .regDate(LocalDateTime.now())
                .build();
//Mockito의 save가 호출된면 post를 리턴하라
        post.setId((long) 10);
        Mockito.when(postRepository.save(post)).thenReturn(post);

        /*
        post 요청, json 넘김
         */
        mockMvc.perform(post("/api/posts/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)  //HAL_JSON을 원한다.
                .content(objectMapper.writeValueAsString(post))) //객체를 json문자열로 바꾸어 요청 본문에 넣어줌
                .andDo(print())  //응답 확인
                .andExpect(status().isCreated())
                .andExpect( jsonPath("id").exists()) //id가 있는
                ;
    }
}
