package sv.edu.udb.www.managedbeans;

import sv.edu.udb.www.util.JSFUtil;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.util.Constants;
import sv.edu.udb.www.entities.Usuario;
import sv.edu.udb.www.facades.UsuarioFacade;

@ManagedBean
@SessionScoped
public class Sesion implements Serializable{

    private Usuario usuario = null;
    private final UsuarioFacade usuarioFacade =  new UsuarioFacade();
    ExternalContext externalContext;
    HttpSession session;

    @PostConstruct
    public void init() {
        usuario = new  Usuario();
    }
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario Sesion) {
        this.usuario = Sesion;
    }
    
    public void iniciarSesion() {
      
      this.usuario =  usuarioFacade.IniciarSesion(usuario.getCorreo(),usuario.getPassword());
      
      if(usuario == null){
          JSFUtil.addErrorMessage("¡Error! No se pudo iniciar sesion");
          return;
      }
      if(usuario.getId() == null ){
        JSFUtil.addInfoMessage("Usuario y/o contraseña incorrectos");
        return;
      }
     try{
        externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.getSessionMap().put("Session", usuario);
        switch (usuario.getTipo().getTipo()){
            case "Administrador":
                externalContext.redirect("Administrador/index.xhtml");
                break;
            case "Encargado":
                externalContext.redirect("Encargado/index.xhtml");
                break;
            case "Dependiente":
                externalContext.redirect("Dependiente/index.xhtml");
                break;
            case "Cliente":
                externalContext.redirect("Cliente/index.xhtml");
            } 
         }catch(Exception e){
             JSFUtil.addErrorMessage("¡Error! No se pudo redirigir");
         }
    }//inicarSesion
    
    public void cerrarSesion(){
        this.setUsuario(null);
        HttpSession session = (HttpSession) externalContext.getSession(false);
        session.invalidate();
        try{
            externalContext.redirect("Cliente/index.xhtml");
        }catch(Exception e){
            JSFUtil.addInfoMessage("¡Error! No se pudo cerrar sesión");
        }
    }//cerrarSesion
    
    public Sesion(){
    }
        
}//Clase
