package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.CommentDto;
import com.springboot.blogapp.entity.Comment;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<Comment> getCommentsByPostId(long postId);
}
