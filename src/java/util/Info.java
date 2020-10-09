/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entidades.Pedido;
import entidades.Tienda;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.servlet.http.Part;

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

    public void validateFile(Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        Part file = (Part) value;
        if (file.getSize() > 1024) {
            msgs.add(new FacesMessage("file too big"));
        }
        if (!"image/jpg".equals(file.getContentType())) {
            msgs.add(new FacesMessage("No es un archivo de imagen"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

}
