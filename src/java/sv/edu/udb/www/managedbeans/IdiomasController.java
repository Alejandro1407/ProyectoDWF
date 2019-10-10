/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.managedbeans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import sv.edu.udb.www.entities.Idioma;
import sv.edu.udb.www.facades.IdiomaFacade;
import sv.edu.udb.www.util.JSFUtil;

@ManagedBean
@ViewScoped
public class IdiomasController implements Serializable{

    @EJB
    private final IdiomaFacade idiomaFacade = new IdiomaFacade();
    private Idioma idioma = null;
    
    @PostConstruct
    public void init(){
        idioma = new Idioma();
    }
    
     public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }
    
    
     public void insertarIdioma(){
     try{
            idiomaFacade.create(getIdioma());
            JSFUtil.addSucessMessage("¡Exito! Idioma Insertado correctamente");
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! No pudo ser insertado");
        }
    }
    
    public List<Idioma> obtenerIdiomas(){
        try{
            return idiomaFacade.findAll();
        }catch(Exception e){
            return null;
        }
    }
      public void eliminarIdioma(){
    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        int id = Integer.parseInt(request.getParameter("id"));
        try{
            setIdioma(idiomaFacade.find(id));   
           if(!idioma.getFuncionList().isEmpty()){
                JSFUtil.addErrorMessage("¡Error! Elimine todas las referencias a esta clasificación e intente de nuevo");
                return;
            }
            idiomaFacade.remove(getIdioma());
            JSFUtil.addSucessMessage("¡Exito! Eliminado correctamente");
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! No se pudo eliminar");
        }
        
    }
    
    public IdiomasController() {
    }
    
}
