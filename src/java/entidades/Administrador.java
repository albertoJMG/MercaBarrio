/*
 * Entidad Administrador. Hereda de Usuario
 */
package entidades;

import java.io.Serializable;
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
@Named(value = "admin")
@SessionScoped
@Entity
@Table(name = "administrador" /*, uniqueConstraints = @UniqueConstraint(columnNames = "adminID")*/)
@PrimaryKeyJoinColumn(name = "usuarioID")
public class Administrador extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "nombre", length = 50)
    private String nombre;
    @Column(name = "apellidos", length = 50)
    private String apellidos;

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

}
