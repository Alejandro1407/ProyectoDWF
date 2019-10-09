package sv.edu.udb.www.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.edu.udb.www.entities.Entrada;
import sv.edu.udb.www.entities.Idioma;
import sv.edu.udb.www.entities.Pelicula;
import sv.edu.udb.www.entities.Sala;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-07T18:27:40")
@StaticMetamodel(Funcion.class)
public class Funcion_ { 

    public static volatile SingularAttribute<Funcion, String> codigo;
    public static volatile SingularAttribute<Funcion, Short> funcion3d;
    public static volatile SingularAttribute<Funcion, Pelicula> pelicula;
    public static volatile SingularAttribute<Funcion, Date> horario;
    public static volatile SingularAttribute<Funcion, Sala> sala;
    public static volatile ListAttribute<Funcion, Entrada> entradaList;
    public static volatile SingularAttribute<Funcion, Idioma> idioma;

}