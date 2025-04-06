package com.poc.spring_batch_reports_generation.Repo;

import com.poc.spring_batch_reports_generation.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
}
