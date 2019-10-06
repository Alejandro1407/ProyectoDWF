package sv.edu.udb.www.managedbeans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import sv.edu.udb.www.entities.Pelicula;

@ManagedBean
@ApplicationScoped
public class Administrador {

    private Pelicula pelicula;
    
    @PostConstruct
    public void init() {
        pelicula = new Pelicula();
    }
    public Pelicula getPelicula() {
        return pelicula;
    }
    
    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

      public Administrador() {
    }
 
    
}
