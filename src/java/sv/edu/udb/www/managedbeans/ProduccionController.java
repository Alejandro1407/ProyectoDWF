/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import sv.edu.udb.www.entities.Oficio;
import sv.edu.udb.www.entities.Produccion;
import sv.edu.udb.www.facades.ProduccionFacade;
import sv.edu.udb.www.util.JSFUtil;


@ManagedBean
@ViewScoped
public class ProduccionController implements Serializable{

   
    @EJB
    private ProduccionFacade produccionFacade = new ProduccionFacade();

    private Produccion produccion = null;

    @PostConstruct
    public void init(){
        produccion = new Produccion();
    }
    
     public Produccion getProduccion() {
        return produccion;
    }
    
    public void setProduccion(Produccion produccion) {
        this.produccion = produccion;
    }
    public ProduccionController() {
    }
    public List<Produccion> obtenerDirectores(){
        
        List<Produccion> p = produccionFacade.findAll();
        List<Produccion> a = new ArrayList<>();
        for (Produccion elemento : p) {
            if(elemento.getOficio().getId() == 1){//id de oficio de directores es 1
                a.add(elemento);
            }
        }
        return a;
        
    }
    public void eliminarDirector(){
    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        int id = Integer.parseInt(request.getParameter("id"));
        try{
             setProduccion(produccionFacade.find(id));   
           
            produccionFacade.remove(getProduccion());
            JSFUtil.addSucessMessage("¡Exito! Eliminado correctamente");
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! No se pudo eliminar");
        }
        
    }
    public String insertarDirector(){
     try{
            produccion.setId(1);
            produccion.setOficio(new Oficio(1,"Director"));
            produccionFacade.create(produccion);
            JSFUtil.addSucessMessage("¡Exito! Director Insertado correctamente");
            return "directores";
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! No pudo ser insertado");
            return "AgregarDirector";
        }
    }
        public void eliminarActor(){
    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        int id = Integer.parseInt(request.getParameter("id"));
        try{
            setProduccion(produccionFacade.find(id));   
            produccionFacade.remove(getProduccion());
            JSFUtil.addSucessMessage("¡Exito! Eliminado correctamente");
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! No se pudo eliminar");
        }
        
    }
    public String insertarActor(){
     try{
            produccion.setId(1);
            produccion.setOficio(new Oficio(2,"Actor"));
            produccionFacade.create(produccion);
            JSFUtil.addSucessMessage("¡Exito! Actor Insertado correctamente");
            return "actores";
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! No pudo ser insertado");
            return "AgregarActor";
        }
    }
    
    public List<Produccion> obtenerActores(){
        List<Produccion> p = produccionFacade.findAll();
        List<Produccion> d = new ArrayList<>();
        for (Produccion elemento : p) {
            if(elemento.getOficio().getId() == 2){//id de oficio de actores es 2
                d.add(elemento);
            }
        }
        return d;
    }
}
