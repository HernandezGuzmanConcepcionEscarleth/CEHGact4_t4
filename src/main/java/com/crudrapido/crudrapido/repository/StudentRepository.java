package com.crudrapido.crudrapido.repository;

import com.crudrapido.crudrapido.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

// jparepository ya trae findall, findbyid, save, deletebyid y paginacion
public interface StudentRepository extends JpaRepository<Student, Long> {
}