package com.poc.spring_batch_reports_generation.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {
    Integer roll;
    Integer subjectCd;
    String studentNm;
    String subjectNm;
    Integer totalMarks;
    Integer obtainedMarks;
}
