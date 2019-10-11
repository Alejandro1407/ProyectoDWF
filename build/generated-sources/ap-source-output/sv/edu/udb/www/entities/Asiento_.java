package sv.edu.udb.www.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.edu.udb.www.entities.Entrada;
import sv.edu.udb.www.entities.Sala;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-11T07:53:07")
@StaticMetamodel(Asiento.class)
public class Asiento_ { 

    public static volatile SingularAttribute<Asiento, Character> columna;
    public static volatile SingularAttribute<Asiento, Character> fila;
    public static volatile ListAttribute<Asiento, Entrada> entradaList;
    public static volatile SingularAttribute<Asiento, Integer> id;
    public static volatile ListAttribute<Asiento, Sala> salaList;

}