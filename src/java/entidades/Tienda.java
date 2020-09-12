/*
 * Entidad Tienda. Hereda de Usuario
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.servlet.http.Part;

/**
 *
 * @author Alberto JMG
 */
@Named(value = "tienda")
@SessionScoped
@Entity
@NamedQuery(name = "buscarPorLoginT", query = "select t from Tienda t where t.nombre_usuario=:nombreUsuarioTienda")
@Table(name = "tienda", uniqueConstraints = @UniqueConstraint(columnNames = "cif"))
@PrimaryKeyJoinColumn(name = "usuarioID")
public class Tienda extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cif", length = 10)
    private String cif;
    @Column(name = "nombre", length = 150)
    private String nombre;
    @Column(name = "responsable", length = 150)
    private String responsable;
    @Column(name = "descripcion", length = 150)
    private String descripcion;
    @Column(name = "nombreAvatar", length = 200)
    private String nombreAvatar;
    @Transient
    private Part fotoSubida;
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "tiendaP", fetch = FetchType.EAGER)
    private List<Producto> productos;
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "tienda", fetch = FetchType.EAGER)
    private List<SubPedido> subPedido;

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getNombreAvatar() {
        return nombreAvatar;
    }

    public void setNombreAvatar(String nombreAvatar) {
        this.nombreAvatar = nombreAvatar;
    }

    public Part getFotoSubida() {
        return fotoSubida;
    }

    public void setFotoSubida(Part fotoSubida) {
        this.fotoSubida = fotoSubida;
    }
    
    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<SubPedido> getSubPedido() {
        return subPedido;
    }

    public void setSubPedido(List<SubPedido> subPedido) {
        this.subPedido = subPedido;
    }

}
