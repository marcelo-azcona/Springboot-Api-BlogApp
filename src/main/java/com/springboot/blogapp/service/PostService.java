package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.PostDto;
import com.springboot.blogapp.payload.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPost(int pageNo, int pageSize);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostbyId(long id);
}
