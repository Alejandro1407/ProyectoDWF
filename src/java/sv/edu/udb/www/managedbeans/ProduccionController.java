/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.managedbeans;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.edu.udb.www.entities.Produccion;
import sv.edu.udb.www.facades.ProduccionFacade;

/**
 *
 * @author kevin
 */
@ManagedBean
@ViewScoped
public class ProduccionController {
    @EJB
    private ProduccionFacade produccionFacade = new ProduccionFacade();

    private Produccion produccion = new Produccion();

    /**
     * Creates a new instance of Producciones
     */
    public ProduccionController() {
    }
    public List<Produccion> obtenerDirectores(){
        
        List<Produccion> p = produccionFacade.findAll();
        List<Produccion> a = new ArrayList<>();
        for (Produccion elemento : p) {
            if(elemento.getOficio().getId() == 1){//id de oficio de directores es 1
                a.add(elemento);
            }
        }
        return a;
        
    }
    public List<Produccion> obtenerActores(){
        List<Produccion> p = produccionFacade.findAll();
        List<Produccion> d = new ArrayList<>();
        for (Produccion elemento : p) {
            if(elemento.getOficio().getId() == 2){//id de oficio de actores es 2
                d.add(elemento);
            }
        }
        return d;
    }
}
