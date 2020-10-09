/*
 * Clase Modelo para el acceso a la Base de Datos
 */
package modelo;

import entidades.*;
import entidades.dao.*;
import entidades.dao.exceptions.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import util.MercaBarrioUtil;

/**
 *
 * @author Alberto JMG
 */
public class MercaBarrioModelo {

    public static final String PU = "MB_PU";

    /*
     <<<>>> Metodos para LOGIN <<<>>>  
     */
    /**
     * Método para el Login de Administrador. Busca al Usuario del tipo
     * Administrador en la BBDD, crea objeto del tipo Administrador que es usado
     * por el ManageBean Administrador para crear la sesión
     *
     * @param nombreUsuario
     * @param password
     * @return Devuelve un objeto de tipo Administrador
     */
    public static Administrador loginA(String nombreUsuario, String password) {
        Administrador a = null;
        Usuario u = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        Query consultaUsuario = em.createNamedQuery("existeUsuario");
        consultaUsuario.setParameter("nombreUsuario", nombreUsuario);
        List<Usuario> resultadoUsuario = consultaUsuario.getResultList();
        if (resultadoUsuario.size() > 0) {
            u = resultadoUsuario.get(0);
            if (u instanceof Administrador) {
                Query consulta = em.createNamedQuery("buscarPorLoginA");
                consulta.setParameter("nombreUsuario", nombreUsuario);
                List<Administrador> resultado = consulta.getResultList();
                if (resultado.size() > 0) {
                    if (resultado.get(0).getPassword().equals(MercaBarrioUtil.codificarSHA256(password))) {
                        a = resultado.get(0);
                    }
                }
            }
        }
        em.close();
        return a;
    }

    /**
     * Método para el Login de Cliente. Busca al Usuario del tipo Cliente en la
     * BBDD, crea objeto del tipo Cliente que es usado por el ManageBean Cliente
     * para crear la sesión
     *
     * @param nombreUsuario
     * @param password
     * @return Devuelve un objeto de tipo Cliente
     */
    public static Cliente loginC(String nombreUsuario, String password) {
        Cliente c = null;
        Usuario u = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        Query consultaUsuario = em.createNamedQuery("existeUsuario");
        consultaUsuario.setParameter("nombreUsuario", nombreUsuario);
        List<Usuario> resultadoUsuario = consultaUsuario.getResultList();
        if (resultadoUsuario.size() > 0) {
            u = resultadoUsuario.get(0);
            if (u instanceof Cliente) {
                Query consulta = em.createNamedQuery("buscarPorLoginC");
                consulta.setParameter("nombreUsuario", nombreUsuario);
                List<Cliente> resultado = consulta.getResultList();
                if (resultado.size() > 0) {
                    if (resultado.get(0).getPassword().equals(MercaBarrioUtil.codificarSHA256(password))) {
                        c = resultado.get(0);
                    }
                }
            }
        }
        em.close();
        return c;
    }

    /**
     * Método para el Login de Tienda. Busca al Usuario del tipo Tienda en la
     * BBDD, crea objeto del tipo Tienda que es usado por el ManageBean Tienda
     * para crear la sesión
     *
     * @param nombreUsuario
     * @param password
     * @return Devuelve un objeto de tipo Tienda
     */
    public static Tienda loginT(String nombreUsuario, String password) {
        Tienda t = null;
        Usuario u = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        Query consultaUsuario = em.createNamedQuery("existeUsuario");
        consultaUsuario.setParameter("nombreUsuario", nombreUsuario);
        List<Usuario> resultadoUsuario = consultaUsuario.getResultList();
        if (resultadoUsuario.size() > 0) {
            u = resultadoUsuario.get(0);
            if (u instanceof Tienda) {
                Query consulta = em.createNamedQuery("buscarPorLoginT");
                consulta.setParameter("nombreUsuarioTienda", nombreUsuario);
                List<Tienda> resultado = consulta.getResultList();
                if (resultado.size() > 0) {
                    if (resultado.get(0).getPassword().equals(MercaBarrioUtil.codificarSHA256(password)) && resultado.get(0).isAceptada()) {
                        t = resultado.get(0);
                    }
                }
            }
        }
        em.close();
        return t;
    }

    /*
     <<<>>> Metodos de CREACION ENTIDADES <<<>>>  
     */
    /**
     * Método que hace la persistencia del objeto tipo Administrador. Comprueba
     * si existe un Usuario con el mismo nombre_usuario
     *
     * @param a Objeto que se persistira en la BBDD
     * @return Booleano que indica si se ha tenido exito al hacer la
     * persistencia.
     */
    public static boolean crearAdmin(Administrador a) {
        boolean exito;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        AdministradorJpaController ejc = new AdministradorJpaController(emf);

        if (!existeUsuario(a.getNombre_usuario())) {
            try {
                a.setPassword(MercaBarrioUtil.codificarSHA256(a.getPassword()));
                ejc.create(a);
                return exito = true;
            } catch (Exception ex) {
                System.err.println("Error al crear al cliente: " + ex.getMessage());
                System.err.println(ex.getStackTrace());
                return exito = false;
            }
        } else {
            return exito = false;
        }

    }

    /**
     * Método que hace la persistencia del objeto tipo Cliente. Comprueba si
     * existe un Usuario con el mismo nombre_usuario
     *
     * @param c Objeto que se persistira en la BBDD
     * @return Booleano que indica si se ha tenido exito al hacer la
     * persistencia.
     */
    public static boolean crearCliente(Cliente c) {
        boolean exito;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ClienteJpaController ejc = new ClienteJpaController(emf);

        if (!existeUsuario(c.getNombre_usuario())) {
            try {
                c.setPassword(MercaBarrioUtil.codificarSHA256(c.getPassword()));
                ejc.create(c);
                return exito = true;
            } catch (Exception ex) {
                System.err.println("Error al crear al cliente: " + ex.getMessage());
                System.err.println(ex.getStackTrace());
                return exito = false;
            }
        } else {
            return exito = false;
        }

    }

    /**
     * Método que hace la persistencia del objeto tipo Tienda. Comprueba si
     * existe un Usuario con el mismo nombre_usuario
     *
     * @param t Objeto que se persistira en la BBDD
     * @return Booleano que indica si se ha tenido exito al hacer la
     * persistencia.
     */
    public static boolean crearTienda(Tienda t) {
        boolean exito;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        TiendaJpaController ejc = new TiendaJpaController(emf);
        if (!existeUsuario(t.getNombre_usuario())) {
            try {
                t.setPassword(MercaBarrioUtil.codificarSHA256(t.getPassword()));
                ejc.create(t);
                return exito = true;
            } catch (Exception ex) {
                System.err.println("Error al crear la Tienda: " + ex.getMessage());
                System.err.println(ex.getStackTrace());
                return exito = false;
            }
        } else {
            return exito = false;
        }
    }

    /**
     * Método que hace la persistencia del objeto tipo Producto
     *
     * @param p Objeto que se persistira en la BBDD
     * @return Booleano que indica si se ha tenido exito al hacer la
     * persistencia.
     */
    public static boolean crearProducto(Producto p) {
        boolean exito;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ProductoJpaController ejc = new ProductoJpaController(emf);
        try {
            ejc.create(p);
            return exito = true;
        } catch (Exception ex) {
            System.err.println("Error al crear el producto: " + ex.getMessage());
            System.err.println(ex.getStackTrace());
            return exito = false;
        }

    }

    /**
     * Método que hace la persistencia del objeto tipo Pedido
     *
     * @param p Objeto que se persistira en la BBDD
     */
    public static void crearPedido(Pedido p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        PedidoJpaController ejc = new PedidoJpaController(emf);
        try {
            ejc.create(p);
        } catch (Exception ex) {
            System.err.println("Error al crear el pedido: " + ex.getMessage());
            System.err.println(ex.getStackTrace());
        }
    }

    /**
     * Método que hace la persistencia del objeto tipo SubPedido
     *
     * @param s Objeto que se persistira en la BBDD
     */
    public static void crearSubPedido(SubPedido s) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        SubPedidoJpaController ejc = new SubPedidoJpaController(emf);
        try {
            ejc.create(s);
        } catch (Exception ex) {
            System.err.println("Error al crear el subpedido: " + ex.getMessage());
            System.err.println(ex.getStackTrace());
        }
    }

    /**
     * Método que hace la persistencia del objeto tipo Envio
     *
     * @param env Objeto que se persistira en la BBDD
     */
    public static void crearEnvio(Envio env) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EnvioJpaController ejc = new EnvioJpaController(emf);
        try {
            ejc.create(env);
        } catch (Exception ex) {
            System.err.println("Error al crear la Tienda: " + ex.getMessage());
            System.err.println(ex.getStackTrace());
        }
    }

    /*
     <<<>>> Metodos para ACTUALIZAR ENTIDADES<<<>>>  
     */
    /**
     * Método que actualiza a un Usuario
     *
     * @param u Objeto -Usuario- a actualizar
     */
    public static void actualizarUsuario(Usuario u) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        UsuarioJpaController ejc = new UsuarioJpaController(emf);
        try {
            ejc.edit(u);
        } catch (RollbackFailureException ex) {
            System.err.println("Actualizando Usuario " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Actualizando Usuario " + ex.getMessage());
        }
    }

    /**
     * Metodo que actualiza una Cliente
     *
     * @param c Objeto -Cliente- a actualizar
     */
    public static void actualizarCliente(Cliente c) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ClienteJpaController ejc = new ClienteJpaController(emf);
        try {
            ejc.edit(c);
        } catch (RollbackFailureException ex) {
            System.err.println("Actualizando Cliente " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Actualizando Cliente " + ex.getMessage());
        }
    }

    /**
     * Metodo que actualiza una Tienda
     *
     * @param t Objeto -Tienda- a actualizar
     */
    public static void actualizarTienda(Tienda t) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        TiendaJpaController ejc = new TiendaJpaController(emf);
        try {
            ejc.edit(t);
        } catch (RollbackFailureException ex) {
            System.err.println("Actualizando Tienda " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Actualizando Tienda " + ex.getMessage());
        }
    }

    /**
     * Método que actualiza un Producto
     *
     * @param p Objeto -Producto- a actualizar
     */
    public static void actualizarProducto(Producto p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ProductoJpaController ejc = new ProductoJpaController(emf);
        try {
            ejc.edit(p);
        } catch (RollbackFailureException ex) {
            System.err.println("Actualizando Producto " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Actualizando Producto " + ex.getMessage());
        }
    }

    /**
     * Método que actualiza a Pedido
     *
     * @param p Objeto -Pedido- a actualizar
     * @throws Exception
     */
    public static void actualizarPedido(Pedido p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        PedidoJpaController ejc = new PedidoJpaController(emf);
        try {
            ejc.edit(p);
        } catch (RollbackFailureException ex) {
            System.err.println("Error actualizando pedido " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Error actualizando pedido " + ex.getMessage());
        }
    }

    /**
     * Método que actualiza a Pedido
     *
     * @param sp Objeto -Pedido- a actualizar
     *
     */
    public static void actualizarSubPedido(SubPedido sp) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        SubPedidoJpaController ejc = new SubPedidoJpaController(emf);
        try {
            ejc.edit(sp);
        } catch (RollbackFailureException ex) {
            System.err.println("Error actualizando pedido " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Error actualizando pedido " + ex.getMessage());
        }
    }

    /**
     * Método que actualiza un Envio
     *
     * @param env Objeto -Envio- que se desea actualizar
     */
    public static void actualizarEnvio(Envio env) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EnvioJpaController ejc = new EnvioJpaController(emf);
        try {
            ejc.edit(env);
        } catch (RollbackFailureException ex) {
            System.err.println("Error actualizando envio " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Error actualizando envio " + ex.getMessage());
        }
    }

    /*
     <<<>>> Metodos de BUSQUEDA<<<>>>  
     */
    /**
     * Método que busca un Producto en concreto
     *
     * @param id Id del Producto que se busca
     * @return Objeto -Producto- que se busca
     */
    public static Producto buscarProducto(Long id) {
        Producto p = new Producto();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ProductoJpaController ejc = new ProductoJpaController(emf);
        p = ejc.findProducto(id);
        return p;
    }

    /**
     * Método que realiza la busqueda de todos los Productos activos de una
     * Tienda determinada
     *
     * @param id_tienda Id de la Tienda cuyos productos se necesitan
     * @return Lista con todos los Productos de una Tienda
     */
    public static List<Producto> buscarProductosPorTienda(Long id_tienda) {
        List<Producto> productosTienda = new LinkedList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ProductoJpaController ejc = new ProductoJpaController(emf);
        List<Producto> productos = ejc.findProductoEntities();
        for (Producto p : productos) {
            if (p.getTiendaP().getId_usuario().equals(id_tienda) && p.isEstadoProducto()) {
                productosTienda.add(p);
            }
        }
        return productosTienda;
    }

    /**
     * Método que realiza la busqueda de todas las Tiendas cuyo sector/barrio
     * corresponde con el Cliente
     *
     * @param barrio Barrio del Cliente que realiza la peticion/consulta
     * @return Lista con todas las Tiendas del mismo sector que el Cliente
     */
    public static List<Tienda> buscarTiendasBarrioConcreto(String barrio) {
        List<Tienda> tiendasSector = new LinkedList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        TiendaJpaController ejc = new TiendaJpaController(emf);
        List<Tienda> tiendas = ejc.findTiendaEntities();
        for (Tienda t : tiendas) {
            if (t.getBarrio().equals(barrio)) {
                tiendasSector.add(t);
            }
        }
        return tiendasSector;
    }

    /**
     * Método que busca una Tienda en concreto
     *
     * @param id_tienda Id de Usuario de la Tienda que se busca
     * @return Objeto -Tienda- que se busca
     */
    public static Tienda buscarTienda(Long id_tienda) {
        Tienda t = new Tienda();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        TiendaJpaController ejc = new TiendaJpaController(emf);
        t = ejc.findTienda(id_tienda);
        return t;
    }

    /**
     * Método que busca un Cliente
     *
     * @param id_usuario Id de Usuario del Cliente
     * @return Objeto -Cliente- que se busca
     */
    public static Cliente buscarCliente(Long id_usuario) {
        Cliente c = new Cliente();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ClienteJpaController ejc = new ClienteJpaController(emf);
        c = ejc.findCliente(id_usuario);
        return c;
    }

    /**
     * Método que busca un Pedido
     *
     * @param id_pedido Id del Pedido que se busca
     * @return Objeto -Pedido- que se busca
     */
    public static Pedido buscarPedido(Long id_pedido) {
        Pedido p = new Pedido();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        PedidoJpaController ejc = new PedidoJpaController(emf);
        p = ejc.findPedido(id_pedido);
        return p;
    }

    /**
     * Método que busca los pedidos que han sido confirmados/realizados por el
     * Cliente
     *
     * @return Lista con los Pedido confirmados
     */
    public static List<Pedido> buscarPedidosConfirmados() {
        List<Pedido> todosPedidos = new LinkedList<>();
        List<Pedido> pedidosConfirmados = new LinkedList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        PedidoJpaController ejc = new PedidoJpaController(emf);
        todosPedidos = ejc.findPedidoEntities();
        for (Pedido p : todosPedidos) {
            if (p.isConfimacion_cliente()) {
                pedidosConfirmados.add(p);
            }
        }
        return pedidosConfirmados;
    }

    /**
     * Método que devuelve todos los Pedidos realizados a una Tienda
     *
     * @param id_tienda Id de la Tienda
     * @return Lista del tipo SubPedido
     */
    public static List<SubPedido> buscarSubPedidosTienda(String id_tienda) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        Query consultaUsuario = em.createNamedQuery("subPedidosTienda");
        Tienda t = MercaBarrioModelo.buscarTienda(Long.parseLong(id_tienda));
        consultaUsuario.setParameter("id_tienda", t);
        List<SubPedido> resultadoSubPedidos = consultaUsuario.getResultList();
        em.close();
        return resultadoSubPedidos;
    }

    /**
     * Método que devuelve los Pedidos realizados a una Tienda y que su estado
     * sea PENDIENTE
     *
     * @param id_tienda Id de la Tienda
     * @return Lista del tipo SubPedido
     */
    public static List<SubPedido> subPedidosTiendaPendientes(String id_tienda) {
        List<SubPedido> pendientes = new LinkedList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        Query consultaUsuario = em.createNamedQuery("subPedidosTienda");
        Tienda t = MercaBarrioModelo.buscarTienda(Long.parseLong(id_tienda));
        consultaUsuario.setParameter("id_tienda", t);
        List<SubPedido> resultadoSubPedidos = consultaUsuario.getResultList();
        for (SubPedido sp : resultadoSubPedidos) {
            if (sp.getEstado().compareTo(SubPedido.EstadoSubPedido.PENDIENTE) == 0) {
                pendientes.add(sp);
            }
        }
        em.close();
        return pendientes;
    }

    /**
     * Método que devuelve el SubPedido indicando su ID
     *
     * @param id_subPedido Id del SubPedido
     * @return SubPedido que se busca
     */
    public static SubPedido buscarSubPedido(Long id_subPedido) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        SubPedidoJpaController ejc = new SubPedidoJpaController(emf);
        SubPedido sp = ejc.findSubPedido(id_subPedido);
        emf.close();
        return sp;
    }

    /**
     * Método que busca y devuelve un Envio
     *
     * @param id_envio Id del Envio para su busqueda
     * @return Devuleve un Objeto -Envio-
     */
    public static Envio buscarEnvio(Long id_envio) {
        Envio env;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EnvioJpaController ejc = new EnvioJpaController(emf);
        env = ejc.findEnvio(id_envio);
        return env;
    }

    /*
     <<<>>> Metodos de BORRADO<<<>>>  
     */
    /**
     * Método que borra un Pedido
     *
     * @param id_pedido Id del Pedido a borrar
     */
    public static void borrarPedido(Long id_pedido) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        PedidoJpaController ejc = new PedidoJpaController(emf);
        try {
            ejc.destroy(id_pedido);
        } catch (RollbackFailureException ex) {
            System.err.println("Error al borrar Pedido" + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Error al borrar Pedido" + ex.getMessage());
        }
    }

    /**
     * Método que borra un Producto. Lo que hace es que no este disponible
     * cambiando el estado del Producto
     *
     * @param id_producto Id del Producto a borrar
     */
    public static void borrarProducto(Long id_producto) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ProductoJpaController ejc = new ProductoJpaController(emf);
        Producto p = ejc.findProducto(id_producto);
        p.setEstadoProducto(false);
        MercaBarrioModelo.actualizarProducto(p);
    }

    /**
     * Método que borra los articulos del carrito actualizando el Pedido y el
     * Cliente
     *
     * @param id_subPedido Id del SubPedido a borrar
     * @return Devuleve un Objeto -Cliente-
     */
    public static void borrarArticuloCarrito(Long id_subPedido) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        SubPedidoJpaController ejc = new SubPedidoJpaController(emf);
        
        try {
            ejc.destroy(id_subPedido);
        } catch (RollbackFailureException ex) {
            System.err.println("Error al borrar SubPedido" + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Error al borrar SubPedido" + ex.getMessage());
        }
        
    }

    /**
     * Método que devuelve una lista todas las Tiendas registradas en la
     * aplicacion
     *
     * @return Lista con las Tiendas
     */
    public static List<Tienda> buscarTiendas() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        TiendaJpaController ejc = new TiendaJpaController(emf);
        List<Tienda> t = ejc.findTiendaEntities();
        return t;
    }

    /*
     <<<>>> Métodos OTROS<<<>>>  
     */
    /**
     * Método que comprueba si existe un Usuario con el mismo nombre de usuario
     *
     * @param nombreUsuario String con el nombre de usuario para su comprobacion
     * @return Devuelve true o false según si existe o no un usuario con dicho
     * nombre de usuario
     */
    public static boolean existeUsuario(String nombreUsuario) {
        boolean existe = false;
//        Usuario u = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        Query consulta = em.createNamedQuery("existeUsuario");
        consulta.setParameter("nombreUsuario", nombreUsuario);
        List<Usuario> resultado = consulta.getResultList();
        if (resultado.size() > 0) {
            existe = true;
        } else {
            existe = false;
        }
        return existe;
    }

    /**
     * Método que comprueba si existe un Producto con el mismo nombre para una
     * determinada Tienda
     *
     * @param nombreProducto Nombre del Producto que se comprueba
     * @param tienda Tienda que va a registrar ese Producto
     * @return Devuelve true o false según si existe o no un Producto con ese
     * nombre en la Tienda
     */
    public static boolean existeProducto(String nombreProducto, Tienda tienda) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        EntityManager em = emf.createEntityManager();
        Query consulta = em.createNamedQuery("existeProducto");
        consulta.setParameter("nombreProducto", nombreProducto);
        consulta.setParameter("tiendaProducto", tienda);
        List<Producto> resultado = consulta.getResultList();
        if (resultado.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    ////////////////////////////////////////////////////////////// Pendiente de ver su utilidad
    
    public static List buscarProductos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        ProductoJpaController ejc = new ProductoJpaController(emf);
        List<Producto> p = ejc.findProductoEntities();
        return p;
    }
}
