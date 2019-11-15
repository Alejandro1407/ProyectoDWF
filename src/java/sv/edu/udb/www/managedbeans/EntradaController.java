/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.managedbeans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import sv.edu.udb.www.entities.Asiento;
import sv.edu.udb.www.entities.Entrada;
import sv.edu.udb.www.entities.Funcion;
import sv.edu.udb.www.facades.EntradaFacade;
import sv.edu.udb.www.facades.FuncionFacade;
import sv.edu.udb.www.facades.PeliculaFacade;

/**
 *
 * @author alejo
 */
@Named(value = "entradaController")
@Dependent
public class EntradaController {

    @EJB
    private PeliculaFacade peliculaFacade = new PeliculaFacade();
    @EJB
    private EntradaFacade entradaFacade = new EntradaFacade();
    @EJB
    private FuncionFacade funcionFacade = new FuncionFacade();
    private Entrada entrada;
    
      public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public EntradaController() {
    }

    @PostConstruct
    public void init() {
        entrada  = new Entrada();
    }

 
    public void loadEntrada(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        int id;
        try{ 
            id=Integer.parseInt(request.getParameter("id"));
        }
        catch(Exception e){
            return;
        }
        Funcion f =  funcionFacade.find(id);
        List<Entrada> entradas = f.getEntradaList();
        Entrada temp = entradaFacade.find(1);
        this.setEntrada(temp);
    }
    
    public List<Entrada> obtenerEntradas(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        int id;
        try{ 
            id=Integer.parseInt(request.getParameter("id"));
        }
        catch(Exception e){
            return null;
        }
        Funcion f =  funcionFacade.find(id);
        List<Entrada> entradas = f.getEntradaList();
        return entradas;
    }

    public int contarAsientosDisponibles(Integer id) {
        Funcion actual = funcionFacade.find(id);
        List<Entrada> entradas = entradaFacade.findAll();
        List<Asiento> asientos = actual.getSala().getAsientoList();
        int total = asientos.size();
            for (Entrada e : entradas) {
                if(actual.equals(e.getFuncion())){
                   total= total -1;
                    
                }
            }
        

        return total;
    }
    
}
