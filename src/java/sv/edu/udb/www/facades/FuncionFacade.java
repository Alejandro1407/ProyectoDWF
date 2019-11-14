/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.facades;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.edu.udb.www.entities.Funcion;
import sv.edu.udb.www.entities.Usuario;
import sv.edu.udb.www.util.JPAUtil;
import sv.edu.udb.www.util.JSFUtil;

/**
 *
 * @author alejo
 */
@Stateless
public class FuncionFacade extends AbstractFacade<Funcion> {

    @PersistenceContext(unitName = "MultiCinemaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Funcion> ObtenerFunciones(Date fecha){
     try{
            EntityManager em =  JPAUtil.getEntityManager();
            Query query = em.createNamedQuery("Funcion.findByHorario").setParameter("horario",fecha); 
            try{
               List<Funcion> funciones = query.getResultList();
               return funciones;
            }catch(Exception e){
                return null;
            }
         }catch(Exception e){
             return null;
         }
    }
    public FuncionFacade() {
        super(Funcion.class);
    }
    
}
