package com.example.seminario.agenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.seminario.agenda.model.CategoriaVO;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaVO,Integer> {
    CategoriaVO findById (int idtCategoria);
    List<CategoriaVO> findCategorias ();
}
