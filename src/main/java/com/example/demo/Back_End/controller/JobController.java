package com.example.demo.Back_End.controller;

import com.example.demo.Back_End.dto.JobDto;
import com.example.demo.Back_End.service.impl.JobServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/get/{id}")
    public ResponseEntity<JobDto> getJobById(@PathVariable int id) {
        JobDto job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @PutMapping("update/{id}")
    public void updateJob(@PathVariable Integer id, @RequestBody JobDto jobDto) {
        jobDto.setId(id);
        jobService.updateJob(jobDto);
    }
}
