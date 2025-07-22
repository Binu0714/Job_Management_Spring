package com.example.demo.Back_End.service.impl;

import com.example.demo.Back_End.dto.JobDto;
import com.example.demo.Back_End.entity.Job;
import com.example.demo.Back_End.repository.JobRepository;
import com.example.demo.Back_End.service.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveJob(JobDto jobDto) {
        jobRepository.save(modelMapper.map(jobDto, Job.class));
    }
}
