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

    public FuncionFacade() {
        super(Funcion.class);
    }
    
}
