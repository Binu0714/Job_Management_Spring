package com.example.demo.Back_End.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobDto {

    private Integer id;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotBlank(message = "Company name is required")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Company name should contain only letters and spaces")
    private String company;

    @NotNull //only checks that the value is not null — it does not check whether it’s an empty string ("")
    private String location;

    @NotBlank(message = "Job type is required")
    //@NotBlank contains NotNull
    private String type;

    @Size(min = 10,max = 1000, message = "Job description should be between 10 and 1000 characters")
    private String jobDescription;

    private String status;

}
