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

    
}
