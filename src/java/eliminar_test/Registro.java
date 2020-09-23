/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eliminar_test;

import entidades.Administrador;
import entidades.Cliente;
import entidades.Pedido;
import entidades.Producto;
import entidades.SubPedido;
import entidades.Tienda;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.MercaBarrioModelo;
import util.MercaBarrioUtil;

/**
 *
 * @author Alberto JMG
 */
@WebServlet(name = "Registro", urlPatterns = {"/Registro"})
public class Registro extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Cliente a = new Cliente();
        Cliente b = new Cliente();
        Cliente c = new Cliente();
        Tienda z = new Tienda();
        Tienda x = new Tienda();
        Tienda y = new Tienda();
        Tienda w = new Tienda();
        Tienda v = new Tienda();
        Tienda u = new Tienda();
        Producto pa = new Producto();
        Producto pb = new Producto();
//        SubPedido sa = new SubPedido();
//        SubPedido sb = new SubPedido();
//        Pedido pda = new Pedido();
//        Pedido pdb = new Pedido();
        Administrador admin = new Administrador();
        
        admin.setNombre_usuario("admin");
        admin.setPassword("admin");
        admin.setNombre("Alberto J.");
        admin.setApellidos("MG");

        a.setDni("11111111H");
        a.setNombre("Alberto Jose");
        a.setApellidos("Marrufo Garcia");
        a.setTipoVIA("CALLE");
        a.setDireccion("ABAD GORDILLO");
        a.setCp("41001");
        a.setBarrio("AEROPUERTO VIEJO");
        a.setNumeroVia("1");
        a.setEmail("cliente01@cliente01.com");
        a.setTelefono("666999000");
        a.setNombre_usuario("a");
        a.setPassword("a");

        b.setDni("22222222H");
        b.setNombre("Cristina");
        b.setApellidos("Herrojo Garcia");
        b.setTipoVIA("CALLE");
        b.setDireccion("ABAD GORDILLO");
        b.setCp("51001");
        b.setBarrio("ALFALFA");
        b.setNumeroVia("2");
        b.setEmail("cliente02@cliente02.com");
        b.setTelefono("666999000");
        b.setNombre_usuario("b");
        b.setPassword("b");

        c.setDni("3333333H");
        c.setNombre("Rosa");
        c.setApellidos("Benito Ariza");
        c.setTipoVIA("CALLE");
        c.setDireccion("ABAD GORDILLO");
        c.setCp("61001");
        c.setBarrio("AEROPUERTO VIEJO");
        c.setNumeroVia("3");
        c.setEmail("cliente03@cliente03.com");
        c.setTelefono("666999000");
        c.setNombre_usuario("c");
        c.setPassword("c");

        z.setCif("H11111111");
        z.setNombre("Pescaderia Manolo");
        z.setResponsable("Manolo");
        z.setDireccion("Calle Una cualquiera");
        z.setCp("41001");
        z.setBarrio("AEROPUERTO VIEJO");
        z.setNumeroVia("1");
        z.setTipoVIA("CALLE");
        z.setEmail("tienda01@tienda01");
        z.setTelefono("555111111");
        z.setNombre_usuario("z");
        z.setPassword("z");
        z.setDescripcion("Pescaderia fundada en 1920. Venta de pescado y mariscos");
        z.setNombreAvatar("test.jpg");
        z.setAceptada(true);
        
        x.setCif("H22222222");
        x.setNombre("Caniceria H. Jurado");
        x.setResponsable("José Jurado");
        x.setDireccion("Calle Otra cualquiera");
        x.setCp("41002");
        x.setBarrio("ALFALFA");
        x.setEmail("tienda02@tienda02");
        x.setTelefono("555111111");
        x.setNombre_usuario("x");
        x.setPassword("x");
        x.setDescripcion("Productos cárnicos naturales");
        x.setNombreAvatar("test.jpg");
        x.setAceptada(true);
        
        y.setCif("H33333333");
        y.setNombre("Pescaderia Gutierrez");
        y.setResponsable("Guty");
        y.setDireccion("Calle Continuamos con cualquiera");
        y.setCp("41003");
        y.setBarrio("AEROPUERTO VIEJO");
        y.setEmail("tienda03@tienda03");
        y.setTelefono("555111111");
        y.setNombre_usuario("y");
        y.setPassword("y");
        y.setDescripcion("Pescados, mariscos y ahumados de calidad");
        y.setNombreAvatar("test.jpg");
        y.setAceptada(false);

        w.setCif("H44444444");
        w.setNombre("Pasteleria El Azucar");
        w.setResponsable("Nataliza Sanchez");
        w.setDireccion("Calle Ya no se que poner");
        w.setCp("41004");
        w.setBarrio("AEROPUERTO VIEJO");
        w.setEmail("tienda04@tienda04");
        w.setTelefono("555111111");
        w.setNombre_usuario("w");
        w.setPassword("w");
        w.setDescripcion("La pasteleria profesional a tu alcance");
        w.setNombreAvatar("test.jpg");
        y.setAceptada(false);

        v.setCif("H55555555");
        v.setNombre("Carniceria Martinez");
        v.setResponsable("Martin Martinez");
        v.setDireccion("Calle Sigo sin ideas");
        v.setCp("41005");
        v.setBarrio("AEROPUERTO VIEJO");
        v.setEmail("tienda05@tienda05");
        v.setTelefono("555111111");
        v.setNombre_usuario("v");
        v.setPassword("v");
        v.setDescripcion("Especialistas en carne de vacuno");
        v.setNombreAvatar("test.jpg");
        y.setAceptada(false);

        u.setCif("H66666666");
        u.setNombre("Panaderia Gemelas");
        u.setResponsable("Laura");
        u.setDireccion("Calle Menos mal que es la ultima");
        u.setCp("41006");
        u.setBarrio("ALFALFA");
        u.setEmail("tienda06@tienda06");
        u.setTelefono("555111111");
        u.setNombre_usuario("u");
        u.setPassword("u");
        u.setDescripcion("Pan freco todos los dias. Ultramarinos");
        u.setNombreAvatar("test.jpg");
        y.setAceptada(false);

        MercaBarrioModelo.crearAdmin(admin);
        MercaBarrioModelo.crearCliente(a);
        MercaBarrioModelo.crearCliente(b);
        MercaBarrioModelo.crearCliente(c);
        MercaBarrioModelo.crearTienda(z);
        MercaBarrioModelo.crearTienda(x);
        MercaBarrioModelo.crearTienda(y);
        MercaBarrioModelo.crearTienda(w);
        MercaBarrioModelo.crearTienda(v);
        MercaBarrioModelo.crearTienda(u);
        
        pa.setNombre("Merluza");
        pa.setBreve_descripcion("Merluza fresca española");
        pa.setDescripcion("Pescada en caladeros españoles");
        pa.setAlergenos("Sin alérgenos");
        pa.setCond_conservacion("Mantener en frio");
        pa.setControles("Ha pasado los controles determinados por la CE");
        pa.setPrecio(1.2);
        pa.setStock(20);
        pa.setUnidad("Kilo");
        pa.setTipoIVA(21);
        pa.setTiendaP(z);
        pa.setCantidadSuministro(400);
        pa.setUnidadSuministro("gramos");
        pa.setNombreFoto("merluza.jpg");
        pa.setEstadoProducto(true);
//        pa.setTiendaP(z);
        
        pb.setNombre("Caballa");
        pb.setBreve_descripcion("Caballa del Mediterraneo");
        pb.setDescripcion("Caballa fresca pescada en caladeros del Mediterraneo");
        pb.setAlergenos("Sin alérgenos");
        pb.setCond_conservacion("Mantener en frio");
        pb.setControles("Ha pasado los controles determinados por la CE");
        pb.setPrecio(0.90);
        pb.setStock(20);
        pb.setUnidad("Kilo");
        pb.setTipoIVA(21);
        pb.setTiendaP(z);
        pb.setCantidadSuministro(400);
        pb.setUnidadSuministro("gramos");
        pb.setNombreFoto("caballa.jpeg");
        pb.setEstadoProducto(true);
//        pb.setTiendaP(z);
        
        
        MercaBarrioModelo.crearProducto(pa);
        MercaBarrioModelo.crearProducto(pb);
        
        z.getProductos().add(pa);
        z.getProductos().add(pb);
        try {
            MercaBarrioModelo.actualizarTienda(z);
        } catch (Exception ex) {
            System.err.println("Error al actualizar tienda"+ex.getMessage());
        }
        
//        sa.setCantidad_producto(1);
//        sa.setProducto(pa);
        
//        MercaBarrioModelo.crearSubPedido(sa);
//        MercaBarrioModelo.crearPedido(pda);
        
//        pda.getSubPedido().add(sa);
//        Cliente c = MercaBarrioModelo.buscarCliente(1L);
//        a.getPedidos().add(pda);
//        pda.setCliente(a);
        try {
//            MercaBarrioModelo.actualizarPedido(pda);
//            MercaBarrioModelo.actualizarCliente(a);
        } catch (Exception ex) {
            System.err.println("Error al actualizar pedido"+ex.getMessage());
        }
        
        
        
        
        response.sendRedirect("faces/index.xhtml");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
