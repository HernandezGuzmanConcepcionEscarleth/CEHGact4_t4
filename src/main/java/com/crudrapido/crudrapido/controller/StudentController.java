package com.crudrapido.crudrapido.controller;

import com.crudrapido.crudrapido.dto.StudentRequest;
import com.crudrapido.crudrapido.dto.StudentResponse;
import com.crudrapido.crudrapido.entity.Student;
import com.crudrapido.crudrapido.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // lista paginada: get /api/v1/students?page=0&size=5
    @GetMapping
    public Page<StudentResponse> getAll(
            @PageableDefault(size = 5, sort = "studentId", direction = Sort.Direction.ASC) Pageable pageable) {
        return studentService.getStudents(pageable).map(StudentResponse::from);
    }

    // busca uno por id
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponse> getById(@PathVariable Long studentId) {
        return studentService.getStudent(studentId)
                .map(s -> ResponseEntity.ok(StudentResponse.from(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    // crea un estudiante
    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest req) {
        Student student = new Student();
        student.setFirstName(req.firstName());
        student.setLastName(req.lastName());
        student.setEmail(req.email());
        Student saved = studentService.saveOrUpdate(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(StudentResponse.from(saved));
    }

    // actualiza completo
    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long studentId,
                                                  @Valid @RequestBody StudentRequest req) {
        return studentService.getStudent(studentId)
                .map(existing -> {
                    existing.setFirstName(req.firstName());
                    existing.setLastName(req.lastName());
                    existing.setEmail(req.email());
                    return ResponseEntity.ok(StudentResponse.from(studentService.saveOrUpdate(existing)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // elimina
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> delete(@PathVariable Long studentId) {
        if (studentService.getStudent(studentId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        studentService.delete(studentId);
        return ResponseEntity.noContent().build();
    }
}