package sv.edu.udb.www.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.edu.udb.www.entities.Pelicula;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T20:51:13")
@StaticMetamodel(Clasificacion.class)
public class Clasificacion_ { 

    public static volatile ListAttribute<Clasificacion, Pelicula> peliculaList;
    public static volatile SingularAttribute<Clasificacion, Integer> id;
    public static volatile SingularAttribute<Clasificacion, String> clasificacion;

}