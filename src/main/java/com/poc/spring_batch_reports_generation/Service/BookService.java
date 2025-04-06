package com.poc.spring_batch_reports_generation.Service;

import com.poc.spring_batch_reports_generation.Model.Book;
import com.poc.spring_batch_reports_generation.Repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepo bookRepo;

//    public ResponseEntity<?> saveAllBooks(List<Book>books){
//        bookRepo.saveAll(books);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
