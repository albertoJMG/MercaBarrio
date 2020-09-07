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
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import modelo.MercaBarrioModelo;
import org.primefaces.PrimeFaces;
import util.MercaBarrioUtil;

/**
 *
 * @author Alberto JMG
 */
@Named(value = "controlador")
@RequestScoped
public class Controlador {

//    @Inject
//    private Cliente cliente;
//    @Inject
//    private Tienda tienda;
//    @Inject
//    private Pedido pedido;
//    @Inject
//    private SubPedido subPedido;
//    @Inject
//    private Producto producto;
//    @Inject
//    private Administrador administrador;
//    @Inject
//    private Envio envio;
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
        //Si el Usuario logueado es del tipo Cliente, eliminamos el ultimo Pedido(se realiza automaticamente) 
        //si el Cliente cierra antes de confirmar el Pedido 
        if (u instanceof Cliente) {
            Cliente c = (Cliente) sessionMap.get("usuarioLogeado");
            Pedido p = c.getPedidos().get(c.getPedidos().size() - 1);
            if (!p.isConfimacion_cliente()) {
                MercaBarrioModelo.borrarPedido(p.getId_pedido());
            }
        }
        externalContext.invalidateSession();
        return "index";
    }

    /*
     <<<>>> Metodos de AÑADIR<<<>>>  
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
            String msg = "El usuario ya existe";
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
        boolean exito = MercaBarrioModelo.crearTienda(t);
        if (exito) {
            return "tiendaLogin";
        } else {
            String msg = "El usuario ya existe";
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
        String id = externalContext.getRequestParameterMap().get("id");
        //Obtenemos el ultimo pedido del Cliente (logueado) que sera el unico no finalizado
        Pedido ped = cliente.getPedidos().get(cliente.getPedidos().size() - 1);
        //Buscamos el Producto que debemos añadir al SubPedido
        Producto pro = MercaBarrioModelo.buscarProducto(Long.parseLong(id));
        s.setProducto(pro);
        s.setTienda(pro.getTiendaP());
        s.setEstado(SubPedido.EstadoSubPedido.PENDIENTE);
        //Persisitimos el SubPedido
        MercaBarrioModelo.crearSubPedido(s);
        //Añadimos al Pedido el SubPedido
        ped.getSubPedido().add(s);
        ped.setImporte(s.getCantidad_producto() * s.getProducto().getPrecio());
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
            String imagen = p.getFotoSubida().getSubmittedFileName();
            MercaBarrioUtil.subirFotoProducto(p.getFotoSubida());
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
            //Cuando ya se ha creado y persistido el Producto, se ha añadido dicho Producto a la Tienda, y actualizada esta
            //se machaca el usuario logueado con la Tienda
            sessionMap.put("usuarioLogeado", t);
            if (exito) {
                return "tiendaProductos";
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
     <<<>>> Metodos de LOGIN<<<>>>  
     */
    /**
     * Método que realiza el Login del usuario. A su vez, crea un Pedido, si
     * este no es confirmado se borrará.
     *
     * @param u Usuario de tipo Cliente que se recibe del formulário de acceso
     * @return String con el nombre de la web segun si se ha tenido exito
     * logueando o no.
     * @see MercaBarrioModelo
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
            return "clienteInicio";
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
            return "tiendaInicio";
//            return "tiendaInicio?faces-redirect=true";
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
        List<SubPedido> subPedidos = c.getPedidos().get(c.getPedidos().size() - 1).getSubPedido();
        return subPedidos;
    }

    /**
     * Metodo que devuelve una lista de Tiendas del mismo barrio/sector del
     * Cliente logueado
     *
     * @return Lista de Tiendas
     * @see MercaBarrioModelo
     */
    public List<Tienda> tiendasBarrio() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Cliente c = (Cliente) sessionMap.get("usuarioLogeado");
        List<Tienda> tiendas;
        tiendas = MercaBarrioModelo.tiendasBarrioConcreto(c.getSector());
        return tiendas;
    }

    /**
     * Método que busca una Tienda por un Id dado
     *
     * @param id Id de la Tienda que se quiere buscar
     * @return Devuelve la Tienda que se quiere buscar
     */
    public Tienda buscarTienda(Long id) {
        Tienda t = null;
        return t = MercaBarrioModelo.buscarTiendaModelo(id);
    }

    /**
     * Método para mostar los productos pertenecientes a una Tienda concreta.
     * Método usado en clienteProductosTienda.xhtml
     *
     * @param id_tienda Id de la tienda de la que se quieren mostrar los
     * Productos
     * @return Lista de todos los Productos de la Tienda
     */
    public List<Producto> verProductos(Long id_tienda) {
        List<Producto> productos;
        productos = MercaBarrioModelo.obtenerProductos(id_tienda);
        return productos;
    }

    /**
     * Método para buscar un Producto segun su id
     *
     * @param id Long con el id del Producto
     * @return Devuelve el -Producto-
     */
    public Producto buscarProducto(Long id) {
        Producto p = MercaBarrioModelo.buscarProducto(id);
        return p;
    }

    /**
     * Método que devuelve una lista con los tres últimos Productos de una
     * Tienda
     *
     * @param id ID de la Tienda cuyos productos se desean mostrar
     * @return Lista de Productos
     */
    public List<Producto> ultimosProductos(Long id) {
        Tienda t = MercaBarrioModelo.buscarTiendaModelo(id);
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
    public Producto productoSeleccionado() {
        Cliente c;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        c = (Cliente) sessionMap.get("usuarioLogeado");
        Pedido pe = c.getPedidos().get(c.getPedidos().size() - 1);
        SubPedido sp = pe.getSubPedido().get(pe.getSubPedido().size() - 1);
        Producto p = sp.getProducto();
        return p;
    }

    /**
     * Método que devuelve uns lista con 3 productos aleatorios de la Tienda en
     * concreto
     *
     * @param id_tienda Id de la tienda de la que se quieren mostrar los
     * Productos
     * @return Lista con 3 Productos aleatorio de la Tienda
     */
    public List<Producto> productosRamdom(Long id_tienda) {
        List<Producto> pRandom = new LinkedList();
        List<Producto> productos;
        productos = MercaBarrioModelo.obtenerProductos(id_tienda);
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
     * @param id
     * @return Lista de los Pedidos realizados
     * @see MercaBarrioModelo
     */
    public List<Pedido> verHistoricoPedidos(String id) {
        Cliente c = MercaBarrioModelo.buscarCliente(Long.parseLong(id));
        List<Pedido> todosPedidos = c.getPedidos();
        List<Pedido> pedidosConfirmados = new LinkedList<>();
//        List<Pedido> antiguosPedidos = MercaBarrioModelo.buscarPedidosConfirmados();
        for (Pedido p : todosPedidos) {
            if (p.isConfimacion_cliente()) {
                pedidosConfirmados.add(p);
            }
        }
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
    public List<SubPedido> subPedidos(String id_tienda) {
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

        List<SubPedido> subPedidos = MercaBarrioModelo.subPedidosTienda(id_tienda);
        Collections.reverse(subPedidos);

        return subPedidos;
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
        if (!c.getDireccion().isEmpty()) {
            clienteAModificar.setDireccion(c.getDireccion());
        }
        if (!c.getCp().isEmpty()) {
            clienteAModificar.setCp(c.getCp());
        }
        if (!c.getSector().isEmpty()) {
            clienteAModificar.setSector(c.getSector());
        }
        if (!c.getEmail().isEmpty()) {
            clienteAModificar.setEmail(c.getEmail());
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
        if (!t.getDireccion().isEmpty()) {
            tiendaAModificar.setDireccion(t.getDireccion());
        }
        if (!t.getCp().isEmpty()) {
            tiendaAModificar.setCp(t.getCp());
        }
        if (!t.getSector().isEmpty()) {
            tiendaAModificar.setSector(t.getSector());
        }
        if (!t.getEmail().isEmpty()) {
            tiendaAModificar.setEmail(t.getEmail());
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
        String id_producto = externalContext.getRequestParameterMap().get("productoEditar");

        Producto productoEditar = MercaBarrioModelo.buscarProducto(Long.parseLong(id_producto));
        //Comprobación de los campos de formulario
        if (!p.getAlergenos().isEmpty()) {
            productoEditar.setAlergenos(p.getAlergenos());
        }
        if (!p.getBreve_descripcion().isEmpty()) {
            productoEditar.setBreve_descripcion(p.getBreve_descripcion());
        }
        if (!p.getCond_conservacion().isEmpty()) {
            productoEditar.setCond_conservacion(p.getCond_conservacion());
        }
        if (!p.getControles().isEmpty()) {
            productoEditar.setControles(p.getControles());
        }
        if (!p.getDescripcion().isEmpty()) {
            productoEditar.setDescripcion(p.getDescripcion());
        }
        if (!p.getNombre().isEmpty()) {
            productoEditar.setNombre(p.getNombre());
        }

        if (p.getPrecio() != null) {
            productoEditar.setPrecio(p.getPrecio());
        }

        if (p.getFotoSubida() != null) {
            String imagen = p.getFotoSubida().getSubmittedFileName();
            MercaBarrioUtil.subirFotoProducto(p.getFotoSubida());

            productoEditar.setNombreFoto(imagen);
        }

        productoEditar.setTipoIVA(p.getTipoIVA());
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
    }

    /*
     <<<>>> Metodos de OTROS<<<>>>  
     */
    /**
     * Método que calcula el importe total del Pedido
     *
     * @return Devuelve el importe del Pedido
     */
    public double importeTotal() {
        double importeTotal = 0;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Cliente c = (Cliente) sessionMap.get("usuarioLogeado");
        List<SubPedido> listaSubPedidos = c.getPedidos().get(c.getPedidos().size() - 1).getSubPedido();
        for (SubPedido sp : listaSubPedidos) {
            importeTotal = importeTotal + sp.getCantidad_producto() * sp.getProducto().getPrecio();
        }

        return importeTotal;
    }

    /**
     * Método de confirmación de compra. Modifica los atributos(añade fecha y la
     * confirmación del cliente) al Pedido que esta realizando el cliente
     *
     * @return Caso de navegación al confirmarse la compra
     */
    public String confirmarCompra() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Cliente c = (Cliente) sessionMap.get("usuarioLogeado");
        Pedido pedidoActual = c.getPedidos().get(c.getPedidos().size() - 1);
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fechaActual = formato.parse(formato.format(fechaActual));
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
        }
        pedidoActual.setFecha_pedido(fechaActual);
        pedidoActual.setConfimacion_cliente(true);
        MercaBarrioModelo.actualizarPedido(pedidoActual);
        return "clientePedidos";
    }

}
