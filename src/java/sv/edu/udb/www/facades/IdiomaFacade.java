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
import sv.edu.udb.www.entities.Idioma;
import sv.edu.udb.www.util.JPAUtil;

/**
 *
 * @author alejo
 */
@Stateless
public class IdiomaFacade extends AbstractFacade<Idioma> {

    @PersistenceContext(unitName = "MultiCinemaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IdiomaFacade() {
        super(Idioma.class);
    }
    
    public boolean ValidateIdioma(int id){
    try{
            EntityManager em =  JPAUtil.getEntityManager();
            Query query = em.createNativeQuery("SELECT * FROM idiomapelicula WHERE idioma = ?").setParameter(1,id);
            List<Object[]> result = query.getResultList();
            return false;
        }catch(Exception e){
            return true;
        }
    }
    
}
