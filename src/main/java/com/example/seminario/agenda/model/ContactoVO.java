package com.example.seminario.agenda.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.Table;

@Entity
@Table(name="tcontacto")
@NamedQueries({
        @NamedQuery(name = "ContactoVO.findContactoByCategoria",query = "select c from ContactoVO c where c.status=1 and idTcategoria=:idTcategoria"),
        @NamedQuery(name = "ContactoVO.findContactos",query = "select c from ContactoVO c where c.status=1"),
    })

public class ContactoVO implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idtcontacto;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String alias;
    private String telefonoA;
    private String telefonoB;
    private String correoA;
    private String correoB;
    private String direccion;
    private String codigopostal;
    private String estado;
    private String pais;
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "idtcategoria",referencedColumnName = "idtcategoria")
    private CategoriaVO idTcategoria;


    public Integer getIdtContacto() {
        return idtcontacto;
    }
    public void setIdtContacto(Integer idtContacto) {
        this.idtcontacto = idtContacto;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidoP() {
        return apellidoP;
    }
    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }
    public String getApellidoM() {
        return apellidoM;
    }
    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getTelefonoA() {
        return telefonoA;
    }
    public void setTelefonoA(String telefonoA) {
        this.telefonoA = telefonoA;
    }
    public String getTelefonoB() {
        return telefonoB;
    }
    public void setTelefonoB(String telefonoB) {
        this.telefonoB = telefonoB;
    }
    public String getCorreoA() {
        return correoA;
    }
    public void setCorreoA(String correoA) {
        this.correoA = correoA;
    }
    public String getCorreoB() {
        return correoB;
    }
    public void setCorreoB(String correoB) {
        this.correoB = correoB;
    }
    
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getCodigoPostal() {
        return codigopostal;
    }
    public void setCodigoPostal(String codigoPostal) {
        this.codigopostal = codigoPostal;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public CategoriaVO getIdTCategoria() {
        return idTcategoria;
    }
    public void setIdTCategoria(CategoriaVO idTcategoria) {
        this.idTcategoria = idTcategoria;
    }
    
}
