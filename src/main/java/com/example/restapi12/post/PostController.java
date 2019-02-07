package com.example.restapi12.post;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "/api/posts",produces = MediaTypes.HAL_JSON_UTF8_VALUE) //URI에 있는 메서드 호출
//클래스의 모든 핸들러의  응답은 HAL_JSON응답을 보냄
public class PostController {



    //빈으로 주입 받고
    private final PostRepository postRepository;

    private final ModelMapper modelMapper;


    public PostController(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }


    /*

    입력값들을 전달하면 JSON 응답으로 201이 나오는지 확인.
   1) Location 헤더에 생성된 이벤트를 조회할 수 있는 URI 담겨 있는지 확인.
    id는 DB에 들어갈 때 자동생성된 값으로 나오는지 확인
     */
    @PostMapping
    /*
    PostDto로 받은 입력 값들로 Post객체를 생성
     */
    public ResponseEntity createPost(@RequestBody PostDto postDto){
        Post post = modelMapper.map(postDto,Post.class);
        Post newPost = this.postRepository.save(post); //저장이 된 객체가 나온다.
        //이 save에 전달된 post 객체는 새로만든 객체 postDto 객체 이다.

        //id에 해당하는 링크를 만들고 링크를 uri로 변환
        URI createdURI = linkTo(PostController.class).slash(newPost.getId()).toUri(); //post에서 생성된 id가 location header에 들어간다.
        //1) 링크를 만들 때

        return ResponseEntity.created(createdURI).body(post);


    }

}
