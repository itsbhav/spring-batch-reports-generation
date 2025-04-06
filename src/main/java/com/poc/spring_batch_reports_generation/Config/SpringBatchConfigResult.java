package com.poc.spring_batch_reports_generation.Config;

import com.poc.spring_batch_reports_generation.Model.ResultDTO;
import com.poc.spring_batch_reports_generation.Model.Student;
import com.poc.spring_batch_reports_generation.Repo.StudentRepo;
import com.poc.spring_batch_reports_generation.Repo.StudentSubjectMarksRepo;
import com.poc.spring_batch_reports_generation.Repo.SubjectRepo;
import com.poc.spring_batch_reports_generation.Service.ResultItemWriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableBatchProcessing
@RequiredArgsConstructor
@Configuration
public class SpringBatchConfigResult {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final StudentRepo studentRepo;
    private final SubjectRepo subjectRepo;
    private final StudentSubjectMarksRepo studentSubjectMarksRepo;
    private final ResultItemWriterService itemWriterResult;


    @Bean
    public RepositoryItemReader<Student> itemReaderStudent(){
        Map<String, Sort.Direction> sort=new HashMap<>();
        sort.put("roll", Sort.Direction.ASC);
        return new RepositoryItemReaderBuilder<Student>()
                .name("studentReader")
                .repository(studentRepo)
                .methodName("findAll")
                .arguments(List.of())
                .pageSize(32)
                .sorts(sort)
                .build();
    }

    @Bean
    public ItemProcessor<Student, List<ResultDTO>>itemProcessorStudent(){
        return student ->{
           return studentSubjectMarksRepo.getResultsForStudentByRoll(student.getRoll());
        };
    }

    @Bean
    public Step resultStep(){
        return new StepBuilder("resultStep",jobRepository)
                .<Student,List<ResultDTO>>chunk(16,transactionManager)
                .reader(itemReaderStudent())
                .processor(itemProcessorStudent())
                .taskExecutor(customTaskExec())
                .writer(itemWriterResult)
                .build();
    }

    public TaskExecutor customTaskExec(){
        SimpleAsyncTaskExecutor asyncTaskExecutor=new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(5);
        return asyncTaskExecutor;
    }

    @Bean
    public Job resultJob(){
        return new JobBuilder("resultJob",jobRepository)
                .start(resultStep())
                .build();
    }

}
