package com.poc.spring_batch_reports_generation.Controller;

import com.poc.spring_batch_reports_generation.Model.Book;
import com.poc.spring_batch_reports_generation.Model.Student;
import com.poc.spring_batch_reports_generation.Model.StudentSubjectResultMap;
import com.poc.spring_batch_reports_generation.Model.Subject;
import com.poc.spring_batch_reports_generation.Service.*;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class HomeController {

   private JobLauncher jobLauncher;
   private Job reverseBookJob;
   private Job resultJob;

        @PostMapping("/book")
        public ResponseEntity<String> runReverseBookJob() {
            return runJob(reverseBookJob, "reverseBookJob");
        }


        @PostMapping("/result")
        public ResponseEntity<String> runResultJob() {
            return runJob(resultJob, "resultJob");
        }

        private ResponseEntity<String> runJob(Job job, String jobName) {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("requestId", System.currentTimeMillis() + "--" + UUID.randomUUID())// ensures uniqueness
                    .toJobParameters();

            try {
                jobLauncher.run(job,jobParameters);
                return ResponseEntity.ok(jobName + " triggered successfully!");
            } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                     JobParametersInvalidException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error running " + jobName + ": " + e.getMessage());
            }
        }

}
