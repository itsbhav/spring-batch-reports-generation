package com.poc.spring_batch_reports_generation.Service;

import com.poc.spring_batch_reports_generation.Model.ResultDTO;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

@Component
public class ResultItemWriterService  implements ItemWriter<List<ResultDTO>> {


    @Override
    public void write(Chunk<? extends List<ResultDTO>> chunk) throws Exception {
        for(List<ResultDTO>resultOfAStudent:chunk){
            if(resultOfAStudent==null||resultOfAStudent.isEmpty())continue;
            ResultDTO studentRes=resultOfAStudent.get(0);
            String sanitizedStudentName = studentRes.getStudentNm().replaceAll(" ", "");
            String fileNm="output/"+studentRes.getRoll()+"_"+sanitizedStudentName+".csv";
            File file=new File(fileNm);
            file.getParentFile().mkdirs();
            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.println("Name: " + studentRes.getStudentNm());
                printWriter.println("Roll: " + studentRes.getRoll());
                printWriter.println(); // empty line
                printWriter.println("Subject Code,Subject Name,Marks Obtained,Total Marks,Percentage");

                int sumObtainedMarks = 0;
                int sumTotalMarks = 0;

                for (ResultDTO resOfASubj : resultOfAStudent) {
                    Integer obtained = resOfASubj.getObtainedMarks();
                    Integer total = resOfASubj.getTotalMarks();
                    double percentage = total != 0 ? (obtained * 100.0 / total) : 0;

                    printWriter.println(resOfASubj.getSubjectCd() + "," +
                            resOfASubj.getSubjectNm() + "," +
                            obtained + "," +
                            total + "," +
                            String.format("%.2f", percentage));

                    sumObtainedMarks += obtained;
                    sumTotalMarks += total;
                }

                printWriter.println(); // empty line
                double overallPercentage = sumTotalMarks != 0 ? (sumObtainedMarks * 100.0 / sumTotalMarks) : 0;
                printWriter.println("Total Obtained Marks: " + sumObtainedMarks);
                printWriter.println("Total Marks: " + sumTotalMarks);
                printWriter.println("Overall Percentage: " + String.format("%.2f", overallPercentage));
            }
        }
    }
}
