/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entidades.Producto;
import entidades.Usuario;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import modelo.MercaBarrioModelo;

/**
 *
 * @author Alberto JMG
 */
@Named(value = "buscadorProductosTienda")
@ViewScoped
public class BuscadorProductosTienda implements Serializable{

    /**
     * Creates a new instance of BuscadorProductosTienda
     */
    
    private List<Producto> productos;
    private List<Producto> productosFiltrados;
    
    
    
    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Usuario u = (Usuario) sessionMap.get("usuarioLogeado");
        productos = MercaBarrioModelo.buscarProductosPorTienda(u.getId_usuario());
    }
    
    public boolean filtroProductos(Object value, Object filter, Locale locale){
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        Producto producto = (Producto) value;
        return producto.getNombre().toLowerCase().contains(filterText);
    }
    


    public List<Producto> getProductos() {
        return productosFiltrados;
    }

    public void setProductos(List<Producto> productos) {
        this.productosFiltrados = productos;
    }
    
    
    
    
}
