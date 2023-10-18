package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.PostDto;
import com.springboot.blogapp.entity.Category;
import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.payload.PostResponse;
import com.springboot.blogapp.repository.CategoryRepository;
import com.springboot.blogapp.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper,
                           CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        // convert DTO to entity
        Post post = dtoToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        // convert entity to DTO;
        PostDto postResponse = entityToDto(newPost);
        return postResponse;
    }


    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort;

        if (sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        // Pageable instance for pagination and sorting
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // Get content from page object
        List<Post> listOfPosts = posts.getContent();

        // Return a custom response
        List<PostDto> content = listOfPosts.stream().map(post -> entityToDto(post)).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        PostDto postDto = entityToDto(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        // Check if the category with a given Id exist in the DB
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);

        PostDto updatedPostDto = entityToDto(updatedPost);
        return updatedPostDto;

    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    private PostDto entityToDto(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);

        return postDto;
    }

    private Post dtoToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);

        return post;
    }
}
