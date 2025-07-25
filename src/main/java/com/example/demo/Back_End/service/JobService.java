package com.example.demo.Back_End.service;

import com.example.demo.Back_End.dto.JobDto;

import java.util.List;

public interface JobService {
    void saveJob(JobDto jobDto);
    List<JobDto> getAllJobs();
    void updateJob(JobDto jobDto);
    JobDto getJobById(int id);
    void deleteJobById(Integer id);
    void changeJobStatus(String id);
    List<JobDto> getAllJobsByKeyword(String keyword);
    List<JobDto> getJobsByPage(int page, int size);
}
