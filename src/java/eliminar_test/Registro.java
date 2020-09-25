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
        Cliente d = new Cliente();
        Cliente e = new Cliente();
        Cliente f = new Cliente();
        Cliente g = new Cliente();
        Tienda z = new Tienda();
        Tienda x = new Tienda();
        Tienda y = new Tienda();
        Tienda w = new Tienda();
        Tienda v = new Tienda();
        Tienda u = new Tienda();
        Tienda t = new Tienda();
        Tienda s = new Tienda();
        Tienda r = new Tienda();
        Tienda q = new Tienda();
        Tienda p = new Tienda();
        Tienda o = new Tienda();
//        Producto pa = new Producto();
//        Producto pb = new Producto();
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
        
        d.setDni("4444444H");
        d.setNombre("Ismael");
        d.setApellidos("Marrufo Garcia");
        d.setTipoVIA("CALLE");
        d.setDireccion("ABAD GORDILLO");
        d.setCp("41001");
        d.setBarrio("ALFALFA");
        d.setNumeroVia("1");
        d.setEmail("cliente04@cliente04.com");
        d.setTelefono("666999000");
        d.setNombre_usuario("d");
        d.setPassword("d");
        
        e.setDni("55555555H");
        e.setNombre("Jose Antonio");
        e.setApellidos("Marrufo Jimenez");
        e.setTipoVIA("CALLE");
        e.setDireccion("ABAD GORDILLO");
        e.setCp("41001");
        e.setBarrio("AMATE");
        e.setNumeroVia("1");
        e.setEmail("cliente05@cliente05.com");
        e.setTelefono("666999000");
        e.setNombre_usuario("e");
        e.setPassword("e");
        
        f.setDni("66666666H");
        f.setNombre("Maria del Valle");
        f.setApellidos("Garcia Delgado");
        f.setTipoVIA("CALLE");
        f.setDireccion("ABAD GORDILLO");
        f.setCp("41001");
        f.setBarrio("AMATE");
        f.setNumeroVia("1");
        f.setEmail("cliente06@cliente06.com");
        f.setTelefono("666999000");
        f.setNombre_usuario("f");
        f.setPassword("f");

        z.setCif("H11111111");
        z.setNombre("Pescaderia Manolo");
        z.setResponsable("Manolo García");
        z.setDireccion("ABEDUL");
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
        x.setDireccion("HIPERION");
        x.setCp("41002");
        x.setBarrio("ALFALFA");
        x.setNumeroVia("1");
        x.setTipoVIA("PASEO");
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
        y.setDireccion("TEBA");
        y.setCp("41003");
        y.setBarrio("AEROPUERTO VIEJO");
        y.setNumeroVia("1");
        y.setTipoVIA("CALLE");
        y.setEmail("tienda03@tienda03");
        y.setTelefono("555111111");
        y.setNombre_usuario("y");
        y.setPassword("y");
        y.setDescripcion("Pescados, mariscos y ahumados");
        y.setNombreAvatar("test.jpg");
        y.setAceptada(true);

        w.setCif("H44444444");
        w.setNombre("Pasteleria El Azucar");
        w.setResponsable("Nataliza Sanchez");
        w.setDireccion("TENIENTE RODRIGUEZ CARMONA");
        w.setCp("41004");
        w.setBarrio("AEROPUERTO VIEJO");
        w.setNumeroVia("1");
        w.setTipoVIA("CALLE");
        w.setEmail("tienda04@tienda04");
        w.setTelefono("555111111");
        w.setNombre_usuario("w");
        w.setPassword("w");
        w.setDescripcion("La pasteleria profesional a tu alcance");
        w.setNombreAvatar("test.jpg");
        w.setAceptada(true);

        v.setCif("H55555555");
        v.setNombre("Carniceria Martinez");
        v.setResponsable("Alfredo Martinez");
        v.setDireccion("LUIS HUIDOBRO");
        v.setCp("41005");
        v.setBarrio("AEROPUERTO VIEJO");
        v.setNumeroVia("1");
        v.setTipoVIA("CALLE");
        v.setEmail("tienda05@tienda05");
        v.setTelefono("555111111");
        v.setNombre_usuario("v");
        v.setPassword("v");
        v.setDescripcion("Especialistas en carne de vacuno");
        v.setNombreAvatar("test.jpg");
        v.setAceptada(true);

        u.setCif("H66666666");
        u.setNombre("Panaderia Gemelas");
        u.setResponsable("Laura");
        u.setDireccion("JESUS DE LA REDENCION");
        u.setCp("41006");
        u.setBarrio("ALFALFA");
        u.setNumeroVia("1");
        u.setTipoVIA("PLAZA");
        u.setEmail("tienda06@tienda06");
        u.setTelefono("555111111");
        u.setNombre_usuario("u");
        u.setPassword("u");
        u.setDescripcion("Pan freco todos los dias");
        u.setNombreAvatar("test.jpg");
        u.setAceptada(true);
        
        t.setCif("H77777777");
        t.setNombre("Papeleria PETRUS");
        t.setResponsable("Jose Petrus");
        t.setDireccion("JOAQUIN GUICHOT");
        t.setCp("41001");
        t.setBarrio("AMATE");
        t.setNumeroVia("1");
        t.setTipoVIA("CALLE");
        t.setEmail("tienda07@tienda07");
        t.setTelefono("555111111");
        t.setNombre_usuario("t");
        t.setPassword("t");
        t.setDescripcion("Papeleria y libreria. Expertos en material escolar");
        t.setNombreAvatar("test.jpg");
        t.setAceptada(false);
        
        s.setCif("H88888888");
        s.setNombre("Pescaderia La Gamba");
        s.setResponsable("Jose Morente");
        s.setDireccion("JOSE BERMEJO");
        s.setCp("41001");
        s.setBarrio("AMATE");
        s.setNumeroVia("1");
        s.setTipoVIA("CALLE");
        s.setEmail("tienda08@tienda08");
        s.setTelefono("555111111");
        s.setNombre_usuario("s");
        s.setPassword("s");
        s.setDescripcion("El pescado al mejor precio");
        s.setNombreAvatar("test.jpg");
        s.setAceptada(false);
        
        r.setCif("H99999999");
        r.setNombre("Pasteleria San Bartolomé");
        r.setResponsable("Tony Stark");
        r.setDireccion("MADRE DE CRISTO");
        r.setCp("41001");
        r.setBarrio("AMATE");
        r.setNumeroVia("1");
        r.setTipoVIA("CALLE");
        r.setEmail("tienda09@tienda09");
        r.setTelefono("555111111");
        r.setNombre_usuario("r");
        r.setPassword("r");
        r.setDescripcion("Pasteleria casera");
        r.setNombreAvatar("test.jpg");
        r.setAceptada(false);
        
        q.setCif("H12121212");
        q.setNombre("Ferreteria Perez");
        q.setResponsable("Tomas Perez");
        q.setDireccion("DELEITE");
        q.setCp("41001");
        q.setBarrio("AMATE");
        q.setNumeroVia("1");
        q.setTipoVIA("CALLE");
        q.setEmail("tienda10@tienda10");
        q.setTelefono("555111111");
        q.setNombre_usuario("q");
        q.setPassword("q");
        q.setDescripcion("Expertos en ferretería, herramientas, artículos para el hogar y jardín");
        q.setNombreAvatar("test.jpg");
        q.setAceptada(false);
        
//        p.setCif("H13131313");
//        p.setNombre("Juguetes Sanchez");
//        p.setResponsable("Pedro Sanchez");
//        p.setDireccion("DESTORNILLADOR");
//        p.setCp("41001");
//        p.setBarrio("AEROPUERTO VIEJO");
//        p.setNumeroVia("1");
//        p.setTipoVIA("CALLE");
//        p.setEmail("tienda11@tienda11");
//        p.setTelefono("555111111");
//        p.setNombre_usuario("p");
//        p.setPassword("p");
//        p.setDescripcion("Todos los Juguetes de la tele");
//        p.setNombreAvatar("test.jpg");
//        p.setAceptada(true);
//        
//        o.setCif("H14141414");
//        o.setNombre("Frutería Manolo y Pepe");
//        o.setResponsable("Manolo");
//        o.setDireccion("ESCUELA DE CRISTO");
//        o.setCp("41001");
//        o.setBarrio("ALFAFA");
//        o.setNumeroVia("1");
//        o.setTipoVIA("PLAZA");
//        o.setEmail("tienda12@tienda12");
//        o.setTelefono("555111111");
//        o.setNombre_usuario("o");
//        o.setPassword("o");
//        o.setDescripcion("En Frutería Manolo y Pepe nos esforzamos por ofrecerle un servicio de calidad");
//        o.setNombreAvatar("test.jpg");
//        o.setAceptada(true);

        MercaBarrioModelo.crearAdmin(admin);
        MercaBarrioModelo.crearCliente(a);
        MercaBarrioModelo.crearCliente(b);
        MercaBarrioModelo.crearCliente(c);
        MercaBarrioModelo.crearCliente(d);
        MercaBarrioModelo.crearCliente(e);
        MercaBarrioModelo.crearCliente(f);
        MercaBarrioModelo.crearTienda(z);
        MercaBarrioModelo.crearTienda(x);
        MercaBarrioModelo.crearTienda(y);
        MercaBarrioModelo.crearTienda(w);
        MercaBarrioModelo.crearTienda(v);
        MercaBarrioModelo.crearTienda(u);
        MercaBarrioModelo.crearTienda(t);
        MercaBarrioModelo.crearTienda(s);
        MercaBarrioModelo.crearTienda(r);
        MercaBarrioModelo.crearTienda(q);
        MercaBarrioModelo.crearTienda(p);
        MercaBarrioModelo.crearTienda(o);
        
//        pa.setNombre("Merluza");
//        pa.setBreve_descripcion("Merluza fresca española");
//        pa.setDescripcion("Pescada en caladeros españoles");
//        pa.setAlergenos("Sin alérgenos");
//        pa.setCond_conservacion("Mantener en frio");
//        pa.setControles("Ha pasado los controles determinados por la CE");
//        pa.setPrecio(1.2);
//        pa.setStock(20);
//        pa.setUnidad("Kilo");
//        pa.setTipoIVA(21);
//        pa.setTiendaP(z);
//        pa.setCantidadSuministro(400);
//        pa.setUnidadSuministro("gramos");
//        pa.setNombreFoto("merluza.jpg");
//        pa.setEstadoProducto(true);
//        pa.setTiendaP(z);
        
//        pb.setNombre("Caballa");
//        pb.setBreve_descripcion("Caballa del Mediterraneo");
//        pb.setDescripcion("Caballa fresca pescada en caladeros del Mediterraneo");
//        pb.setAlergenos("Sin alérgenos");
//        pb.setCond_conservacion("Mantener en frio");
//        pb.setControles("Ha pasado los controles determinados por la CE");
//        pb.setPrecio(0.90);
//        pb.setStock(20);
//        pb.setUnidad("Kilo");
//        pb.setTipoIVA(21);
//        pb.setTiendaP(z);
//        pb.setCantidadSuministro(400);
//        pb.setUnidadSuministro("gramos");
//        pb.setNombreFoto("caballa.jpeg");
//        pb.setEstadoProducto(true);
//        pb.setTiendaP(z);
        
        
//        MercaBarrioModelo.crearProducto(pa);
//        MercaBarrioModelo.crearProducto(pb);
//        
//        z.getProductos().add(pa);
//        z.getProductos().add(pb);
//        try {
//            MercaBarrioModelo.actualizarTienda(z);
//        } catch (Exception ex) {
//            System.err.println("Error al actualizar tienda"+ex.getMessage());
//        }
        
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
