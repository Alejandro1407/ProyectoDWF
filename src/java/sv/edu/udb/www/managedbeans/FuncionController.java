/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.managedbeans;

import static com.sun.xml.bind.util.CalendarConv.formatter;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import sv.edu.udb.www.entities.Funcion;
import sv.edu.udb.www.entities.Idioma;
import sv.edu.udb.www.entities.Pelicula;
import sv.edu.udb.www.entities.Sala;
import sv.edu.udb.www.facades.FuncionFacade;
import sv.edu.udb.www.facades.IdiomaFacade;
import sv.edu.udb.www.facades.PeliculaFacade;
import sv.edu.udb.www.facades.SalaFacade;
import sv.edu.udb.www.util.JPAUtil;
import sv.edu.udb.www.util.JSFUtil;

/**
 *
 * @author denny
 */
@Named(value = "funcionController")
@ManagedBean
@javax.faces.bean.ViewScoped
public class FuncionController implements Serializable{

    /**
     * @return the idioma
     */
    public int getIdioma() {
        return idioma;
    }

    /**
     * @param idioma the idioma to set
     */
    public void setIdioma(int idioma) {
        this.idioma = idioma;
    }

    /**
     * @return the pelicula
     */
    public int getPelicula() {
        return pelicula;
    }

    /**
     * @param pelicula the pelicula to set
     */
    public void setPelicula(int pelicula) {
        this.pelicula = pelicula;
    }

    /**
     * @return the sala
     */
    public int getSala() {
        return sala;
    }

    /**
     * @param sala the sala to set
     */
    public void setSala(int sala) {
        this.sala = sala;
    }

    @EJB
    private IdiomaFacade idiomaFacade = new IdiomaFacade();

    @EJB
    private SalaFacade salaFacade = new SalaFacade();

    @EJB
    private PeliculaFacade peliculaFacade = new PeliculaFacade();
    
    @EJB
    private final FuncionFacade funcionFacade = new FuncionFacade();
    private Funcion funcion;
    private int idioma;
    private int pelicula;
    private int sala;
    /**
     * Creates a new instance of FuncionController
     */
    public FuncionController(){
        
    }
    @PostConstruct
    public void init() {
        funcion = new Funcion();
    }
    
    public void setGenero(Funcion funcion){
        this.funcion = funcion;
    }
    
    public Funcion getFuncion(){
        return funcion;
    }
    
    public List<Funcion> obtenerFunciones(){
        try{
            return funcionFacade.findAll();
        } catch(Exception e){
            return null;
        }
    }
    
    public void insertarFuncion() throws ParseException{
        Idioma id = idiomaFacade.find(this.idioma);
        this.funcion.setIdioma(id);
        Pelicula pl = peliculaFacade.find(this.pelicula);
        this.funcion.setPelicula(pl);
        Sala sl = salaFacade.find(this.sala);
        this.funcion.setSala(sl);
        
        try{
            funcionFacade.create(funcion);
            JSFUtil.addSucessMessage("Se agrego la funcion correctamente");
        }catch(Exception e){
            JSFUtil.addErrorMessage("No fue posible agregar la funcion :(");
        }
    }
    
    public void buscarFuncion(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String id = request.getParameter("id");
        this.funcion = obtenerFuncion(id);
        System.out.println(this.funcion.getCodigo());
    }
    
    public void eliminarFuncion(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String id = request.getParameter("id");
        
        try{
            funcionFacade.remove(obtenerFuncion(id));
            JSFUtil.addSucessMessage("Funcion eliminada correctamente");
        } catch(Exception e){
            JSFUtil.addErrorMessage("No fue posible eliminar la funcion :(");
        }
    }
    
    public Funcion obtenerFuncion(String Codigo){
        try{
            EntityManager em = JPAUtil.getEntityManager();
            Query qr = em.createNamedQuery("Funcion.findByCodigo");
            qr.setParameter("codigo", Codigo);
            JSFUtil.addSucessMessage("Si se encontro xd");
            try{
                return (Funcion) qr.getSingleResult();
            } catch (Exception e){
                JSFUtil.addSucessMessage("No se pudo convertir xd" + e.toString());
                return new Funcion();
            }
        } catch(Exception e){
            JSFUtil.addSucessMessage("No se encontro");
            return null;
        }
    }
    
    public List<Pelicula> obtenerPeliculas(){
        List<Pelicula> peliculasList = peliculaFacade.findAll();
        return peliculasList;
    }
    
    public List<Sala> obtenerSalas(){
        List<Sala> salaList = salaFacade.findAll();
        return salaList;
    }
    
    public List<Idioma> obtenerIdiomas(){
        List<Idioma> idiomaList = idiomaFacade.findAll();
        return idiomaList;
    }
    
    public void editarFuncion(){
        Idioma id = idiomaFacade.find(this.idioma);
        this.funcion.setIdioma(id);
        Pelicula pl = peliculaFacade.find(this.pelicula);
        this.funcion.setPelicula(pl);
        Sala sl = salaFacade.find(this.sala);
        this.funcion.setSala(sl);
        
        try{
            funcionFacade.edit(funcion);
            JSFUtil.addSucessMessage("Se modifico la funcion correctamente");
        }catch(Exception e){
            JSFUtil.addErrorMessage("No fue posible modificar la funcion :(");
        }
    }
    
    
}
