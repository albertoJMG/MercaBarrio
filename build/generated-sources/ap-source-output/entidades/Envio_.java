package entidades;

import entidades.Envio.EstadoEnvio;
import entidades.Pedido;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-06T23:26:27")
@StaticMetamodel(Envio.class)
public class Envio_ { 

    public static volatile SingularAttribute<Envio, Date> fecha_envio;
    public static volatile SingularAttribute<Envio, Pedido> pedidoE;
    public static volatile SingularAttribute<Envio, Long> id_envio;
    public static volatile SingularAttribute<Envio, Date> fecha_recepcion;
    public static volatile SingularAttribute<Envio, EstadoEnvio> estado_envio;

}