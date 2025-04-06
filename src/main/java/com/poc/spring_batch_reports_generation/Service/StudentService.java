package com.poc.spring_batch_reports_generation.Service;

import com.poc.spring_batch_reports_generation.Model.Student;
import com.poc.spring_batch_reports_generation.Repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

//    public ResponseEntity<?> postStudent(List<Student>students){
//        studentRepo.saveAll(students);
//        return new ResponseEntity<>("Total Processed Items added : "+students.size(), HttpStatus.CREATED);
//    }
}
