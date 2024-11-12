package com.example.seminario.agenda.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.Table;

@Entity
@Table(name="tcategoria")
@NamedQueries({
        @NamedQuery(name = "CategoriaVO.findCategorias",query = "select c from CategoriaVO c where c.status=1")
    })


public class CategoriaVO implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idtcategoria;
    private String descripcion;
    private Integer status;


    public Integer getIdtCategoria() {
        return idtcategoria;
    }
    public void setIdtCategoria(Integer idtCategoria) {
        this.idtcategoria = idtCategoria;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    
}
