package sv.edu.udb.www.facades;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.edu.udb.www.entities.Token;
import sv.edu.udb.www.entities.Usuario;
import sv.edu.udb.www.util.JPAUtil;
import sv.edu.udb.www.util.JSFUtil;

@Stateless
public class TokenFacade extends AbstractFacade<Token> {

    @PersistenceContext()
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TokenFacade() {
        super(Token.class);
    }
    
    public Token GetToken(String Email, String Token){
     try{
            EntityManager em =  JPAUtil.getEntityManager();
            Query query = em.createNamedQuery("Token.find").setParameter("email",Email).
                                                            setParameter("token", Token);           
           try{
                Token t = (Token) query.getSingleResult();
                return t;
           }catch(Exception e){
               return new Token();
           }
         }catch(Exception e){
             return null;
         }
    }
   
}
