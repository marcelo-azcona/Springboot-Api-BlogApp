package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.PostDto;

public interface PostService {

    PostDto createPost(PostDto postDto);
}
