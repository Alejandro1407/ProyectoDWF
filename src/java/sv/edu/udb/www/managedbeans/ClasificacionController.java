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
import sv.edu.udb.www.entities.Clasificacion;
import sv.edu.udb.www.facades.ClasificacionFacade;
import sv.edu.udb.www.util.JSFUtil;

@ManagedBean
@ViewScoped
public class ClasificacionController implements Serializable{
    
    @EJB
    private final ClasificacionFacade clasificacionFacade = new ClasificacionFacade();
    private Clasificacion clasificacion = null;
    
    @PostConstruct
    public void init(){
        clasificacion = new Clasificacion();
    }
    
    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public void insertarClasificacion(){
     try{
            clasificacionFacade.create(clasificacion);
            JSFUtil.addSucessMessage("¡Exito! Clasificación Insertado correctamente");
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! No pudo ser insertado");
        }
    }
    public List<Clasificacion> obtenerClasificaciones(){
        try{
           return clasificacionFacade.findAll();
        }catch(Exception e){
            return null;
        }
    }
    public void eliminarClasificacion(){
    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        int id = Integer.parseInt(request.getParameter("id"));
        try{
            clasificacion = clasificacionFacade.find(id);
           if(!clasificacion.getPeliculaList().isEmpty()){
                JSFUtil.addErrorMessage("¡Error! Elimine todas las referencias a esta clasificación e intente de nuevo");
                return;
            }
            clasificacionFacade.remove(clasificacion);
            JSFUtil.addSucessMessage("¡Exito! Eliminado correctamente");
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! No se pudo eliminar");
        }
        
    }
    
    
    
    
    public ClasificacionController() {
    }
    
}
