/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entidades.SubPedido;
import entidades.Tienda;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

/**
 *
 * @author Alberto JMG
 */
public class MercaBarrioUtil {

    public static void subirFoto(Part p) {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        String rutaGuardadoFoto = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + p.getSubmittedFileName();
        File outputFile = new File(rutaGuardadoFoto);
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        try {
            inputStream = p.getInputStream();
            outputStream = new FileOutputStream(outputFile);
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException ex) {
            System.err.println("Error al subir la foto");
        }

    }

    public static String codificarSHA256(String mensaje) {
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(mensaje.getBytes(StandardCharsets.UTF_8));

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(hash[i] & 0xff);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ex) {

        }

        return hexString.toString();
    }

    public static double ventasPorMes(List<SubPedido> listaSubPedidos) {
        double ganancias = 0;
        for (SubPedido sp : listaSubPedidos) {
            String opcion = sp.getProducto().getUnidadSuministro();
            if (opcion.equals("gramo") || opcion.equals("mililitro")) {
                ganancias = ganancias + (sp.getCantidad_producto() * (((double) sp.getProducto().getCantidadSuministro() / 1000))
                        * (sp.getProducto().getPrecio()));
            } else {
                ganancias = ganancias + sp.getCantidad_producto() * sp.getProducto().getPrecio();
            }
        }

        return ganancias;
    }
    


}
