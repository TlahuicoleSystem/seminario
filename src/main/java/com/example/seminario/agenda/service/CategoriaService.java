package com.example.seminario.agenda.service;

import java.util.List;
import com.example.seminario.agenda.Utils.exception.AppException;
import com.example.seminario.agenda.model.CategoriaVO;


public interface CategoriaService {
    void insert (String descripcion) throws AppException;;
    void delete (int id) throws AppException;
    List<CategoriaVO> findAll() throws AppException;;
}
