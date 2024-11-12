package com.example.seminario.agenda.service.serviceImpl;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.seminario.agenda.Utils.Utils;
import com.example.seminario.agenda.Utils.exception.AppException;
import com.example.seminario.agenda.Utils.exception.AppException404NotFound;
import com.example.seminario.agenda.model.CategoriaVO;
import com.example.seminario.agenda.model.ContactoBuilder;
import com.example.seminario.agenda.model.ContactoDTO;
import com.example.seminario.agenda.model.ContactoVO;
import com.example.seminario.agenda.repository.CategoriaRepository;
import com.example.seminario.agenda.repository.ContactoRepository;
import com.example.seminario.agenda.service.ContactoService;

@Service
public class ContactoServiceImpl implements ContactoService{

    private static final Logger LOG = LoggerFactory.getLogger(ContactoService.class);

    @Autowired
    CategoriaRepository xCategoriaRepository;
    @Autowired
    ContactoRepository contactoRepository;

    @Override
    public void insert(ContactoDTO contactoDTO) throws AppException {
        LOG.info("insert-> contactoDTO: {}",contactoDTO);
        ContactoVO contactoVO;
        Optional<CategoriaVO> categoria;
        try {
            contactoVO = ContactoBuilder.fromDTO(contactoDTO);
            contactoVO.setIdtContacto(0);
            contactoVO.setStatus(1);

            categoria = xCategoriaRepository.findById(contactoDTO.getIdTCategoria());
            categoria.ifPresent(contactoVO::setIdTCategoria);

            contactoRepository.save(contactoVO);
        } catch (Exception e) {
            Utils.raise(e, "Error al inserar el contacto");
        }
    }

    @Override
    public void upddate(int id, ContactoDTO contactoDTO) throws AppException {
        LOG.info("update()->id: {} data: {}",id, contactoDTO);
        try {
            ContactoVO vo = contactoRepository.findById(id);
            if (vo == null) 
                throw new AppException404NotFound("No se encuentra el contacto");

            if(vo.getStatus() == 0)
                throw new AppException404NotFound("No se encuentra el contacto");

            vo.setNombre(contactoDTO.getNombre());
            vo.setApellidoM(contactoDTO.getApellidoM());
            vo.setApellidoP(contactoDTO.getApellidoP());
            vo.setAlias(contactoDTO.getAlias());
            vo.setTelefonoA(contactoDTO.getTelefonoA());
            vo.setTelefonoB(contactoDTO.getTelefonoB());
            vo.setCorreoA(contactoDTO.getCorreoA());
            vo.setCorreoB(contactoDTO.getCorreoB());
            vo.setDireccion(contactoDTO.getDireccion());
            vo.setEstado(contactoDTO.getEstado());
            vo.setPais(contactoDTO.getPais());

            contactoRepository.save(vo);
        } catch (Exception e) {
            Utils.raise(e, "Error al actualizar el contacto");
        }
    }

    @Override
    public void delete(int id) throws AppException {
        LOG.info("update()->id: {} ",id);
        try {
            ContactoVO vo = contactoRepository.findById(id);
            if (vo == null) 
                throw new AppException404NotFound("No se encuentra el contacto");

            if(vo.getStatus() == 0)
                throw new AppException404NotFound("No se encuentra el contacto");

            vo.setStatus(0);

            contactoRepository.save(vo);
        } catch (Exception e) {
            Utils.raise(e, "Error al actualizar el contacto");
        }
    }

    @Override
    public List<ContactoVO> findContactoByCategoria(int id) throws AppException {
        List<ContactoVO> listContactoVOs = null;
        CategoriaVO idTCategoria = new CategoriaVO();
        try {
            idTCategoria.setIdtCategoria(id);
            listContactoVOs = contactoRepository.findContactoByCategoria(idTCategoria);
        } catch (Exception e) {
            Utils.raise(e, "Error al obtener los datos de contacto por cateria");
        }
        return listContactoVOs;
    }

    @Override
    public List<ContactoVO> findAll() throws AppException {
        List<ContactoVO> listContactoVOs = null;
        try {
            listContactoVOs = contactoRepository.findContactos();  
        } catch (Exception e) {
            Utils.raise(e, "Error Al obtener los datos de contacto");
        }
        return listContactoVOs;
    }

}
