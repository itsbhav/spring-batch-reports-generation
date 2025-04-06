package com.poc.spring_batch_reports_generation.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentSubjectResultMap {

    @EmbeddedId
    private StudentSubjectMapId id;

    @ManyToOne
    @MapsId("id.StudentId")
    @JoinColumn(name = "student_id", referencedColumnName = "roll")
    private Student student;

    @ManyToOne
    @MapsId("id.subjectCode")
    @JoinColumn(name = "subject_code", referencedColumnName = "subjectCode")
    private Subject subject;


    private Integer marksObtained;
    private Integer totalMarks;
}
