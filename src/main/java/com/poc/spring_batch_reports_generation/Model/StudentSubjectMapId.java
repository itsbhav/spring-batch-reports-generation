package com.poc.spring_batch_reports_generation.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.Objects;

@Embeddable
@Data
public class StudentSubjectMapId {

    @Column(name = "student_id")
    private Integer StudentId;

    @Column(name="subject_code")
    private Integer subjectCode;

    @Override
    public boolean equals(Object x){
        if(this==x)return true;
        if(x==null || getClass() !=x.getClass())return false;
        StudentSubjectMapId p=(StudentSubjectMapId) x;
        return Objects.equals(p.getStudentId(),this.StudentId)&&Objects.equals(p.getSubjectCode(),this.subjectCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.StudentId,this.subjectCode);
    }
}
