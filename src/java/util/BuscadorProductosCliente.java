/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entidades.Producto;
import entidades.Tienda;
import entidades.Usuario;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
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
@Named(value = "buscadorProductosCliente")
@ViewScoped
public class BuscadorProductosCliente implements Serializable{

       
    private String textoABuscar;
    private Tienda t;
    private List<Producto> productos;
    private List<Producto> productosFiltrados = new LinkedList<>();
    
    @PostConstruct
    public void init() {
        productos = MercaBarrioModelo.buscarProductos();
    }
    
    public void filtrar(){
        System.out.println(productos);
        for(Producto pro: productos){
            if(pro.getNombre().toLowerCase().contains(textoABuscar)){
                
            }
        }
        
    }

    public String getTextoABuscar() {
        return textoABuscar;
    }

    public void setTextoABuscar(String textoABuscar) {
        this.textoABuscar = textoABuscar;
    }

    public Tienda getT() {
        return t;
    }

    public void setT(Tienda t) {
        this.t = t;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Producto> getProductosFiltrados() {
        return productosFiltrados;
    }

    public void setProductosFiltrados(List<Producto> productosFiltrados) {
        this.productosFiltrados = productosFiltrados;
    }
    
    
    
    
}
