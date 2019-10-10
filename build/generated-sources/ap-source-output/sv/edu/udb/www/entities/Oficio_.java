package sv.edu.udb.www.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.edu.udb.www.entities.Produccion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T15:12:13")
@StaticMetamodel(Oficio.class)
public class Oficio_ { 

    public static volatile SingularAttribute<Oficio, String> trabajo;
    public static volatile SingularAttribute<Oficio, Integer> id;
    public static volatile ListAttribute<Oficio, Produccion> produccionList;

}