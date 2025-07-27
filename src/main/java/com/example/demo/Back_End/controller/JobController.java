package com.example.demo.Back_End.controller;

import com.example.demo.Back_End.dto.JobDto;
import com.example.demo.Back_End.service.impl.JobServiceImpl;
import com.example.demo.Back_End.util.APIResponse;
import jakarta.validation.Valid;
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
    public ResponseEntity<APIResponse> createJob(@Valid @RequestBody JobDto jobDto){
        jobService.saveJob(jobDto);
        return ResponseEntity.ok(new APIResponse(
                200,
                "Job created successfully",
                jobDto
        ));
    }

    @GetMapping("get")
    public ResponseEntity<APIResponse> getAllJobs() {
        List<JobDto> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(new APIResponse(
            500,
            "All Jobs Fetched Successfully",
            jobs
        ));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<APIResponse> getJobById(@PathVariable int id) {
        JobDto job = jobService.getJobById(id);
        return ResponseEntity.ok(new APIResponse(
                200,
                "Job Fetched Successfully",
                job
        ));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<APIResponse> updateJob(@PathVariable Integer id, @RequestBody JobDto jobDto) {
        jobDto.setId(id);
        jobService.updateJob(jobDto);
        return ResponseEntity.ok(new APIResponse(
                200,
                "Job Updated Successfully",
                jobDto
        ));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<APIResponse> deleteJob(@PathVariable Integer id){
        jobService.deleteJobById(id);
        return ResponseEntity.ok(new APIResponse(
                200,
                "Job Deleted Successfully",
                null
        ));
    }

    @PatchMapping("status/{id}")
    public ResponseEntity<APIResponse> changeJobStatus(@PathVariable String id) {
        jobService.changeJobStatus(id);
        return ResponseEntity.ok(new APIResponse(
                200,
                "Job Status Changed Successfully",
                null
        ));
    }

    @GetMapping("search/{keyword}")
    public ResponseEntity<APIResponse> searchJob(@PathVariable("keyword") String keyword) {
        List<JobDto> jobs = jobService.getAllJobsByKeyword(keyword);
        return ResponseEntity.ok(new APIResponse(
                200,
                "Job Fetched Successfully",
                jobs
        ));
    }

    @GetMapping("/get-paged")
    public ResponseEntity<APIResponse> getJobsByPage(@RequestParam int page, @RequestParam int size) {
        List<JobDto> jobs = jobService.getJobsByPage(page, size);
        return ResponseEntity.ok(new APIResponse(
                200,
                "Job Fetched Successfully",
                jobs
        ));
    }

}
