/*
 * Entidad envio
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;

/**
 *
 * @author Alberto JMG
 */
@Named(value = "envio")
@RequestScoped
@Entity
@Table(name = "envio")
public class Envio implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum EstadoEnvio {
        EN_PROCESO, EN_TRANSITO, ENTREGADO
    };

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_envio")
    private Long id_envio;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_envio")
    private Date fecha_envio;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_recepcion")
    private Date fecha_recepcion;
    @Enumerated(EnumType.STRING)
    private EstadoEnvio estado_envio;
    @OneToOne
    @JoinColumn(name = "FK_pedidoE")
    private Pedido pedidoE;

    public Long getId_envio() {
        return id_envio;
    }

    public void setId_envio(Long id_envio) {
        this.id_envio = id_envio;
    }

    public Date getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(Date fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public Date getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(Date fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public EstadoEnvio getEstado_envio() {
        return estado_envio;
    }

    public void setEstado_envio(EstadoEnvio estado_envio) {
        this.estado_envio = estado_envio;
    }

    public Pedido getPedidoE() {
        return pedidoE;
    }

    public void setPedidoE(Pedido pedidoE) {
        this.pedidoE = pedidoE;
    }

}
