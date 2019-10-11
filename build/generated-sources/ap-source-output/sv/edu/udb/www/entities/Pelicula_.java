package sv.edu.udb.www.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.edu.udb.www.entities.Clasificacion;
import sv.edu.udb.www.entities.Funcion;
import sv.edu.udb.www.entities.Genero;
import sv.edu.udb.www.entities.Idioma;
import sv.edu.udb.www.entities.Produccion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-11T07:53:07")
@StaticMetamodel(Pelicula.class)
public class Pelicula_ { 

    public static volatile ListAttribute<Pelicula, Produccion> actores;
    public static volatile SingularAttribute<Pelicula, Short> subtitulada;
    public static volatile SingularAttribute<Pelicula, String> titulo;
    public static volatile SingularAttribute<Pelicula, byte[]> imagen;
    public static volatile SingularAttribute<Pelicula, Date> estreno;
    public static volatile ListAttribute<Pelicula, Funcion> funcionList;
    public static volatile ListAttribute<Pelicula, Idioma> idiomaList;
    public static volatile SingularAttribute<Pelicula, Integer> duracion;
    public static volatile ListAttribute<Pelicula, Produccion> directores;
    public static volatile SingularAttribute<Pelicula, Integer> id;
    public static volatile ListAttribute<Pelicula, Genero> generoList;
    public static volatile SingularAttribute<Pelicula, Clasificacion> clasificacion;
    public static volatile SingularAttribute<Pelicula, String> sinopsis;

}