package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.CommentDto;
import com.springboot.blogapp.entity.Comment;
import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.exception.BlogApiException;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.repository.CommentRepository;
import com.springboot.blogapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public CommentDto createComment(Long postId, CommentDto commentDto) {

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
    public List<CommentDto> getCommentsByPostId(Long postId) {
        // retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        // convert the list of comments entities to a list of comments dto
        List<CommentDto> commentsDto = comments.stream().map(comment -> entityToDto(comment)).collect(Collectors.toList());

        return commentsDto;
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {

        Post post = retrievePostById(postId);

        Comment commentById = retrieveCommentById(commentId);

        if (!commentById.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentById.setName(commentRequest.getName());
        commentById.setBody(commentRequest.getBody());
        commentById.setEmail(commentRequest.getEmail());

        Comment updatedComment = commentRepository.save(commentById);
        return entityToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {

        Post post = retrievePostById(postId);

        Comment commentById = retrieveCommentById(commentId);

        if (!commentById.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.delete(commentById);
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = retrievePostById(postId);

        // retrieve comment by id
        Comment commentById = retrieveCommentById(commentId);

        // Check if the comment belongs to the post
        if (!commentById.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return entityToDto(commentById);
    }


    private Post retrievePostById(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        return post;
    }

    private Comment retrieveCommentById(Long commentId) {

        Comment commentById = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        return commentById;
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
