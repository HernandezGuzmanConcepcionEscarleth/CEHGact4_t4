package com.crudrapido.crudrapido.controller;

import com.crudrapido.crudrapido.entity.Student;
import com.crudrapido.crudrapido.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAll() {
        return studentService.getStudents();
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getById(@PathVariable Long studentId) {
        return studentService.getStudent(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());          // 404
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student saved = studentService.saveOrUpdate(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved); // 201
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> update(@PathVariable Long studentId,
                                          @RequestBody Student student) {
        if (studentService.getStudent(studentId).isEmpty()) {
            return ResponseEntity.notFound().build();                // 404
        }
        student.setStudentId(studentId);
        return ResponseEntity.ok(studentService.saveOrUpdate(student)); // 200
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<Student> partialUpdate(@PathVariable Long studentId,
                                                 @RequestBody Student changes) {
        return studentService.getStudent(studentId)
                .map(existing -> {
                    if (changes.getFirstName() != null) existing.setFirstName(changes.getFirstName());
                    if (changes.getLastName() != null)  existing.setLastName(changes.getLastName());
                    if (changes.getEmail() != null)     existing.setEmail(changes.getEmail());
                    return ResponseEntity.ok(studentService.saveOrUpdate(existing));
                })
                .orElse(ResponseEntity.notFound().build());          // 404
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> delete(@PathVariable Long studentId) {
        if (studentService.getStudent(studentId).isEmpty()) {
            return ResponseEntity.notFound().build();                // 404
        }
        studentService.delete(studentId);
        return ResponseEntity.noContent().build();                   // 204
    }
}