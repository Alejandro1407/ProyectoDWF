/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.facades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.edu.udb.www.entities.Funcion;
import sv.edu.udb.www.entities.Horarios;
import sv.edu.udb.www.entities.Pelicula;
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

    public List<Pelicula> ObtenerFunciones(Date fecha){
        
     try{
            EntityManager em =  JPAUtil.getEntityManager();
            Query query = em.createNamedQuery("Funcion.findByHorario").setParameter("horario",fecha); 
            try{
               List<Pelicula> funciones = query.getResultList();
               return funciones;
            }catch(Exception e){
                return null;
            }
         }catch(Exception e){
             return null;
         }
    }
    
    public List<Horarios> ObtenerHorarios(int id,Date fecha){
        List<Horarios> horarios = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("HH:mm");
        try{
            EntityManager em =  JPAUtil.getEntityManager();
            Query query = em.createNamedQuery("Funcion.findTimes").setParameter("id",id).
                                                                   setParameter("fecha", fecha); 
            try{
               List<Object[]> ObjectList = query.getResultList();
                for(Object[] array:ObjectList){
                   Horarios h = new Horarios(); 
                   h.setCodigo(array[0].toString());
                   h.setIdioma(array[1].toString());
                   h.setFuncion3d(Boolean.parseBoolean(array[2].toString()));
                   h.setHoraInicio(format.parse(format.format(array[3])));
                   h.setHoraFinal(format.parse(format.format(array[4])));
                   horarios.add(h);
                }
               return horarios;
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
