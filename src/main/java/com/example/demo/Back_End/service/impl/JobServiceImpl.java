package com.example.demo.Back_End.service.impl;

import com.example.demo.Back_End.dto.JobDto;
import com.example.demo.Back_End.entity.Job;
import com.example.demo.Back_End.repository.JobRepository;
import com.example.demo.Back_End.service.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveJob(JobDto jobDto) {
        jobRepository.save(modelMapper.map(jobDto, Job.class));
    }

    @Override
    public List<JobDto> getAllJobs() {
        List<Job> allJobs = jobRepository.findAll();
        return modelMapper.map(allJobs,new TypeToken<List<JobDto>>(){}.getType());
    }

    @Override
    public void updateJob(JobDto jobDto) {
        jobRepository.save(modelMapper.map(jobDto, Job.class));
    }

    @Override
    public JobDto getJobById(int id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return modelMapper.map(job, JobDto.class);
    }
}
