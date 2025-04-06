package com.poc.spring_batch_reports_generation.Repo;

import com.poc.spring_batch_reports_generation.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepo extends JpaRepository<Book, UUID> {
}
