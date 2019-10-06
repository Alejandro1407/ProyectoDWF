/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alejo
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcion.findAll", query = "SELECT f FROM Funcion f")
    , @NamedQuery(name = "Funcion.findByCodigo", query = "SELECT f FROM Funcion f WHERE f.codigo = :codigo")
    , @NamedQuery(name = "Funcion.findByHorario", query = "SELECT f FROM Funcion f WHERE f.horario = :horario")
    , @NamedQuery(name = "Funcion.findByFuncion3d", query = "SELECT f FROM Funcion f WHERE f.funcion3d = :funcion3d")})
public class Funcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date horario;
    private Short funcion3d;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcion")
    private List<Entrada> entradaList;
    @JoinColumn(name = "sala", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sala sala;
    @JoinColumn(name = "pelicula", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pelicula pelicula;
    @JoinColumn(name = "idioma", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Idioma idioma;

    public Funcion() {
    }

    public Funcion(String codigo) {
        this.codigo = codigo;
    }

    public Funcion(String codigo, Date horario) {
        this.codigo = codigo;
        this.horario = horario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public Short getFuncion3d() {
        return funcion3d;
    }

    public void setFuncion3d(Short funcion3d) {
        this.funcion3d = funcion3d;
    }

    @XmlTransient
    public List<Entrada> getEntradaList() {
        return entradaList;
    }

    public void setEntradaList(List<Entrada> entradaList) {
        this.entradaList = entradaList;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcion)) {
            return false;
        }
        Funcion other = (Funcion) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.udb.entities.Funcion[ codigo=" + codigo + " ]";
    }
    
}
