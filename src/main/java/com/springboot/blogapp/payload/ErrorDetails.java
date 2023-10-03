package com.springboot.blogapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {

    private Date timestamp;
    private String errorMessage;
    private String errorDetails;
}
