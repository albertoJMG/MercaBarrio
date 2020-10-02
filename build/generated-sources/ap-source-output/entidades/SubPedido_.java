package entidades;

import entidades.Pedido;
import entidades.Producto;
import entidades.SubPedido.EstadoSubPedido;
import entidades.Tienda;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-02T20:04:38")
@StaticMetamodel(SubPedido.class)
public class SubPedido_ { 

    public static volatile SingularAttribute<SubPedido, EstadoSubPedido> estado;
    public static volatile SingularAttribute<SubPedido, Tienda> tienda;
    public static volatile SingularAttribute<SubPedido, Integer> cantidad_producto;
    public static volatile SingularAttribute<SubPedido, Pedido> pedido;
    public static volatile SingularAttribute<SubPedido, Producto> producto;
    public static volatile SingularAttribute<SubPedido, Long> id_subPedido;

}