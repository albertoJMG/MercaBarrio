/*
 * Entidad Usuario, recoge a todos los usuario (Administrador, cliente o tienda)
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
@Named(value = "usuario")
@RequestScoped
@Entity
@NamedQuery(name = "existeUsuario", query = "select t from Usuario t where t.nombre_usuario=:nombreUsuario")
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "nombre_usuario"))
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario")
    private Long id_usuario;
    @Column(name = "nombre_usuario", length = 20)
    private String nombre_usuario;
    @Column(name = "password", length = 64)
    private String password;
    @Column(name = "direccion", length = 100)
    private String direccion;
    @Column(name = "barrio", length = 200)
    private String barrio;
    @Column(name = "tipoVIA", length = 10)
    private String tipoVIA;
    @Column(name = "numeroVia", length = 5)
    private String numeroVia;
    @Column(name = "cp", length = 10)
    private String cp;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "telefono", length = 10)
    private String telefono;

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTipoVIA() {
        return tipoVIA;
    }

    public void setTipoVIA(String tipoVIA) {
        this.tipoVIA = tipoVIA;
    }

    public String getNumeroVia() {
        return numeroVia;
    }

    public void setNumeroVia(String numeroVia) {
        this.numeroVia = numeroVia;
    }
    
    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
