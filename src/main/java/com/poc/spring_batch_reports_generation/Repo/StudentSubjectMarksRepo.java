package com.poc.spring_batch_reports_generation.Repo;

import com.poc.spring_batch_reports_generation.Model.ResultDTO;
import com.poc.spring_batch_reports_generation.Model.StudentSubjectMapId;
import com.poc.spring_batch_reports_generation.Model.StudentSubjectResultMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentSubjectMarksRepo extends JpaRepository<StudentSubjectResultMap, StudentSubjectMapId> {
    @Query(value = "SELECT " +
            "s.roll AS roll, " +
            "sub.subject_code AS subjectCd, " +
            "s.name AS studentNm, " +
            "sub.subject_name AS subjectNm, " +
            "map.total_marks AS totalMarks, " +
            "map.marks_obtained AS obtainedMarks " +
            "FROM student s " +
            "JOIN student_subject_result_map map ON s.roll = map.student_id " +
            "JOIN subject sub ON map.subject_code = sub.subject_code " +
            "WHERE s.roll = :roll", nativeQuery = true)
    List<ResultDTO> getResultsForStudentByRoll(@Param("roll") Integer roll);
}
