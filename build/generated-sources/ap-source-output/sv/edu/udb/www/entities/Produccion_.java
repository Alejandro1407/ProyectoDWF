package sv.edu.udb.www.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.edu.udb.www.entities.Oficio;
import sv.edu.udb.www.entities.Pelicula;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-11T07:53:07")
@StaticMetamodel(Produccion.class)
public class Produccion_ { 

    public static volatile ListAttribute<Produccion, Pelicula> actores;
    public static volatile ListAttribute<Produccion, Pelicula> directores;
    public static volatile SingularAttribute<Produccion, Integer> id;
    public static volatile SingularAttribute<Produccion, String> nombre;
    public static volatile SingularAttribute<Produccion, Oficio> oficio;

}