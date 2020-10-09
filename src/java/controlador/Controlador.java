/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.Administrador;
import entidades.Cliente;
import entidades.Envio;
import entidades.Pedido;
import entidades.Producto;
import entidades.SubPedido;
import entidades.Tienda;
import entidades.Usuario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import javax.faces.application.FacesMessage;
import modelo.MercaBarrioModelo;
import org.primefaces.PrimeFaces;
import util.MercaBarrioUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Alberto JMG
 */
@Named(value = "controlador")
@RequestScoped
public class Controlador {

    @Inject
    private Cliente cliente;
    @Inject
    private Tienda tienda;
    @Inject
    private Pedido pedido;
    @Inject
    private SubPedido subPedido;
    @Inject
    private Producto producto;
    @Inject
    private Administrador administrador;
    @Inject
    private Envio envio;

    /**
     * Método para comporbar si el Usuario loguerado es del tipo Cliente
     *
     * @param u Objeto -Usuario-
     * @return Booleano para determinar en template.xhtml que zona mostrar (la
     * destinada al Cliente o a la Tienda)
     */
    public boolean esCliente(Usuario u) {
        boolean tipo = false;
        if (u instanceof Cliente) {
            tipo = true;
        }
        return tipo;
    }

    /**
     * Método para comporbar si el Usuario loguerado es del tipo Tienda
     *
     * @param u Objeto -Usuario-
     * @return Booleano para determinar en template.xhtml que zona mostrar (la
     * destinada al Cliente o a la Tienda)
     */
    public boolean esTienda(Usuario u) {
        boolean tipo = false;
        if (u instanceof Tienda) {
            tipo = true;
        }
        return tipo;
    }

    /**
     * Metodo que determina los casos de navegacion según tipo de usuario
     * logeado, cuando se hace click en el logo
     *
     * @return String con el nombre de la web a la que redirigir la aplicación
     */
    public String navegacionLogo() {
        String navegacion;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Usuario u = (Usuario) sessionMap.get("usuarioLogeado");
        if (u instanceof Cliente) {
            navegacion = "clienteInicio";
        } else if (u instanceof Tienda) {
            navegacion = "tiendaInicio";
        } else if (u instanceof Administrador) {
            navegacion = "adminGestion";
        } else {
            navegacion = "index";
        }
        return navegacion;
    }

    /**
     * Metodo para cerrar sesion independientemente del tipo de usuario que sea.
     * En el caso de que el usuario sea tipo Cliente y este no haya confirmado
     * el Pedido, este se elimina. (Por Pedido nos referimos al Pedido que se
     * crea automaticamente al iniciar la sesion)
     *
     * @return String con el nombre de la web a la que redirigir la aplicación,
     * en este caso a index.xhtml
     */
    public String cerrarSesion() {
        String navegacion = null;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Usuario u = (Usuario) sessionMap.get("usuarioLogeado");
        //Si el Usuario logueado es del tipo Cliente, eliminamos el ultimo Pedido(este se realiza automaticamente) 
        //si el Cliente cierra antes de confirmar el Pedido 
        if (u instanceof Cliente) {
            Cliente c = (Cliente) sessionMap.get("usuarioLogeado");
            Pedido p = c.getPedidos().get(c.getPedidos().size() - 1);
            if (!p.isConfimacion_cliente()) {
                MercaBarrioModelo.borrarPedido(p.getId_pedido());
            }
        }
        externalContext.invalidateSession();
        return "index?faces-redirect=true";
    }

    /*
     <<<>>> Metodos de AÑADIR <<<>>>  
     */
    /**
     * Metodo que según si se ha creado correctamente el Cliente redirige a una
     * página o a otra
     *
     * @param c Objeto -Cliente- recibido del formulario de
     * clienteRegistro.xhtml
     * @return Devuelve un String con el nombre de la pagina JSF que debe
     * mostrarse
     * @see MercaBarrioModelo
     */
    public String addNuevoCliente(Cliente c) {
        boolean exito = MercaBarrioModelo.crearCliente(c);
        if (exito) {
            return "index";
        } else {
            String msg = "Ya existe un usuario con ese nombre";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, facesMessage);
            return "clienteRegistro";
        }
    }

    /**
     * Metodo que segun si se ha creado correctamente la Tienda redirige a una
     * página o a otra
     *
     * @param t Objeto -Tienda- recibido del formulario de tiendaRegistro.xhtml
     * @return Devuelve un String con el nombre de la pagina JSF que debe
     * mostrarse
     * @see MercaBarrioModelo
     */
    public String addNuevaTienda(Tienda t) {
        if (t.getFotoSubida() == null) {
            t.setNombreAvatar("logoMB.svg");
        } else {
            String indice = Integer.toString(MercaBarrioModelo.buscarTiendas().size());
            String imagen = indice+t.getFotoSubida().getSubmittedFileName();
            t.setNombreAvatar(imagen);
            
            MercaBarrioUtil.subirFoto(t.getFotoSubida(), indice);
        }
        ////// DE MOMENTO TODA TIENDA QUE SE REGISTE ES INMEDIATAMENTE ACEPTADA EN LA PLATAFORMA,esta funcion deberia ser realizada por los Administradores
        t.setAceptada(true);
        boolean exito = MercaBarrioModelo.crearTienda(t);
        if (exito) {
            return "tiendaLogin";
        } else {
            String msg = "Ya existe un usuario con ese nombre";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, facesMessage);
            return "tiendaRegistro";
        }
    }

    /**
     * Método para persistir un SubPedido
     *
     * @param s Objeto -SubPedido- recibido del formulario de producto.xhtml
     * @return Devuelve un String con el nombre de la pagina JSF que debe
     * mostrarse
     * @see MercaBarrioModelo
     */
    public String addNuevoSubPedido(SubPedido s) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        //Recuperamos al cliente de la sesion
        Cliente cliente = (Cliente) sessionMap.get("usuarioLogeado");
        //Recuperamos el Id del producto (de producto.xhtml)
        String idProducto = externalContext.getRequestParameterMap().get("id");
        //Obtenemos el ultimo pedido del Cliente (logueado) que sera el unico no finalizado
        Pedido ped = cliente.getPedidos().get(cliente.getPedidos().size() - 1);
        //Buscamos el Producto que debemos añadir al SubPedido
        Producto pro = MercaBarrioModelo.buscarProducto(Long.parseLong(idProducto));
        s.setProducto(pro);
        s.setTienda(pro.getTiendaP());
        s.setEstado(SubPedido.EstadoSubPedido.PENDIENTE);
        //Persisitimos el SubPedido
        MercaBarrioModelo.crearSubPedido(s);
        //Añadimos al Pedido el SubPedido
        ped.getSubPedido().add(s);
        ped.setImporte(importeTotal());
        try {
            //Actualizamos el Pedido con el nuevo SubPedido
            MercaBarrioModelo.actualizarPedido(ped);
        } catch (Exception ex) {
            System.err.println("Error al crear subPedido(actualizar Pedido)" + ex.getMessage());
            System.err.println(ex.getStackTrace());
        }

        return "clienteConfirmacionArticulo";
    }

    /**
     * Método para persistir un Producto
     *
     * @param p Objeto -Producto- recibido del formulario de
     * productoRegistro.xhtml
     * @return Devuelve un String con el nombre de la pagina JSF que debe
     * mostrarse
     * @see MercaBarrioModelo
     */
    public String addNuevoProducto(Producto p) {
        //Rescato la Tienda de la sesion para poder hacer las relaciones entre Producto-Tienda, Tienda-Producto.
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Tienda t = (Tienda) sessionMap.get("usuarioLogeado");

        if (MercaBarrioModelo.existeProducto(p.getNombre(), t)) {
            String msg = "Ya existe un producto con el mismo nombre";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, facesMessage);
            return "productoRegistro";
        } else {
            //Nombre y ruta de guardado de la foto del Producto
            String indice = Integer.toString(MercaBarrioModelo.buscarProductos().size());
            String imagen = indice+p.getFotoSubida().getSubmittedFileName();
            MercaBarrioUtil.subirFoto(p.getFotoSubida(), indice);
            p.setEstadoProducto(true);
            p.setNombreFoto(imagen);
            p.setTiendaP(t);
            boolean exito = MercaBarrioModelo.crearProducto(p);
            t.getProductos().add(p);
            try {
                MercaBarrioModelo.actualizarTienda(t);
            } catch (Exception ex) {
                System.err.println("Error al añadir Producto " + ex.getMessage());
                System.err.println(ex.getStackTrace());
            }
            //Cuando ya se ha creado y persistido el Producto, se ha añadido dicho Producto a la Tienda y actualizada esta,
            //se machaca el usuario logueado con la Tienda
            sessionMap.put("usuarioLogeado", t);
            if (exito) {
                return "tiendaProductos?faces-redirect=true";
            } else {
                String msg = "Revise el formulario";
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.addMessage(null, facesMessage);
                return "productoRegistro";
            }
        }
    }

    /*
     <<<>>> Metodos de LOGIN <<<>>>  
     */
    /**
     * Método que realiza el Login del usuario -Administrador-
     *
     * @param u Usuario de tipo Administrador que se recibe del formulário de
     * acceso
     * @return String con el nombre de la web segun si se ha tenido exito
     * logueando o no.
     */
    public String loginAdmin(Usuario u) {
        Administrador admin = new Administrador();
        String casoNavegacion;
        if (MercaBarrioModelo.loginA(u.getNombre_usuario(), u.getPassword()) != null) {
            admin = MercaBarrioModelo.loginA(u.getNombre_usuario(), u.getPassword());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogeado", admin);
            casoNavegacion = "adminGestion?faces-redirect=true";
        } else if (u.getNombre_usuario().equals("superAdmin") && u.getPassword().equals("a")) {
            admin.setNombre_usuario(u.getNombre_usuario());
            admin.setPassword(u.getPassword());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogeado", admin);
            casoNavegacion = "adminGestion?faces-redirect=true";
        } else {
            casoNavegacion = "adminLogin";
        }
        return casoNavegacion;
    }

    /**
     * Método que realiza el Login del usuario. A su vez, crea un Pedido, si
     * este no es confirmado se borrará.
     *
     * @param u Usuario de tipo Cliente que se recibe del formulário de acceso
     * @return String con el nombre de la web segun si se ha tenido exito
     * logueando o no.
     */
    public String clienteLogin(Usuario u) {
        FacesMessage message;
        boolean valido = MercaBarrioModelo.loginC(u.getNombre_usuario(), u.getPassword()) != null;
        if (valido) {
            Cliente c = MercaBarrioModelo.loginC(u.getNombre_usuario(), u.getPassword());
            //Para comprobar si en una anterior sesion el Cliente no ha confirmado el Pedido, si no lo ha hecho se elimina
            if (c.getPedidos().size() > 0) {
                Pedido pBorrar = c.getPedidos().get(c.getPedidos().size() - 1);
                if (!pBorrar.isConfimacion_cliente()) {
                    MercaBarrioModelo.borrarPedido(pBorrar.getId_pedido());
                }
            }
            //Despues de la comprobación anterior, creamos un nuevo Pedido al iniciar la sesion
            Pedido p = new Pedido();
            p.setCliente(c);
            MercaBarrioModelo.crearPedido(p);
            c.getPedidos().add(p);
            try {
                MercaBarrioModelo.actualizarCliente(c);
            } catch (Exception ex) {
                System.err.println("Error al logear" + ex.getMessage());
            }
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogeado", c);
            return "clienteInicio?faces-redirect=true";
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error en el acceso", "El usuario o el password son erroneos");
            FacesContext.getCurrentInstance().addMessage(null, message);
            PrimeFaces.current().ajax().addCallbackParam("loggedIn", valido);
            return "index";
        }
    }

    /**
     * Método que realiza el Login del usuario -Tienda-
     *
     * @param u Usuario de tipo Tienda que se recibe del formulário de acceso
     * @return String con el nombre de la web segun si se ha tenido exito
     * logueando o no.
     * @see MercaBarrioModelo
     */
    public String tiendaLogin(Usuario u) {
        boolean valido = MercaBarrioModelo.loginT(u.getNombre_usuario(), u.getPassword()) != null;

        if (valido) {
            Tienda t = MercaBarrioModelo.loginT(u.getNombre_usuario(), u.getPassword());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogeado", t);
            return "tiendaInicio?faces-redirect=true";
        } else {
            String msg = "El usuario o el password son erroneos";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, facesMessage);
            return "tiendaLogin";
        }
    }

    /*
     <<<>>> Metodos de BUSQUEDA / INFO <<<>>>  
     */
    /**
     * Método que recupera los articulos(los SubPedidos) del Pedido que está
     * realizando el Cliente
     *
     * @return Lista de los SubPedidos (Articulos)
     */
    public List<SubPedido> listaArticulosCarrito() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Cliente c = (Cliente) sessionMap.get("usuarioLogeado");
        List<SubPedido> subPedidos = new LinkedList<>();
        if (!c.getPedidos().get(c.getPedidos().size() - 1).isConfimacion_cliente()) {
            subPedidos = c.getPedidos().get(c.getPedidos().size() - 1).getSubPedido();
        }
//        subPedidosPendientes = c.getPedidos().get(c.getPedidos().size() - 1).getSubPedido();
        return subPedidos;
    }

    /**
     * Metodo que devuelve una lista de Tiendas del mismo barrio/sector del
     * Cliente logueado
     *
     * @return Lista de Tiendas
     * @see MercaBarrioModelo
     */
    public List<Tienda> tiendasBarrioCliente() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Cliente c = (Cliente) sessionMap.get("usuarioLogeado");
        List<Tienda> tiendas;
        List<Tienda> tiendasAdmitidas = new LinkedList<>();
        //Todas las Tiendas de un barrio del mismo barrio que el Cliente
        tiendas = MercaBarrioModelo.buscarTiendasBarrioConcreto(c.getBarrio());
        //De todas las Tiendas solo aquellas que han sido comprobadas y admitidas por los Administradores
        for (Tienda t : tiendas) {
            if (t.isAceptada()) {
                tiendasAdmitidas.add(t);
            }
        }
        return tiendasAdmitidas;
    }

    /**
     * Método que busca una Tienda por un Id dado
     *
     * @param id Id de la Tienda que se quiere buscar
     * @return Devuelve la Tienda que se quiere buscar
     */
    public Tienda buscarTienda(Long id) {
        Tienda t = null;
        return t = MercaBarrioModelo.buscarTienda(id);
    }

    /**
     * Método que busca una Pedido por un Id dado
     *
     * @param id Id del Pedido que se quiere buscar
     * @return Devuelve el Pedido que se quiere buscar
     */
    public Pedido buscarPedido(Long id) {
        Pedido t = null;
        return t = MercaBarrioModelo.buscarPedido(id);
    }

    /**
     * Método para buscar un Producto segun su idProducto
     *
     * @param id Long con el idProducto del Producto
     * @return Devuelve el -Producto-
     */
    public Producto buscarProducto(Long id) {
        Producto p = MercaBarrioModelo.buscarProducto(id);
        return p;
    }

    /**
     * Método para mostar los productos pertenecientes a una Tienda concreta.
     * Método usado en clienteProductosTienda.xhtml
     *
     * @param id_tienda Id de la tienda de la que se quieren mostrar los
     * Productos
     * @return Lista de todos los Productos de la Tienda
     */
    public List<Producto> todosProductosTienda(Long id_tienda) {
        List<Producto> productos;
        productos = MercaBarrioModelo.buscarProductosPorTienda(id_tienda);
        return productos;
    }

    /**
     * Método para mostrar los Productos disponibles (Aquellos que la Tienda no
     * ha dado de baja)
     *
     * @param id_tienda Id de la tienda de la que se quieren mostrar los
     * Productos disponibles
     * @return Lista de todos los Productos disponibles de la Tienda
     */
    public List<Producto> productosDisponiblesTienda(Long id_tienda) {
        List<Producto> productos;
        List<Producto> productosDisponibles = new LinkedList<>();
        productos = MercaBarrioModelo.buscarProductosPorTienda(id_tienda);
        for (Producto p : productos) {
            if (p.isEstadoProducto()) {
                productosDisponibles.add(p);
            }
        }
        return productosDisponibles;
    }

    /**
     * Método que devuelve una lista con los tres últimos Productos de una
     * Tienda
     *
     * @param id_tienda ID de la Tienda cuyos productos se desean mostrar
     * @return Lista de Productos
     */
    public List<Producto> ultimosProductos(Long id_tienda) {
        Tienda t = MercaBarrioModelo.buscarTienda(id_tienda);
        List<Producto> listaUltimosproductos = new LinkedList<>();
        for (Producto p : t.getProductos()) {
            if (p.isEstadoProducto()) {
                listaUltimosproductos.add(p);
            }
        }
        if (listaUltimosproductos.size() > 3) {
            listaUltimosproductos = listaUltimosproductos.subList((listaUltimosproductos.size() - 3), listaUltimosproductos.size());
        }
        return listaUltimosproductos;
    }

    /**
     * Método que facilita los datos para la pagina clienteConfirmacionArticulo.
     * Es el último artículo añadido al pedido por el Cliente
     *
     * @return Devuelve el -Producto-
     */
    public SubPedido productoSeleccionado() {
        Cliente c;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        c = (Cliente) sessionMap.get("usuarioLogeado");
        Pedido pe = c.getPedidos().get(c.getPedidos().size() - 1);
        SubPedido sp = pe.getSubPedido().get(pe.getSubPedido().size() - 1);
//        Producto p = sp.getProducto();
        return sp;
    }

    /**
     * Método que devuelve uns lista con 3 productos aleatorios de la Tienda en
     * concreto
     *
     * @param id_tienda Id de la tienda de la que se quieren mostrar los
     * Productos
     * @return Lista con 3 Productos aleatorio de la Tienda
     */
    public List<Producto> productosRandom(Long id_tienda) {
        List<Producto> pRandom = new LinkedList();
        List<Producto> productos = productosDisponiblesTienda(id_tienda);
//        productos = MercaBarrioModelo.buscarProductosPorTienda(id_tienda);
        int contador = 3;
        if (productos.size() > 3) {
            int index;
            for (int i = 0; i < contador; i++) {
                index = (int) (Math.random() * productos.size());
                pRandom.add(productos.get(index));
                productos.remove(index);
            }
        }
        return pRandom;
    }

    /**
     * Método que busca y devuelve los Pedidos realizados por el usuario Cliente
     *
     * @param id_cliente
     * @return Lista de los Pedidos realizados
     * @see MercaBarrioModelo
     */
    public List<Pedido> pedidosRealizados(String id_cliente) {
        Cliente c = MercaBarrioModelo.buscarCliente(Long.parseLong(id_cliente));
        List<Pedido> todosPedidos = c.getPedidos();
        List<Pedido> pedidosConfirmados = new LinkedList<>();
//        List<Pedido> antiguosPedidos = MercaBarrioModelo.buscarPedidosConfirmados();
        for (Pedido p : todosPedidos) {
            if (p.isConfimacion_cliente()) {
                pedidosConfirmados.add(p);
            }
        }
        Collections.reverse(pedidosConfirmados);
        return pedidosConfirmados;
    }

    /**
     * Método que devuelve los Pedidos(SubPedidos) PENDIENTES de una Tienda en
     * concreto.
     *
     * @param id_tienda Id de la Tienda para la que se busca los
     * Pedidos(SubPedidos) PENDIENTES
     * @return Lista con los Pedidos(SubPedidos) PENDIENTES. El orden es primero
     * los mas recientes
     */
    public List<SubPedido> subPedidosPendientes(String id_tienda) {
        List<SubPedido> subPedidos = MercaBarrioModelo.subPedidosTiendaPendientes(id_tienda);
        Collections.reverse(subPedidos);
        return subPedidos;
    }

    /**
     * Método que devuelve los Pedidos(SubPedidos) de una Tienda en concreto.
     *
     * @param id_tienda Id de la Tienda para la que se busca los Pedidos
     * @return Lista con los Pedidos(SubPedidos). El orden es primero los mas
     * recientes
     */
    public List<SubPedido> todosSubPedidos(String id_tienda) {

        List<SubPedido> subPedidos = MercaBarrioModelo.buscarSubPedidosTienda(id_tienda);
        Collections.reverse(subPedidos);

        return subPedidos;
    }

    /**
     * Método que devuelve una lista con todas las Tiendas registradas
     *
     * @return Lista con todas las Tiendas registradas en la aplicación
     */
    public List<Tienda> todasLasTiendas() {
        List<Tienda> t = MercaBarrioModelo.buscarTiendas();
        return t;
    }

    /*
     <<<>>> Metodos de BORRADO <<<>>>  
     */
    /**
     * Método que borra el Producto deseado
     *
     * @return Devuelve string con la web a la debe ser redirigida
     */
    public String borrarProducto() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String id_producto = externalContext.getRequestParameterMap().get("elProductoABorrar");
        String casoNavegacion = externalContext.getRequestParameterMap().get("destino");
        MercaBarrioModelo.borrarProducto(Long.parseLong(id_producto));
        return casoNavegacion;
    }

    /**
     * Método que borra un articulo del carrito o Pedido del Cliente
     *
     * @param id_subPedido
     */
    public void borrarArticuloCarrito(String id_subPedido) {
//        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        SubPedido sp = MercaBarrioModelo.buscarSubPedido(Long.parseLong(id_subPedido));
        Pedido p = sp.getPedido();
        p.getSubPedido().remove(sp);
        p.setImporte(MercaBarrioUtil.recalcularImporteTotal(p));
        MercaBarrioModelo.actualizarPedido(p);
        Cliente c = p.getCliente();
        System.out.println("IMPORTE---------->------------> " + p.getImporte());
        System.out.println("IMPORTE---------->------------> " + c.getPedidos().get(c.getPedidos().size() - 1).getImporte());
        MercaBarrioModelo.actualizarCliente(c);
        MercaBarrioModelo.borrarArticuloCarrito(Long.parseLong(id_subPedido));

//        Cliente c = MercaBarrioModelo.borrarArticuloCarrito(Long.parseLong(id_subPedido));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogeado", c);
    }

    /*
     <<<>>> Metodos de EDICIÓN<<<>>>  
     */
    /**
     * Método que modifica las credenciales de los Usuarios (independiente del
     * tipo). Si es exitoso, cierra la sesión y nos devuelve a index para
     * iniciar con las nuevas credenciales Si es fallido, devuelve un mensaje de
     * error
     *
     * @param u Usuario (Cliente o Tienda) que modifica sus credenciales
     * @return Devuelve String con el caso de navegacion segun si la
     * modificacion es exitosa o no
     */
    public String editarCredencialesUsuario(Usuario u) {
        String msg;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Usuario usuarioAModificar = (Usuario) sessionMap.get("usuarioLogeado");
        if (u.getNombre_usuario() != null && !MercaBarrioModelo.existeUsuario(u.getNombre_usuario())) {
            usuarioAModificar.setNombre_usuario(u.getNombre_usuario());
            usuarioAModificar.setPassword(u.getPassword());
            try {
                MercaBarrioModelo.actualizarUsuario(usuarioAModificar);
                return cerrarSesion();
            } catch (Exception ex) {
                System.err.println("Error al modificar usuario" + ex.getMessage() + "//" + ex.getStackTrace());
            }
        } else {
            msg = "A ocurrido un problema";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, facesMessage);
            return null;
        }
        return null;
    }

    /**
     * Método que modifica/actualiza los datos del cliente
     *
     * @param c Objeto -Cliente- recibido del formulario de
     * clienteEditarDatos.xhtml
     * @see MercaBarrioModelo
     */
    public void editarDatosCliente(Cliente c) {
        String msg;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Cliente clienteAModificar = (Cliente) sessionMap.get("usuarioLogeado");
        if (!c.getApellidos().isEmpty()) {
            clienteAModificar.setApellidos(c.getApellidos());
        }
        if (!c.getNombre().isEmpty()) {
            clienteAModificar.setNombre(c.getNombre());
        }
        if (!c.getDni().isEmpty()) {
            clienteAModificar.setDni(c.getDni());
        }
        if (!c.getTelefono().isEmpty()) {
            clienteAModificar.setTelefono(c.getTelefono());
        }
        if (c.getDireccion() != null) {
            clienteAModificar.setDireccion(c.getDireccion());
        }
        if (!c.getCp().isEmpty()) {
            clienteAModificar.setCp(c.getCp());
        }
        if (c.getBarrio() != null) {
            clienteAModificar.setBarrio(c.getBarrio());
        }
        if (!c.getEmail().isEmpty()) {
            clienteAModificar.setEmail(c.getEmail());
        }
        if (!c.getNumeroVia().isEmpty()) {
            clienteAModificar.setNumeroVia(c.getNumeroVia());
        }
        if (c.getTipoVIA() != null) {
            clienteAModificar.setTipoVIA(c.getTipoVIA());
        }
        try {
            MercaBarrioModelo.actualizarCliente(clienteAModificar);
            sessionMap.put("usuarioLogeado", clienteAModificar);
            msg = "Datos cambiados correctamente";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, facesMessage);
        } catch (Exception ex) {
            msg = "A ocurrido un problema";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, facesMessage);
        }
    }

    /**
     * Método que modifica/actualiza los datos del tienda
     *
     * @param t Objeto -Tienda- recibido del formulario de
     * tiendaEditarDatos.xhtml
     * @see MercaBarrioModelo
     */
    public void editarDatosTienda(Tienda t) {
        String msg;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Tienda tiendaAModificar = (Tienda) sessionMap.get("usuarioLogeado");
        if (!t.getNombre().isEmpty()) {
            tiendaAModificar.setNombre(t.getNombre());
        }
        if (!t.getCif().isEmpty()) {
            tiendaAModificar.setCif(t.getCif());
        }
        if (!t.getResponsable().isEmpty()) {
            tiendaAModificar.setResponsable(t.getResponsable());
        }
        if (!t.getTelefono().isEmpty()) {
            tiendaAModificar.setTelefono(t.getTelefono());
        }
        if (t.getDireccion() != null) {
            tiendaAModificar.setDireccion(t.getDireccion());
        }
        if (!t.getCp().isEmpty()) {
            tiendaAModificar.setCp(t.getCp());
        }
        if (t.getBarrio() != null) {
            tiendaAModificar.setBarrio(t.getBarrio());
        }
        if (t.getTipoVIA() != null) {
            tiendaAModificar.setTipoVIA(t.getTipoVIA());
        }
        if (!t.getEmail().isEmpty()) {
            tiendaAModificar.setEmail(t.getEmail());
        }
        if (t.getFotoSubida() != null) {
            String imagen = tiendaAModificar.getId_usuario()+t.getFotoSubida().getSubmittedFileName();
            MercaBarrioUtil.subirFoto(t.getFotoSubida(), Long.toString(tiendaAModificar.getId_usuario()));

            tiendaAModificar.setNombreAvatar(imagen);
        }
        try {
            MercaBarrioModelo.actualizarTienda(tiendaAModificar);
            sessionMap.put("usuarioLogeado", tiendaAModificar);
            msg = "Datos cambiados correctamente";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, facesMessage);
        } catch (Exception ex) {
            msg = "A ocurrido un problema";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, facesMessage);
            System.out.println("Error al actualizar tienda" + ex.getMessage() + "//" + ex.getStackTrace());
        }
    }

    /**
     * Método que actualiza/edita un Producto.
     *
     * @param p Objeto -Producto- a actualizar/editar
     * @return
     */
    public String editarProducto(Producto p) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String id_producto = externalContext.getRequestParameterMap().get("id_productoEditar");
//        System.out.println("------------<-----------------<-----------------< " + id_producto);

        Producto productoEditar = MercaBarrioModelo.buscarProducto(Long.parseLong(id_producto));
        //Comprobación de los campos de formulario
        if (!p.getNombre().isEmpty()) {
            productoEditar.setNombre(p.getNombre());
        }
        if (!p.getBreve_descripcion().isEmpty()) {
            productoEditar.setBreve_descripcion(p.getBreve_descripcion());
        }
        if (!p.getDescripcion().isEmpty()) {
            productoEditar.setDescripcion(p.getDescripcion());
        }
        
        //UNIDAD
        
        if (p.getPrecio() > 0) {
            productoEditar.setPrecio(p.getPrecio());
        }
        
        productoEditar.setTipoIVA(p.getTipoIVA());
               
        if(p.getStock()>0){
            productoEditar.setStock(p.getStock());
        }
        
        if (!p.getControles().isEmpty()) {
            productoEditar.setControles(p.getControles());
        }
        if (!p.getCond_conservacion().isEmpty()) {
            productoEditar.setCond_conservacion(p.getCond_conservacion());
        }
        
        if (!p.getAlergenos().isEmpty()) {
            productoEditar.setAlergenos(p.getAlergenos());
        }


        if (p.getFotoSubida() != null) {
            String imagen = productoEditar.getId_producto()+p.getFotoSubida().getSubmittedFileName();
            MercaBarrioUtil.subirFoto(p.getFotoSubida(), Long.toString(productoEditar.getId_producto()));

            productoEditar.setNombreFoto(imagen);
        }
        
        if(p.getCantidadSuministro()>0){
            productoEditar.setCantidadSuministro(p.getCantidadSuministro());
        }
        
        //UNIDAD SUMINISTRO

        try {
            MercaBarrioModelo.actualizarProducto(productoEditar);
        } catch (Exception ex) {
            System.err.println("No se ha podido actualizar el producto: " + ex.getMessage());
        }

        return "tiendaProductos";
//        return "tiendaProductos?faces-redirect=true";
    }

    /**
     * Método que cambia el estado del Pedido(SubPedido). Es cambiado con un
     * usuario Tienda
     *
     * @param id_subPedido Id de SubPedido a actualizar
     */
    public void actualizarEstadoSubPedido(String id_subPedido) {
        SubPedido sp = MercaBarrioModelo.buscarSubPedido(Long.parseLong(id_subPedido));

        sp.setEstado(SubPedido.EstadoSubPedido.PREPARADO);
        MercaBarrioModelo.actualizarSubPedido(sp);
        Pedido p = sp.getPedido();
        Envio env = p.getEnvio();
        boolean estadoEnvio = true;
        List<SubPedido> subPedidos = p.getSubPedido();
//        for (SubPedido sps : subPedidos) {
//            if (!sps.getEstado().equals(SubPedido.EstadoSubPedido.PREPARADO)) {
//                estadoEnvio = false;
//            }
//        }
        for (int i = 0; i < subPedidos.size() && estadoEnvio; i++) {
            if (!subPedidos.get(i).getEstado().equals(SubPedido.EstadoSubPedido.PREPARADO)) {
                estadoEnvio = false;
            }
        }
        if (estadoEnvio) {
            env.setEstado_envio(Envio.EstadoEnvio.EN_TRANSITO);
            MercaBarrioModelo.actualizarEnvio(env);
        }

    }

    /*
     <<<>>> Metodos OTROS <<<>>>  
     */
    /**
     * Método de confirmación de compra. Modifica los atributos(añade fecha y la
     * confirmación del cliente) al Pedido que esta realizando el cliente
     *
     * @param pedido
     * @return Caso de navegación al confirmarse la compra
     */
    public String confirmarCompra(Pedido pedido) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Cliente c = (Cliente) sessionMap.get("usuarioLogeado");
        Pedido pedidoActual = c.getPedidos().get(c.getPedidos().size() - 1);
        List<SubPedido> subPedidos = pedidoActual.getSubPedido();
        //Modificacion del stock de los Productos que contienen el Pedido
        for (SubPedido sp : subPedidos) {
            Producto p = sp.getProducto();
            p.setStock(p.getStock() - sp.getCantidad_producto());
            MercaBarrioModelo.actualizarProducto(p);
        }
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fechaActual = formato.parse(formato.format(fechaActual));
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
        }
        pedidoActual.setFecha_pedido(fechaActual);
        pedidoActual.setConfimacion_cliente(true);
        pedidoActual.setMetodo_pago(pedido.getMetodo_pago());

        Envio env = new Envio();
        env.setEstado_envio(Envio.EstadoEnvio.EN_PROCESO);
        env.setFecha_envio(fechaActual);
        env.setPedidoE(pedidoActual);
        MercaBarrioModelo.crearEnvio(env);
        pedidoActual.setEnvio(env);
        MercaBarrioModelo.actualizarPedido(pedidoActual);

        //Nuevo Pedido inicial una vez que se ha confirmado el anterior
        Pedido p = new Pedido();
        p.setCliente(c);
        MercaBarrioModelo.crearPedido(p);
        c.getPedidos().add(p);
//        System.out.println("<-<-<-<-<-<-<-<-<-<<-<-" + pedido.getMetodo_pago());
        MercaBarrioModelo.actualizarCliente(c);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogeado", c);

        return "clientePedidos";
    }

    /**
     * Método que lee un archivo XML devolviendo lista con la informacion de los
     * nombres de las vias de Sevilla
     *
     * @return Lista con los nombres de las vias de Sevilla
     */
    public List<String> callesSevilla() {

        List todasCalles = new ArrayList();

        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            InputStream input = externalContext.getResourceAsStream("/resources/docs/vias.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse((input));
            NodeList listaCalles = documento.getElementsByTagName("NOMBRE_VIA");
            for (int i = 0; i < listaCalles.getLength(); i++) {
                Node nodo = listaCalles.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) nodo;
                    NodeList hijos = e.getChildNodes();
                    for (int j = 0; j < hijos.getLength(); j++) {
                        Node hijo = hijos.item(j);
                        todasCalles.add(hijo.getNodeValue());
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.err.println(ex.getMessage());
        }
        return todasCalles;
    }

    /**
     * Método que lee un archivo XML devolviendo lista con la informacion de los
     * tipos de vias de las calles de Sevilla
     *
     * @return Lista con los tipos de vias de Sevilla
     */
    public List<String> tipoViasSevilla() {
        List tipoVias = new ArrayList();
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            InputStream input = externalContext.getResourceAsStream("/resources/docs/vias.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse((input));
            NodeList listaTipoVias = documento.getElementsByTagName("TIPO_VIA");
            for (int i = 0; i < listaTipoVias.getLength(); i++) {
                Node nodo = listaTipoVias.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) nodo;
                    NodeList hijos = e.getChildNodes();
                    for (int j = 0; j < hijos.getLength(); j++) {
                        tipoVias.add(hijos.item(j).getNodeValue());
                    }
                }
            }
            Set<String> tiposVia = new HashSet<>(tipoVias);
            tipoVias.clear();
            tipoVias.addAll(tiposVia);
            Collections.sort(tipoVias);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.err.println(ex.getMessage());
        }
        return tipoVias;
    }

    /**
     * Método que lee un archivo XML devolviendo lista con la informacion de los
     * nombre de los barrios de Sevilla
     *
     * @return Lista con los nombres de los barrio de Sevilla
     */
    public List<String> barriosSevilla() {

        List todosBarrios = new ArrayList();

        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            InputStream input = externalContext.getResourceAsStream("/resources/docs/Barrios.kml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse((input));
            NodeList listaBarrios = documento.getElementsByTagName("SimpleData");

            if (listaBarrios != null && listaBarrios.getLength() > 0) {
                for (int j = 0; j < listaBarrios.getLength(); j++) {
                    Element e = (Element) listaBarrios.item(j);
                    if (e.getAttribute("name").equals("Barrio")) {
                        NodeList hijos = e.getChildNodes();
                        for (int i = 0; i < hijos.getLength(); i++) {
                            Node hijo = hijos.item(i);
                            todosBarrios.add(hijo.getNodeValue());

                        }
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.err.println(ex.getMessage());
        }
        Collections.sort(todosBarrios);
        return todosBarrios;
    }

    /**
     * Método que formatea un dato tipo Fecha
     *
     * @param fecha Fecha que se desea formatear
     * @return String de la fecha una vez formateada
     */
    public String formatearFecha(Date fecha) {
        String fechaFormateada = "";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        fechaFormateada = formato.format(fecha);
        return fechaFormateada;
    }

    /**
     * Método que valida a una Tienda pudiendo asi operar con la aplicacion
     *
     * @param id_tienda Id de la Tienda a confirmar
     */
    public void confirmaTienda(Long id_tienda) {
        Tienda t = MercaBarrioModelo.buscarTienda(id_tienda);
        t.setAceptada(true);
        MercaBarrioModelo.actualizarTienda(t);

    }

    /**
     * Método que realiza la confirmacion de entrega del Envio
     *
     * @param id_envio Id del Envio del que se confirma su recepción
     */
    public void confirmarRecepcion(Long id_envio) {
        Envio env = MercaBarrioModelo.buscarEnvio(id_envio);
        env.setEstado_envio(Envio.EstadoEnvio.ENTREGADO);
        Date fechaActual = new Date();
        env.setFecha_recepcion(fechaActual);
        MercaBarrioModelo.actualizarEnvio(env);

    }

    /**
     * Método que calcula el precio de un Produco con el IVA
     *
     * @param id_producto Id del Producto del que se quiere obtener el precio
     * con IVA
     * @return double con el valor de aplicar el IVA al Producto
     */
    public double precioUnProductoIVA(Long id_producto) {
        double importeIVA = 0.0;
        Producto p = MercaBarrioModelo.buscarProducto(id_producto);
        importeIVA = p.getPrecio() * (1 + ((double) p.getTipoIVA() / 100));
        return importeIVA;
    }

    /**
     * Método que calcula el importe de un SubPedido (o lo que es lo mismo de un
     * Producto)
     *
     * @param sp Objeto -SubPedido- del que se quiere obtener el importe
     * @return double del importe total del SubPedido
     */
    public double importeSubPedido(SubPedido sp) {
        double importe;

        String opcion = sp.getProducto().getUnidadSuministro();
        if (opcion.equals("gramo") || opcion.equals("mililitros")) {
            importe = precioUnProductoIVA(sp.getProducto().getId_producto()) * (sp.getCantidad_producto() * ((double) sp.getProducto().getCantidadSuministro() / 1000));
        } else {
            importe = precioUnProductoIVA(sp.getProducto().getId_producto()) * (sp.getCantidad_producto() * sp.getProducto().getCantidadSuministro());
        }

        return importe;
    }

    /**
     * Método que calcula el importe total del Pedido
     *
     * @return Devuelve el importe del Pedido
     */
    public double importeTotal() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Cliente c = (Cliente) sessionMap.get("usuarioLogeado");
        double importeTotal = 0;
        List<SubPedido> listaSubPedidos = c.getPedidos().get(c.getPedidos().size() - 1).getSubPedido();
        for (SubPedido sp : listaSubPedidos) {
            String opcion = sp.getProducto().getUnidadSuministro();
            if (opcion.equals("gramo") || opcion.equals("mililitros")) {
                importeTotal += precioUnProductoIVA(sp.getProducto().getId_producto()) * (sp.getCantidad_producto() * ((double) sp.getProducto().getCantidadSuministro() / 1000));
            } else {
                importeTotal += precioUnProductoIVA(sp.getProducto().getId_producto()) * (sp.getCantidad_producto() * sp.getProducto().getCantidadSuministro());
            }
        }
        return importeTotal;
    }

    /**
     * Método que calcula el importe total del Pedido
     *
     * @return Devuelve el importe del Pedido
     */
    public double importe() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Cliente c = (Cliente) sessionMap.get("usuarioLogeado");
        double importe = c.getPedidos().get(c.getPedidos().size() - 1).getImporte();
        return importe;
    }

}
