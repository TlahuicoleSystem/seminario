package com.example.seminario.agenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.seminario.agenda.model.CategoriaVO;
import com.example.seminario.agenda.model.ContactoVO;


@Repository
public interface ContactoRepository extends JpaRepository<ContactoVO,Integer>{
    ContactoVO findById(int idTCategoria);
    List<ContactoVO> findContactoByCategoria (CategoriaVO idTcategoria);
    List<ContactoVO> findContactos();
}
