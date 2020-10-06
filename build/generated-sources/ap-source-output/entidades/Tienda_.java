package entidades;

import entidades.Producto;
import entidades.SubPedido;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-06T23:26:27")
@StaticMetamodel(Tienda.class)
public class Tienda_ extends Usuario_ {

    public static volatile SingularAttribute<Tienda, String> descripcion;
    public static volatile SingularAttribute<Tienda, String> cif;
    public static volatile SingularAttribute<Tienda, String> responsable;
    public static volatile SingularAttribute<Tienda, Boolean> aceptada;
    public static volatile ListAttribute<Tienda, SubPedido> subPedido;
    public static volatile SingularAttribute<Tienda, String> nombreAvatar;
    public static volatile SingularAttribute<Tienda, String> nombre;
    public static volatile ListAttribute<Tienda, Producto> productos;

}