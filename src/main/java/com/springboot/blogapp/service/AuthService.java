package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.LoginDto;

public interface AuthService {
    
    String login(LoginDto loginDto);
}
