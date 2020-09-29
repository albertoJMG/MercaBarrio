package entidades;

import entidades.Cliente;
import entidades.Envio;
import entidades.SubPedido;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-29T21:21:48")
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile SingularAttribute<Pedido, Envio> envio;
    public static volatile SingularAttribute<Pedido, Cliente> cliente;
    public static volatile SingularAttribute<Pedido, Date> fecha_pedido;
    public static volatile SingularAttribute<Pedido, String> lugar_entrega;
    public static volatile SingularAttribute<Pedido, Boolean> confimacion_cliente;
    public static volatile ListAttribute<Pedido, SubPedido> subPedido;
    public static volatile SingularAttribute<Pedido, String> metodo_pago;
    public static volatile SingularAttribute<Pedido, Long> id_pedido;
    public static volatile SingularAttribute<Pedido, Double> importe;
    public static volatile SingularAttribute<Pedido, String> destinatario;

}