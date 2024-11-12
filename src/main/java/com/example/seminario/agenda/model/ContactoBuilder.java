package com.example.seminario.agenda.model;


public class ContactoBuilder {
    
    private ContactoBuilder() {}

    public static ContactoDTO fromVO (ContactoVO contactoVO){
        ContactoVO origin = contactoVO;
        ContactoDTO destin = new ContactoDTO();

        destin.setIdtContacto(origin.getIdtContacto());
        destin.setNombre(origin.getNombre());
        destin.setApellidoM(origin.getApellidoM());
        destin.setApellidoP(origin.getApellidoP());
        destin.setAlias(origin.getAlias());
        destin.setTelefonoA(origin.getTelefonoA());
        destin.setTelefonoB(origin.getTelefonoB());
        destin.setCorreoA(origin.getCorreoA());
        destin.setCorreoB(origin.getCorreoB());
        destin.setDireccion(origin.getDireccion());
        destin.setCodigoPostal(origin.getCodigoPostal());
        destin.setEstado(origin.getEstado());
        destin.setPais(origin.getPais());
        destin.setStatus(origin.getStatus());
        destin.setIdTCategoria(origin.getIdTCategoria().getIdtCategoria());

        return destin;
    }

    public static ContactoVO fromDTO (ContactoDTO contactoDTO){
        ContactoDTO origin = contactoDTO;
        ContactoVO destin = new ContactoVO();

        destin.setIdtContacto(origin.getIdtContacto());
        destin.setNombre(origin.getNombre());
        destin.setApellidoM(origin.getApellidoM());
        destin.setApellidoP(origin.getApellidoP());
        destin.setAlias(origin.getAlias());
        destin.setTelefonoA(origin.getTelefonoA());
        destin.setTelefonoB(origin.getTelefonoB());
        destin.setCorreoA(origin.getCorreoA());
        destin.setCorreoB(origin.getCorreoB());
        destin.setDireccion(origin.getDireccion());
        destin.setCodigoPostal(origin.getCodigoPostal());
        destin.setEstado(origin.getEstado());
        destin.setPais(origin.getPais());
        destin.setStatus(origin.getStatus());

        return destin;
    }
}
