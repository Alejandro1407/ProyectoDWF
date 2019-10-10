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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "Pelicula.findAll", query = "SELECT p FROM Pelicula p")
    , @NamedQuery(name = "Pelicula.findById", query = "SELECT p FROM Pelicula p WHERE p.id = :id")
    , @NamedQuery(name = "Pelicula.findByTitulo", query = "SELECT p FROM Pelicula p WHERE p.titulo = :titulo")
    , @NamedQuery(name = "Pelicula.findByDuracion", query = "SELECT p FROM Pelicula p WHERE p.duracion = :duracion")
    , @NamedQuery(name = "Pelicula.findBySubtitulada", query = "SELECT p FROM Pelicula p WHERE p.subtitulada = :subtitulada")
    , @NamedQuery(name = "Pelicula.findByEstreno", query = "SELECT p FROM Pelicula p WHERE p.estreno = :estreno")})
public class Pelicula implements Serializable {

   

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    private String titulo;
    @Basic(optional = false)
    @NotNull
    private int duracion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    private String sinopsis;
    @Basic(optional = false)
    @NotNull
    private short subtitulada;
    @Lob
    private byte[] imagen;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date estreno;
    @JoinTable(name = "director", joinColumns = {
        @JoinColumn(name = "pelicula", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "director", referencedColumnName = "id")})
    @ManyToMany
    private List<Produccion> directores;
    @JoinTable(name = "generopelicula", joinColumns = {
        @JoinColumn(name = "pelicula", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "genero", referencedColumnName = "id")})
    @ManyToMany
    private List<Genero> generoList;
    @JoinTable(name = "reparto", joinColumns = {
        @JoinColumn(name = "pelicula", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "actor", referencedColumnName = "id")})
    @ManyToMany
    private List<Produccion> actores;
    @JoinColumn(name = "clasificacion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Clasificacion clasificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pelicula")
    private List<Funcion> funcionList;
    
    @JoinTable(name = "idiomapelicula", joinColumns = {
        @JoinColumn(name = "pelicula", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "idioma", referencedColumnName = "id")})
    @ManyToMany
    private List<Idioma> idiomaList;

    public Pelicula() {
    }

    public Pelicula(Integer id) {
        this.id = id;
    }

    public Pelicula(Integer id, String titulo, int duracion, String sinopsis, short subtitulada, Date estreno) {
        this.id = id;
        this.titulo = titulo;
        this.duracion = duracion;
        this.sinopsis = sinopsis;
        this.subtitulada = subtitulada;
        this.estreno = estreno;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public short getSubtitulada() {
        return subtitulada;
    }

    public void setSubtitulada(short subtitulada) {
        this.subtitulada = subtitulada;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Date getEstreno() {
        return estreno;
    }

    public void setEstreno(Date estreno) {
        this.estreno = estreno;
    }

    @XmlTransient
    public List<Produccion> getDirectoresList() {
        return directores;
    }

    public void setDirectoresList(List<Produccion> DirectoresList) {
        this.directores = DirectoresList;
    }

    @XmlTransient
    public List<Genero> getGeneroList() {
        return generoList;
    }

    public void setGeneroList(List<Genero> generoList) {
        this.generoList = generoList;
    }

    @XmlTransient
    public List<Produccion> getActoresList() {
        return this.actores;
    }

    public void setActoresList(List<Produccion> actoresList) {
        this.actores = actoresList;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    @XmlTransient
    public List<Funcion> getFuncionList() {
        return funcionList;
    }

    public void setFuncionList(List<Funcion> funcionList) {
        this.funcionList = funcionList;
    }
    @XmlTransient
    public List<Idioma> getIdiomaList() {
        return idiomaList;
    }

    public void setIdiomaList(List<Idioma> idiomaList) {
        this.idiomaList = idiomaList;
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
        if (!(object instanceof Pelicula)) {
            return false;
        }
        Pelicula other = (Pelicula) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.udb.entities.Pelicula[ id=" + id + " ]";
    }
    
}
