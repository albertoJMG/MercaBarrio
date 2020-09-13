/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entidades.Pedido;
import entidades.Tienda;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Alberto JMG
 */
@Named(value = "info")
@RequestScoped
public class Info implements Serializable {

    @Inject
    private Tienda tiendaSeleccionada;
    @Inject
    private Pedido pedidoSeleccionado;

    public Tienda getTiendaSeleccionada() {
        return tiendaSeleccionada;
    }

    public void setTiendaSeleccionada(Tienda tiendaSeleccionada) {
        this.tiendaSeleccionada = tiendaSeleccionada;
    }

    public Pedido getPedidoSeleccionado() {
        return pedidoSeleccionado;
    }

    public void setPedidoSeleccionado(Pedido pedidoSeleccionado) {
        this.pedidoSeleccionado = pedidoSeleccionado;
    }
    
    
    
    
    
}
