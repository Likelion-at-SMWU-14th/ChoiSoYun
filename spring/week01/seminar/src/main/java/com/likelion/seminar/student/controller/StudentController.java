package com.likelion.seminar.student.controller;

import com.likelion.seminar.student.dto.StudentDTO;
import com.likelion.seminar.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    // 학생 등록
    @PostMapping
    public void createStudent(@RequestBody StudentDTO studentDTO) {
        studentService.createStudent(studentDTO);
    }


    @GetMapping
    public List<StudentDTO> getStudents() {
        return studentService.getStudents();
    }


    @GetMapping("/{studentId}")
    public StudentDTO getStudentById(
            @PathVariable("studentId") String studentId
    ) {
        return studentService.getStudentById(studentId);
    }

    @PutMapping("/{studentId}")
    public void updateStudent(
            @PathVariable("studentId") String studentId,
            @RequestBody StudentDTO studentDTO
    ) {
        studentService.updateStudent(studentId, studentDTO);
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(
            @PathVariable("studentId") String studentId
    ) {
        studentService.deleteStudent(studentId);
    }

}