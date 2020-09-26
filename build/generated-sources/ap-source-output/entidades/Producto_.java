package entidades;

import entidades.SubPedido;
import entidades.Tienda;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-26T12:46:29")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, String> descripcion;
    public static volatile SingularAttribute<Producto, String> breve_descripcion;
    public static volatile SingularAttribute<Producto, Boolean> estadoProducto;
    public static volatile ListAttribute<Producto, SubPedido> subPedido;
    public static volatile SingularAttribute<Producto, Long> id_producto;
    public static volatile SingularAttribute<Producto, String> cond_conservacion;
    public static volatile SingularAttribute<Producto, String> nombre;
    public static volatile SingularAttribute<Producto, String> unidad;
    public static volatile SingularAttribute<Producto, Double> precio;
    public static volatile SingularAttribute<Producto, Tienda> tiendaP;
    public static volatile SingularAttribute<Producto, String> controles;
    public static volatile SingularAttribute<Producto, String> alergenos;
    public static volatile SingularAttribute<Producto, String> nombreFoto;
    public static volatile SingularAttribute<Producto, String> unidadSuministro;
    public static volatile SingularAttribute<Producto, Integer> stock;
    public static volatile SingularAttribute<Producto, Integer> tipoIVA;
    public static volatile SingularAttribute<Producto, Integer> cantidadSuministro;

}