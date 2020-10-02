package entidades;

import entidades.Pedido;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-02T20:04:38")
@StaticMetamodel(Cliente.class)
public class Cliente_ extends Usuario_ {

    public static volatile SingularAttribute<Cliente, String> apellidos;
    public static volatile ListAttribute<Cliente, Pedido> pedidos;
    public static volatile SingularAttribute<Cliente, String> nombre;
    public static volatile SingularAttribute<Cliente, String> dni;

}