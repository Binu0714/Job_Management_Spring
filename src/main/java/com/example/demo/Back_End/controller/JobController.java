package com.example.demo.Back_End.controller;

import com.example.demo.Back_End.dto.JobDto;
import com.example.demo.Back_End.service.impl.JobServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api/v2/job")
@RestController
@RequiredArgsConstructor
public class JobController {
    private final JobServiceImpl jobService;

    @PostMapping("create")
    public void createJob(@RequestBody JobDto jobDto){
        jobService.saveJob(jobDto);
    }

    @GetMapping("get")
    public List<JobDto> getAllJobs() {
        return jobService.getAllJobs();
    }
}
