/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.facades;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.edu.udb.www.entities.Usuario;
import sv.edu.udb.www.util.JPAUtil;
import sv.edu.udb.www.util.JSFUtil;

/**
 *
 * @author alejo
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext()
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public Usuario IniciarSesion(String usuario,String Password){
        try{
            EntityManager em =  JPAUtil.getEntityManager();
            Query query = em.createNamedQuery("Usuario.iniciarSesion").setParameter("correo",usuario).
                                                                       setParameter("password", JSFUtil.HashPassword(Password));           
            List<Usuario> list = query.getResultList();
            if(list.isEmpty()){
                return new Usuario();
            }else{
                return list.get(0);
            }
         }catch(Exception e){
             return null;
         }
    }
    
    public Usuario FindByCorreo(String Email){
        try{
            EntityManager em =  JPAUtil.getEntityManager();
            Query query = em.createNamedQuery("Usuario.findByCorreo").setParameter("correo",Email); 
            try{
                Usuario u = (Usuario) query.getSingleResult();
                return u;
            }catch(Exception e){
                return new Usuario();
            }
         }catch(Exception e){
             return null;
         }
    }
    
    public boolean ActualizarContraseña(String NewPass,int ID){
        try{
            EntityManager em =  JPAUtil.getEntityManager();
            Query query = em.createNativeQuery("UPDATE usuario SET password = ? WHERE id = ?").setParameter(1,NewPass).
                                                                                               setParameter(2,ID);
            query.executeUpdate();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
        
    public Boolean ValidateExitsEmail(String Email){
          try{
            EntityManager em =  JPAUtil.getEntityManager();
            Query query = em.createNamedQuery("Usuario.findByCorreo").setParameter("correo",Email); 
            try{
                Usuario u = (Usuario) query.getSingleResult();
                return true;
            }catch(Exception e){
                return false;
            }
         }catch(Exception e){
             return null;
         }
    }
    
    public Boolean ValidateExitsDUI(String DUI){
          try{
            EntityManager em =  JPAUtil.getEntityManager();
            Query query = em.createNamedQuery("Usuario.findByDui").setParameter("dui",DUI); 
            try{
                Usuario u = (Usuario) query.getSingleResult();
                return true;
            }catch(Exception e){
                return false;
            }
         }catch(Exception e){
             return null;
         }
    }
    
}
