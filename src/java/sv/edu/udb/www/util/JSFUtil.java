package sv.edu.udb.www.util;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.codec.digest.DigestUtils;

public class JSFUtil {
    
    public static void addErrorMessage(String msg){
        FacesMessage facesmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
        FacesContext.getCurrentInstance().addMessage(null, facesmsg);
    }
    
     public static void addInfoMessage(String msg){
        FacesMessage facesmsg = new FacesMessage(FacesMessage.SEVERITY_WARN,msg,msg);
        FacesContext.getCurrentInstance().addMessage(null, facesmsg);
    }
     
    public static void addSucessMessage(String msg){
       FacesMessage facesmsg = new FacesMessage(FacesMessage.SEVERITY_INFO,msg,msg);
       FacesContext.getCurrentInstance().addMessage(null, facesmsg);
   }
    
   public static String HashPassword(String Password){
       return DigestUtils.sha256Hex(Password);
   }
    
}
