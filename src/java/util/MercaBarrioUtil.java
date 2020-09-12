/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
}
