package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.PostDto;
import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO to entity
        Post post = DtoToEntity(postDto);

        Post newPost = postRepository.save(post);

        // convert entity to DTO;
        PostDto postResponse = entityToDto(newPost);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPost() {
        return null;
    }

    private PostDto entityToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());

        return postDto;
    }

    private Post DtoToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        return post;
    }
}
