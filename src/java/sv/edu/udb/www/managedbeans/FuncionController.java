/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.managedbeans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
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
import java.util.concurrent.TimeUnit;
import sv.edu.udb.www.entities.Fecha;

@Named(value = "funcionController")
@ManagedBean
@javax.faces.bean.ViewScoped
public class FuncionController implements Serializable{

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    
    public int getIdioma() {
        return idioma;
    }

    public void setIdioma(int idioma) {
        this.idioma = idioma;
    }

    public int getPelicula() {
        return pelicula;
    }

    public void setPelicula(int pelicula) {
        this.pelicula = pelicula;
    }

    public int getSala() {
        return sala;
    }

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
    private Pelicula peliculaobj;
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
            List<Funcion> funciones = funcionFacade.ObtenerFunciones(date);
            return funciones;
        } catch(Exception e){
            return null;
        }
    }
    
    public Date getCurrentDate() {
        return date;
    }
    
    public String obtenerFecha(){
        return dateFormat.format(date);
    }
    
    public String obtenerFechaD(){
        String dayNames[] = new DateFormatSymbols().getWeekdays();
        String monthNames[] = new DateFormatSymbols().getMonths();
        Calendar calendar = Calendar.getInstance();
        return (String.format("%s %s de %s de %s",dayNames[calendar.get(Calendar.DAY_OF_WEEK)],calendar.get(Calendar.DAY_OF_MONTH),monthNames[calendar.get(Calendar.MONTH)],calendar.get(Calendar.YEAR)));
    }
    
    public String minhour(){
        DateFormat minhourformat =  new SimpleDateFormat("HH");
        return minhourformat.format(date);
    }
    
    public String minminute(){
        DateFormat minminuteformat =  new SimpleDateFormat("mm");
        return minminuteformat.format(date);
    }
    
    public List<Fecha> obtenerFechas(){
        List<Fecha> fechas =  new ArrayList<>();
        String dayNames[] = new DateFormatSymbols().getWeekdays();
        String monthNames[] = new DateFormatSymbols().getMonths();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        for(int i = 0;i < 8;i++){
            calendar.add(Calendar.DAY_OF_MONTH,1); 
            Fecha fecha =  new Fecha();
            fecha.setDisplayDate(String.format("%s %s de %s de %s",dayNames[calendar.get(Calendar.DAY_OF_WEEK)],calendar.get(Calendar.DAY_OF_MONTH),monthNames[calendar.get(Calendar.MONTH)],calendar.get(Calendar.YEAR)));
            fecha.setDate(dateFormat.format(date).substring(0,8).concat(String.valueOf(Integer.parseInt(dateFormat.format(date).substring(8,10)) + i)));
            fechas.add(fecha);
        } 
        return fechas;
    }
 
    
    public String insertarFuncion() throws ParseException{
        Idioma id = idiomaFacade.find(this.idioma);
        this.funcion.setIdioma(id);
        Pelicula pl = peliculaFacade.find(this.pelicula);
        this.funcion.setPelicula(pl);
        Sala sl = salaFacade.find(this.sala);
        this.funcion.setSala(sl);
        try{
            funcionFacade.create(funcion);
            JSFUtil.addSucessMessage("Se agrego la funcion correctamente");
            return "funciones";
        }catch(Exception e){
            JSFUtil.addErrorMessage("No fue posible agregar la funcion :(");
            return "AgregarFuncion";
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
    
    public String editarFuncion(){
        Idioma id = idiomaFacade.find(this.idioma);
        this.funcion.setIdioma(id);
        Pelicula pl = peliculaFacade.find(this.pelicula);
        this.funcion.setPelicula(pl);
        Sala sl = salaFacade.find(this.sala);
        this.funcion.setSala(sl);
        
        try{
            funcionFacade.edit(funcion);
            JSFUtil.addSucessMessage("Se modifico la funcion correctamente");
            return "funciones";
        }catch(Exception e){
            JSFUtil.addErrorMessage("No fue posible modificar la funcion :(");
            return "editarFuncion";
        }
    }
    
    
}
