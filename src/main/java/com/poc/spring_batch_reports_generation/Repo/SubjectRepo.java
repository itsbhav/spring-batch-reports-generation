package com.poc.spring_batch_reports_generation.Repo;

import com.poc.spring_batch_reports_generation.Model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepo extends JpaRepository<Subject,Integer> {
}
