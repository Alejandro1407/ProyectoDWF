package sv.edu.udb.www.managedbeans;

import sv.edu.udb.www.util.JSFUtil;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.util.Constants;
import sv.edu.udb.www.entities.Tipo;
import sv.edu.udb.www.entities.Token;
import sv.edu.udb.www.entities.Usuario;
import sv.edu.udb.www.facades.TokenFacade;
import sv.edu.udb.www.facades.UsuarioFacade;
import sv.edu.udb.www.util.Mailer;

@ManagedBean
@SessionScoped
public class Sesion implements Serializable{

    
    private Usuario usuario = null;
    
    @EJB
    private final UsuarioFacade usuarioFacade =  new UsuarioFacade();
    @EJB
    private final TokenFacade tokenFacade =  new TokenFacade();
    
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
    
    public void registrarUusario(){
    
        try{
            Boolean ExitsDUI = usuarioFacade.ValidateExitsDUI(usuario.getDui());
            Boolean ExitsEmail = usuarioFacade.ValidateExitsEmail(usuario.getCorreo());
            if(ExitsDUI == null || ExitsEmail == null){
                JSFUtil.addErrorMessage("!Error¡ Datos no pudieron ser validados");
            }
            if(ExitsDUI){
                JSFUtil.addErrorMessage("¡Error! Numero DUI ya registrado");
                return;
            }
            if(ExitsEmail){
                JSFUtil.addErrorMessage("¡Error! Correo electronico ya registrado");
                return;
            }
            Mailer mailer = new Mailer();
            UUID Token = UUID.randomUUID();
            Token token = new Token();
            token.setEmail(usuario.getCorreo());
            token.setToken(Token.toString());
            token.setEnabled(true);
            String Message = "Hola\nBienvenido a Multicinema SV\n"
                            + "\nPara validar su correo entre al siguiente link"
                            + "\nhttp://localhost:21058/ProyectoDWF/index.xhtml?Email=" + token.getEmail() + "&Token=" + token.getToken()
                            + "\n\nSi no hiciste dicha solicitud ignora este mensaje"
                            + "\n\nDepartamento de Administración - Multicinema SV";

            mailer.send(usuario.getCorreo(), "Bienvendio a Multicinema SV" , Message);
            tokenFacade.create(token);
            usuario.setTipo(new Tipo(2,"Cliente"));
            usuario.setPassword(JSFUtil.HashPassword(usuario.getPassword()));
            usuarioFacade.create(usuario);
            JSFUtil.addSucessMessage("¡Exito! Usuario registrado");
        }catch(Exception e){
            JSFUtil.addErrorMessage("¡Error! Usuario no pudo ser registrado");
        }
    
    }
    
    public void validateEmail(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String Email = request.getParameter("Email");
        String Token =  request.getParameter("Token");
        if(Email == null){
            return;
        }
        Token t =  tokenFacade.GetToken(Email, Token);
         if(t == null){
          JSFUtil.addErrorMessage("¡Error! No se pudo validar");
          return;
        }
        if(t.getId() == null ){
          JSFUtil.addInfoMessage("Token ingresado invalido");
          return;
        }
        if(t.getEnabled() == false ){
          JSFUtil.addInfoMessage("Email ya confirmado");
          return;
        } 
        t.setEnabled(false);
        tokenFacade.edit(t);
        JSFUtil.addSucessMessage("Email Confirmado");
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
      if(usuario.getEnabled() == false){
           JSFUtil.addInfoMessage("¡Email no confirmado!");
          return;
      }
     try{
        externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.getSessionMap().put("Session", usuario);
        switch (usuario.getTipo().getTipo()){
            case "Administrador":
                externalContext.redirect("/ProyectoDWF/Administrador/index.xhtml");
                break;
            case "Encargado":
                externalContext.redirect("/ProyectoDWF/Encargado/index.xhtml");
                break;
            case "Dependiente":
                externalContext.redirect("/ProyectoDWF/Dependiente/index.xhtml");
                break;
            case "Cliente":
                externalContext.redirect("/ProyectoDWF/Cliente/index.xhtml");
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
            externalContext.redirect("index.xhtml");
        }catch(Exception e){
            JSFUtil.addInfoMessage("¡Error! No se pudo cerrar sesión");
        }
    }//cerrarSesion
    
    public void recuperarContrasenia(){
       this.usuario = usuarioFacade.FindByCorreo(usuario.getCorreo());
       if(usuario == null){
           JSFUtil.addErrorMessage("¡Error! No se pudo recuperar la contraseña");
           return;
       }
       if(usuario.getId() == null){
           JSFUtil.addErrorMessage("¡Error! Email no corresponde a ningun usuario");
           return;
       }
       try{
        Mailer mailer = new Mailer();
        String Password = Mailer.generatePassword(8);
        String Message = "Hola\nSu solicitud de restablecimiento de Contraseña tuvo exito\n"
                         + "\nSu nueva contraseña: \n"
                         + "\"" + Password + "\""
                         + "\n\nSi no hiciste dicha solicitud ignora este mensaje"
                         + "\n\nDepartamento de Administración - Multicinema SV";

         mailer.send(usuario.getCorreo(), "Recuperación de contraseña" , Message);
         //usuario.setPassword(JSFUtil.HashPassword(Password));
         usuarioFacade.ActualizarContraseña(JSFUtil.HashPassword(Password), usuario.getId());
         JSFUtil.addSucessMessage("¡Exito! Su nueva contraseña fue enviada a: " + usuario.getCorreo());
         return;
       }catch(Exception e){
          JSFUtil.addErrorMessage("¡Error! No se pudo enviar email");
        return;
       }
    }
   
    public Sesion(){
    }
        
}//Clase
