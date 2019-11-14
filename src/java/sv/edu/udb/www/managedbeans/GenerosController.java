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
import sv.edu.udb.www.entities.Genero;
import sv.edu.udb.www.facades.GeneroFacade;
import sv.edu.udb.www.util.JSFUtil;

@ManagedBean
@ViewScoped
public class GenerosController implements Serializable {

    @EJB
    private final GeneroFacade generosFacade = new GeneroFacade();
    private Genero genero = null;
    
    @PostConstruct
    public void init() {
        genero = new  Genero();
    }
    
     public Genero getGenero() {
        return genero;
    }
     
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public List<Genero> obtenerGeneros(){
        try{
            return generosFacade.findAll();
        }catch(Exception e){
            return null;
        }
    }
    
    public String insertarGenero(){
        try{
            generosFacade.create(genero);
            JSFUtil.addSucessMessage("¡Exito! Genero Insertado correctamente");
            return "funciones";
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! No pudo ser insertado");
            return "AgregarGenero";
        }
    }
    
    public void eliminarGenero(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        int id = Integer.parseInt(request.getParameter("id"));
        try{
            genero = generosFacade.find(id);
            if(!genero.getPeliculaList().isEmpty()){
                JSFUtil.addErrorMessage("¡Error! Elimine todas las referencias a este genero e intente de nuevo");
                return;
            }
            generosFacade.remove(genero);
            JSFUtil.addSucessMessage("¡Exito! Eliminado correctamente");
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! No se pudo eliminar");
        }
    }
    
    
    public GenerosController() {
    }
    
}
