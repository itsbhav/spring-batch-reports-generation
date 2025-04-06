package com.poc.spring_batch_reports_generation.Service;

import com.poc.spring_batch_reports_generation.Model.StudentSubjectResultMap;
import com.poc.spring_batch_reports_generation.Repo.StudentSubjectMarksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {
    @Autowired
    private StudentSubjectMarksRepo resultRepo;

//    public ResponseEntity<?> postMarks(List<StudentSubjectResultMap> results){
//        resultRepo.saveAll(results);
//        return new ResponseEntity<>("Total created maps are : "+results.size(),HttpStatus.CREATED);
//    }
}
