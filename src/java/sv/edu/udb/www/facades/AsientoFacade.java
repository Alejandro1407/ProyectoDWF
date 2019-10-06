package sv.edu.udb.www.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.edu.udb.www.entities.Asiento;

@Stateless
public class AsientoFacade extends AbstractFacade<Asiento> {

    @PersistenceContext(unitName = "MultiCinemaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsientoFacade() {
        super(Asiento.class);
    }
    
}
