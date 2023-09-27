package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.CommentDto;
import com.springboot.blogapp.entity.Comment;
import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.repository.CommentRepository;
import com.springboot.blogapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {


    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = dtoToEntity(commentDto);

        // Retrieve post by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // Set post to comment entity
        comment.setPost(post);

        // Save comment entity to DB
        Comment savedComment = commentRepository.save(comment);

        CommentDto savedCommentDto = entityToDto(savedComment);
        
        return savedCommentDto;
    }

    private CommentDto entityToDto(Comment comment) {

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());

        return commentDto;
    }

    private Comment dtoToEntity(CommentDto commentDto) {

        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setBody(commentDto.getBody());
        comment.setName(comment.getName());
        comment.setEmail(comment.getEmail());

        return comment;
    }
}
