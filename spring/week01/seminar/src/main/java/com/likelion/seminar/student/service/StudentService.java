package com.likelion.seminar.student.service;

import com.likelion.seminar.student.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final List<StudentDTO> studentDTOList;

    public void createStudent(StudentDTO studentDTO) {
        this.studentDTOList.add(studentDTO);
    }

    public List<StudentDTO> getStudents() {
        return this.studentDTOList;
    }

    public StudentDTO getStudentById(String studentId) {

        for (StudentDTO studentDTO : this.studentDTOList) {
            if (studentDTO.getStudentId().equals(studentId)) {
                return studentDTO;
            }
        }

        return null;
    }

    public void updateStudent(String studentId, StudentDTO studentDTO) {

        for (StudentDTO targetStudent : this.studentDTOList) {
            if (targetStudent.getStudentId().equals(studentId)) {
                targetStudent.setName(studentDTO.getName());
                targetStudent.setDateOfBirth(studentDTO.getDateOfBirth());
            }
        }
    }

    public void deleteStudent(String studentId) {

        StudentDTO targetStudent = null;

        for (StudentDTO studentDTO : this.studentDTOList) {
            if (studentDTO.getStudentId().equals(studentId)) {
                targetStudent = studentDTO;
            }
        }

        this.studentDTOList.remove(targetStudent);
    }


}