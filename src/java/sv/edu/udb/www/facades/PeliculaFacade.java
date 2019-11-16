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
import sv.edu.udb.www.entities.Funcion;
import sv.edu.udb.www.entities.Pelicula;
import sv.edu.udb.www.entities.Usuario;
import sv.edu.udb.www.util.JPAUtil;
import sv.edu.udb.www.util.JSFUtil;

/**
 *
 * @author alejo
 */
@Stateless
public class PeliculaFacade extends AbstractFacade<Pelicula> {

    @PersistenceContext(unitName = "MultiCinemaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeliculaFacade() {
        super(Pelicula.class);
    }
    
    public boolean validaFuncion(int id){
        try{
            EntityManager em =  JPAUtil.getEntityManager();
            Query query = em.createNamedQuery("Pelicula.findByFuncion").setParameter("id",id);           
            List<Funcion> funcion = query.getResultList();
            if(funcion.isEmpty()){
                return true;
            }else{
                return false;
            }
         }catch(Exception e){
             return false;
         }
    }
    

}
