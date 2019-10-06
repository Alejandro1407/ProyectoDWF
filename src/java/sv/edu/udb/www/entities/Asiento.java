/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alejo
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asiento.findAll", query = "SELECT a FROM Asiento a")
    , @NamedQuery(name = "Asiento.findByColumna", query = "SELECT a FROM Asiento a WHERE a.columna = :columna")
    , @NamedQuery(name = "Asiento.findByFila", query = "SELECT a FROM Asiento a WHERE a.fila = :fila")
    , @NamedQuery(name = "Asiento.findById", query = "SELECT a FROM Asiento a WHERE a.id = :id")})
public class Asiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    private Character columna;
    @Basic(optional = false)
    @NotNull
    private Character fila;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @ManyToMany(mappedBy = "asientoList")
    private List<Sala> salaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asiento")
    private List<Entrada> entradaList;

    public Asiento() {
    }

    public Asiento(Integer id) {
        this.id = id;
    }

    public Asiento(Integer id, Character columna, Character fila) {
        this.id = id;
        this.columna = columna;
        this.fila = fila;
    }

    public Character getColumna() {
        return columna;
    }

    public void setColumna(Character columna) {
        this.columna = columna;
    }

    public Character getFila() {
        return fila;
    }

    public void setFila(Character fila) {
        this.fila = fila;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<Sala> getSalaList() {
        return salaList;
    }

    public void setSalaList(List<Sala> salaList) {
        this.salaList = salaList;
    }

    @XmlTransient
    public List<Entrada> getEntradaList() {
        return entradaList;
    }

    public void setEntradaList(List<Entrada> entradaList) {
        this.entradaList = entradaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asiento)) {
            return false;
        }
        Asiento other = (Asiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.udb.entities.Asiento[ id=" + id + " ]";
    }
    
}
