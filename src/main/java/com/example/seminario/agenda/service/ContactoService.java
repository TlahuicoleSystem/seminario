package com.example.seminario.agenda.service;

import java.util.List;

import com.example.seminario.agenda.Utils.exception.AppException;
import com.example.seminario.agenda.model.ContactoDTO;
import com.example.seminario.agenda.model.ContactoVO;

public interface ContactoService {

    void insert (ContactoDTO contactoDTO) throws AppException;
    void upddate (int id, ContactoDTO contactoDTO) throws AppException;
    void delete (int id) throws AppException;
    List<ContactoVO> findContactoByCategoria (int idTCategoria) throws AppException;
    List<ContactoVO> findAll() throws AppException;
}
