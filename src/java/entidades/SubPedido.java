/*
 * Entidad subpedido. Refleja cada uno de los subpedidos que componen un Pedido
 */
package entidades;

import java.io.Serializable;
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
@Named(value = "subPedido")
@RequestScoped
@Entity
@NamedQuery(name = "subPedidosTienda", query = "select s from SubPedido s where s.tienda=:id_tienda")
@Table(name = "subPedido")
public class SubPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum EstadoSubPedido {
        PENDIENTE, PREPARADO
    };

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_subPedido")
    private Long id_subPedido;
    @Column(name = "cantidad_producto")
    private int cantidad_producto;
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoSubPedido estado;
    @ManyToOne
    @JoinColumn(name = "producto")
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "tienda")
    private Tienda tienda;
    @ManyToOne
    @JoinColumn(name = "pedido")
    private Pedido pedido;

    public Long getId_subPedido() {
        return id_subPedido;
    }

    public void setId_subPedido(Long id_subPedido) {
        this.id_subPedido = id_subPedido;
    }

    public int getCantidad_producto() {
        return cantidad_producto;
    }

    public void setCantidad_producto(int cantidad_producto) {
        this.cantidad_producto = cantidad_producto;
    }

    public EstadoSubPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoSubPedido estado) {
        this.estado = estado;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}
