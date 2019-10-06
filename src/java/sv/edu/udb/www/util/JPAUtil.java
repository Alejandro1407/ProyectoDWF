package sv.edu.udb.www.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class JPAUtil {
    
    private static final EntityManagerFactory emFactory;
    
    static {
            emFactory = Persistence.createEntityManagerFactory("MultiCinemaPU");
    }
    
    public static EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }
    
}