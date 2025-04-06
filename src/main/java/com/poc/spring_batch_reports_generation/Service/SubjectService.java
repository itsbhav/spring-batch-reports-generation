package com.poc.spring_batch_reports_generation.Service;

import com.poc.spring_batch_reports_generation.Model.Subject;
import com.poc.spring_batch_reports_generation.Repo.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepo subjectRepo;

//    public ResponseEntity<?> postSubjects(List<Subject>subjects){
//        subjectRepo.saveAll(subjects);
//        return new ResponseEntity<>("Total Subjects added : "+subjects.size(),HttpStatus.CREATED);
//    }
}
