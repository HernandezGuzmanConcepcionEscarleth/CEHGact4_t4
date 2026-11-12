package com.crudrapido.crudrapido.service;

import com.crudrapido.crudrapido.entity.Student;
import com.crudrapido.crudrapido.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // lista sin paginar
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    // lista paginada
    public Page<Student> getStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    // busca uno por id
    public Optional<Student> getStudent(Long id) {
        return studentRepository.findById(id);
    }

    // crea o actualiza segun tenga id o no
    public Student saveOrUpdate(Student student) {
        return studentRepository.save(student);
    }

    // elimina por id
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}