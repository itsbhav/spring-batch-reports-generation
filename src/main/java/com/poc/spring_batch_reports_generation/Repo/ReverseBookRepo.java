package com.poc.spring_batch_reports_generation.Repo;

import com.poc.spring_batch_reports_generation.Model.ReverseBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ReverseBookRepo extends JpaRepository<ReverseBook, UUID> {

    @Modifying
    @Query("INSERT INTO ReverseBook (id, name, author, reversedComment) VALUES (:#{#book.id}, :#{#book.name}, :#{#book.author}, :#{#book.reversedComment})")
    void customInsert(@Param("book") ReverseBook book);
}
