/*
 * Entidad Producto
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import javax.servlet.http.Part;

/**
 *
 * @author Alberto JMG
 */
@Named(value = "producto")
@RequestScoped
@Entity
@NamedQuery(name = "existeProducto", query = "select p from Producto p where p.nombre=:nombreProducto and p.tiendaP=:tiendaProducto")
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_producto")
    private Long id_producto;
    @Column(name = "nombre", length = 50)
    private String nombre;
    @Column(name = "descripcion_corta", length = 100)
    private String breve_descripcion;
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    @Column(name = "unidad", length = 5)
    private String unidad;
    @Column(name = "precio")
    private Double precio;
    @Column(name = "tipoIVA")
    private int tipoIVA;
    @Column(name = "stock")
    private int stock;
    @Column(name = "controles", length = 500)
    private String controles;
    @Column(name = "cond_conservacion", length = 500)
    private String cond_conservacion;
    @Column(name = "alergenos", length = 500)
    private String alergenos;
    @Column(name = "foto", length = 200)
    private String nombreFoto;
    @Column(name = "estadoProducto")
    private boolean estadoProducto;
    @Column(name = "cantidad_suministro")
    private int cantidadSuministro;
    @Column(name = "unidad_suministro", length = 20)
    private String unidadSuministro;
    @Transient
    private Part fotoSubida;
    @ManyToOne
    @JoinColumn(name = "tienda")
    private Tienda tiendaP;
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "producto", fetch = FetchType.EAGER)
    private List<SubPedido> subPedido;

    public Long getId_producto() {
        return id_producto;
    }

    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBreve_descripcion() {
        return breve_descripcion;
    }

    public void setBreve_descripcion(String breve_descripcion) {
        this.breve_descripcion = breve_descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getTipoIVA() {
        return tipoIVA;
    }

    public void setTipoIVA(int tipoIVA) {
        this.tipoIVA = tipoIVA;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getControles() {
        return controles;
    }

    public void setControles(String controles) {
        this.controles = controles;
    }

    public String getCond_conservacion() {
        return cond_conservacion;
    }

    public void setCond_conservacion(String cond_conservacion) {
        this.cond_conservacion = cond_conservacion;
    }

    public String getAlergenos() {
        return alergenos;
    }

    public void setAlergenos(String alergenos) {
        this.alergenos = alergenos;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }

    public boolean isEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(boolean estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public int getCantidadSuministro() {
        return cantidadSuministro;
    }

    public void setCantidadSuministro(int cantidadSuministro) {
        this.cantidadSuministro = cantidadSuministro;
    }

    public String getUnidadSuministro() {
        return unidadSuministro;
    }

    public void setUnidadSuministro(String unidadSuministro) {
        this.unidadSuministro = unidadSuministro;
    }

    public Part getFotoSubida() {
        return fotoSubida;
    }

    public void setFotoSubida(Part fotoSubida) {
        this.fotoSubida = fotoSubida;
    }

    public Tienda getTiendaP() {
        return tiendaP;
    }

    public void setTiendaP(Tienda tiendaP) {
        this.tiendaP = tiendaP;
    }

    public List<SubPedido> getSubPedido() {
        return subPedido;
    }

    public void setSubPedido(List<SubPedido> subPedido) {
        this.subPedido = subPedido;
    }

}
