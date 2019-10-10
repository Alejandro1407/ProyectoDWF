package sv.edu.udb.www.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.edu.udb.www.entities.Entrada;
import sv.edu.udb.www.entities.Tipo;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T15:12:13")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> password;
    public static volatile SingularAttribute<Usuario, Tipo> tipo;
    public static volatile SingularAttribute<Usuario, String> correo;
    public static volatile SingularAttribute<Usuario, String> direccion;
    public static volatile ListAttribute<Usuario, Entrada> entradaList;
    public static volatile SingularAttribute<Usuario, String> dui;
    public static volatile SingularAttribute<Usuario, Integer> id;
    public static volatile SingularAttribute<Usuario, String> telefono;
    public static volatile SingularAttribute<Usuario, String> tarjeta;
    public static volatile SingularAttribute<Usuario, String> nombre;
    public static volatile SingularAttribute<Usuario, Boolean> enabled;

}