package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.CommentDto;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);
}
