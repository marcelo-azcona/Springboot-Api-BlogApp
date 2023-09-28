package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.CommentDto;
import com.springboot.blogapp.entity.Comment;
import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.repository.CommentRepository;
import com.springboot.blogapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        Post post = retrievePostById(postId);

        // Set post to comment entity
        comment.setPost(post);

        // Save comment entity to DB
        Comment savedComment = commentRepository.save(comment);

        CommentDto savedCommentDto = entityToDto(savedComment);

        return savedCommentDto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        // retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        // convert the list of comments entities to a list of comments dto
        List<CommentDto> commentsDto = comments.stream().map(comment -> entityToDto(comment)).collect(Collectors.toList());

        return commentsDto;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {

        // retrieve post entity by id
        Post post = retrievePostById(postId);

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // Check if the comment belogs to the post
        return null;
    }

    private Post retrievePostById(long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        return post;
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
