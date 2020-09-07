/*
 * Entida de Cliente. Hereda de Usuario
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.*;

/**
 *
 * @author Alberto JMG
 */
@Named(value = "cliente")
@SessionScoped
@Entity
@NamedQuery(name = "buscarPorLoginC", query = "select c from Usuario c where c.nombre_usuario=:nombreUsuario")
@Table(name = "cliente", uniqueConstraints = @UniqueConstraint(columnNames = "dni"))
@PrimaryKeyJoinColumn(name = "usuarioID")
public class Cliente extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "dni", length = 10)
    private String dni;
    @Column(name = "nombre", length = 50)
    private String nombre;
    @Column(name = "apellidos", length = 50)
    private String apellidos;
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Pedido> pedidos;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

}
