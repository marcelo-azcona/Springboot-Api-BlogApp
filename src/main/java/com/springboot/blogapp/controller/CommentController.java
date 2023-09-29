package com.springboot.blogapp.controller;

import com.springboot.blogapp.dto.CommentDto;
import com.springboot.blogapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/post/{postId}/comment")
    public List<CommentDto> getAllCommentsByPost(@PathVariable(value = "postId") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId,
                                                    @RequestBody CommentDto commentDto) {

        CommentDto createdComment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}/comment/{id}")
    public CommentDto getCommentById(@PathVariable(value = "postId") Long postId,
                                     @PathVariable(value = "id") Long commentId) {

        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return commentDto;
    }
}
