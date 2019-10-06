package sv.edu.udb.www.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.edu.udb.www.entities.Asiento;
import sv.edu.udb.www.entities.Funcion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-05T18:40:52")
@StaticMetamodel(Sala.class)
public class Sala_ { 

    public static volatile SingularAttribute<Sala, String> descripcion;
    public static volatile ListAttribute<Sala, Asiento> asientoList;
    public static volatile ListAttribute<Sala, Funcion> funcionList;
    public static volatile SingularAttribute<Sala, Integer> id;

}