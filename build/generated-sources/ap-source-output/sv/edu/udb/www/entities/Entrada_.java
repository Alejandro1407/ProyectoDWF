package sv.edu.udb.www.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.edu.udb.www.entities.Asiento;
import sv.edu.udb.www.entities.Funcion;
import sv.edu.udb.www.entities.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-11T07:53:07")
@StaticMetamodel(Entrada.class)
public class Entrada_ { 

    public static volatile SingularAttribute<Entrada, String> codigo;
    public static volatile SingularAttribute<Entrada, Usuario> usuario;
    public static volatile SingularAttribute<Entrada, Funcion> funcion;
    public static volatile SingularAttribute<Entrada, Asiento> asiento;

}