/*
 * Entidad Pedido
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
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
@Named(value = "pedido")
@SessionScoped
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_pedido")
    private Long id_pedido;
    @Column(name = "destinatario", length = 150)
    private String destinatario;
    @Column(name = "fecha_pedido")
    @Temporal(TemporalType.DATE)
    private Date fecha_pedido;
    @Column(name = "importe")
    private double importe;
    @Column(name = "metodo_pago")
    private String metodo_pago;
    @Column(name = "lugar_entrega", length = 150)
    private String lugar_entrega;
    @Column(name = "confirmacion_cliente")
    private boolean confimacion_cliente = false;
    @ManyToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<SubPedido> subPedido;
    @OneToOne(cascade = CascadeType.ALL,
            mappedBy = "pedidoE", fetch = FetchType.EAGER)
    private Envio envio;

    public Long getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public Date getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Date fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public String getLugar_entrega() {
        return lugar_entrega;
    }

    public void setLugar_entrega(String lugar_entrega) {
        this.lugar_entrega = lugar_entrega;
    }

    public boolean isConfimacion_cliente() {
        return confimacion_cliente;
    }

    public void setConfimacion_cliente(boolean confimacion_cliente) {
        this.confimacion_cliente = confimacion_cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<SubPedido> getSubPedido() {
        return subPedido;
    }

    public void setSubPedido(List<SubPedido> subPedido) {
        this.subPedido = subPedido;
    }

    public Envio getEnvio() {
        return envio;
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
    }

}
