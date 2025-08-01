package com.example.demo.Back_End.service.impl;

import com.example.demo.Back_End.dto.JobDto;
import com.example.demo.Back_End.entity.Job;
import com.example.demo.Back_End.exception.DuplicateEntryException;
import com.example.demo.Back_End.exception.ResourceNotFoundException;
import com.example.demo.Back_End.repository.JobRepository;
import com.example.demo.Back_End.service.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveJob(JobDto jobDto) {
//        if (jobRepository.existsById(jobDto.getId())){
//            throw new DuplicateEntryException("Job already exists");
//        }
        jobRepository.save(modelMapper.map(jobDto, Job.class));
    }

    @Override
    public List<JobDto> getAllJobs() {
        List<Job> allJobs = jobRepository.findAll();
        if (allJobs.isEmpty()){
            throw new ResourceNotFoundException("Jobs not found");
        }
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

    @Override
    public void deleteJobById(Integer id) {
        if (!jobRepository.existsById(id)){
            throw new ResourceNotFoundException("Job with ID " + id + " not found");
        }
        jobRepository.deleteById(id);
    }

    @Override
    public void changeJobStatus(String id) {
        if (!jobRepository.existsById(Integer.valueOf(id))){
            throw new ResourceNotFoundException("Job with ID " + id + " not found");
        }
        jobRepository.changeJobStatus(id);
    }

    @Override
    public List<JobDto> getAllJobsByKeyword(String keyword) {
        List<Job> allJobs = jobRepository.findJobByJobTitleContainingIgnoreCase(keyword);
        return modelMapper.map(allJobs,new TypeToken<List<JobDto>>(){}.getType());
    }

    @Override
    public List<JobDto> getJobsByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobRepository.findAll(pageable)
                .stream()
                .map(job -> modelMapper.map(job, JobDto.class))
                .collect(Collectors.toList());
    }
}
