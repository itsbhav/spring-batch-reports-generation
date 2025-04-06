package com.poc.spring_batch_reports_generation.Config;

import com.poc.spring_batch_reports_generation.Model.Book;
import com.poc.spring_batch_reports_generation.Model.ReverseBook;
import com.poc.spring_batch_reports_generation.Repo.BookRepo;
import com.poc.spring_batch_reports_generation.Repo.ReverseBookRepo;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfigBook {

    private final BookRepo bookRepo;
    private final ReverseBookRepo reverseBookRepo;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;


    @Bean
    public RepositoryItemReader<Book>itemReader(){
        Map<String, Sort.Direction>sort=new HashMap<>();
        sort.put("id", Sort.Direction.ASC);
        return new RepositoryItemReaderBuilder<Book>()
                .name("BookReader")
                .methodName("findAll")
                .repository(bookRepo)
                .arguments(List.of())
                .pageSize(100)
                .sorts(sort)
                .build();
    }

    @Bean
    public ItemProcessor<Book, ReverseBook>itemProcessor(){
      return  book->{
            ReverseBook reverseBook=new ReverseBook();
            reverseBook.setId(book.getId());
            reverseBook.setReversedComment(new StringBuilder(book.getComment()).reverse().toString());
            reverseBook.setName(new StringBuilder(book.getName()).reverse().toString());
            reverseBook.setAuthor(new StringBuilder(book.getAuthor()).reverse().toString());
            return reverseBook;
        };
    }

    @Bean
    public RepositoryItemWriter<ReverseBook>itemWriter(){
        return new RepositoryItemWriterBuilder<ReverseBook>()
                .repository(reverseBookRepo)
                .methodName("customInsert")
                .build();
    }
    @Bean
    public Step reverseBookStep(){
        return new StepBuilder("reverseBookStep",jobRepository)
                .<Book,ReverseBook>chunk(40,transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .taskExecutor(customTaskExec())
                .build();
    }

    public TaskExecutor customTaskExec(){
        SimpleAsyncTaskExecutor asyncTaskExecutor=new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(40);
        return asyncTaskExecutor;
    }
    @Bean
    public Job reverseBookJob(){
        return new JobBuilder("reverseBookJob", jobRepository)
                .start(reverseBookStep())
                .build();
    }
}
