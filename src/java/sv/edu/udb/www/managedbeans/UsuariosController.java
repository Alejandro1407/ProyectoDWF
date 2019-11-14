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
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import sv.edu.udb.www.entities.Tipo;
import sv.edu.udb.www.entities.Usuario;
import sv.edu.udb.www.facades.TipoFacade;
import sv.edu.udb.www.facades.UsuarioFacade;
import sv.edu.udb.www.util.JSFUtil;

/**
 *
 * @author alejo
 */
@ManagedBean
@ViewScoped
public class UsuariosController implements Serializable{

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @EJB
    private final UsuarioFacade usuarioFacade = new UsuarioFacade();
    @EJB
    private final TipoFacade tipoFacade =  new TipoFacade();
    private Usuario usuario = null;
    private Tipo tipo = null;
    
    @PostConstruct
    public void init(){
        setUsuario(new Usuario());
        setTipo(new Tipo());
    }
    public UsuariosController() {
    }
    
    
    public List<Tipo> obtenerTipos(){
        try{
            return tipoFacade.findAll();
        }catch(Exception e){
            return null;
        }
    }
      public List<Usuario> obtenerUsuarios(){
        try{  
          return usuarioFacade.findAll();
        }catch(Exception e){
            return null;
        }
    }
    public String insertarUsuario(){
     try{
            Boolean ExitsDUI = usuarioFacade.ValidateExitsDUI(usuario.getDui());
            Boolean ExitsEmail = usuarioFacade.ValidateExitsEmail(usuario.getCorreo());
            if(ExitsDUI == null || ExitsEmail == null){
                JSFUtil.addErrorMessage("!Error¡ Datos no pudieron ser validados");
            }
            if(ExitsDUI){
                JSFUtil.addErrorMessage("¡Error! Numero DUI ya registrado");
                return "AgregarUsuario";
            }
            if(ExitsEmail){
                JSFUtil.addErrorMessage("¡Error! Correo electronico ya registrado");
                return "AgregarUsuario";
            }         
            usuario.setEnabled(true);
            usuario.setTipo(tipo);
            usuario.setPassword(JSFUtil.HashPassword(usuario.getPassword()));
            usuarioFacade.create(usuario);
            JSFUtil.addSucessMessage("¡Exito! usuario Insertado correctamente");
            return "usuarios";
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! No pudo ser insertado");
            return "AgregarUsuario";
        }
    }
    public void eliminarUsuario(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        int id = Integer.parseInt(request.getParameter("id"));
        try{
          Usuario u =  usuarioFacade.find(id);
          usuarioFacade.remove(u);
          JSFUtil.addSucessMessage("¡Exito! Se elimino correctamente");
          return;
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! No se pudo elimianr");
        }
    }
     public String editarUsuario(){
        try{
            usuario.setTipo(tipo); 
            usuario.setEnabled(true);
            usuarioFacade.edit(usuario);
            JSFUtil.addSucessMessage("¡Exito! Actualizado correctamente");
            return "usuarios";
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! No pudo actualizarse");
            return "EditarUsuario";
        }
    }
    public void loadUsuario(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        int id;
        try{
             id = Integer.parseInt(request.getParameter("id"));
        }catch(Exception e){
            return;
        }
         usuario = usuarioFacade.find(id);
    }
    
    
}
