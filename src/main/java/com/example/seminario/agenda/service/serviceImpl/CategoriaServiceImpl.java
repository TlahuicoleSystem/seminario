package com.example.seminario.agenda.service.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.seminario.agenda.Utils.Utils;
import com.example.seminario.agenda.Utils.exception.AppException;
import com.example.seminario.agenda.Utils.exception.AppException404NotFound;
import com.example.seminario.agenda.model.CategoriaVO;
import com.example.seminario.agenda.repository.CategoriaRepository;
import com.example.seminario.agenda.service.CategoriaService;
import com.example.seminario.agenda.service.ContactoService;

@Service
public class CategoriaServiceImpl implements CategoriaService{
    private static final Logger LOG = LoggerFactory.getLogger(ContactoService.class);

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void insert(String descripcion) throws AppException {
       LOG.info("insert-> descripcion: {}",descripcion);
       CategoriaVO categoriaVO;
        try {
            categoriaVO = new CategoriaVO();
            categoriaVO.setIdtCategoria(0);
            categoriaVO.setDescripcion(descripcion);
            categoriaVO.setStatus(1);
            categoriaRepository.save(categoriaVO);
        } catch (Exception e) {
            Utils.raise(e, "Error al inserar la categoria");
        }
    }

    @Override
    public void delete(int id) throws AppException{
        LOG.info("update()->id: {} ",id);
        try {
            CategoriaVO vo = categoriaRepository.findById(id);
            if (vo == null) 
                throw new AppException404NotFound("No se encuentra la categoria");

            if(vo.getStatus() == 0)
                throw new AppException404NotFound("No se encuentra la categoria");

            vo.setStatus(0);

            categoriaRepository.save(vo);
        } catch (Exception e) {
            Utils.raise(e, "Error al actualizar el contacto");
        }
    }

    @Override
    public List<CategoriaVO> findAll() throws AppException{
        List<CategoriaVO> listContactoVOs = null;
        try {
            listContactoVOs = categoriaRepository.findCategorias();  
        } catch (Exception e) {
            Utils.raise(e, "Error Al obtener los datos de contacto");
        }
        return listContactoVOs;
    }

}
