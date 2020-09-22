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
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

/**
 *
 * @author Alberto JMG
 */
@Named(value = "subiMultiplesArchivos")
@RequestScoped
public class SubirMultiplesArchivos {

    private UploadedFiles archivos;

    public UploadedFiles getArchivos() {
        return archivos;
    }

    public void setArchivos(UploadedFiles archivos) {
        this.archivos = archivos;
    }

    public void subirMultiple() {
        InputStream inputStream;
        OutputStream outputStream;
        if (archivos != null) {
            for (UploadedFile f : archivos.getFiles()) {
                FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);

                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                ServletContext servletContext = (ServletContext) externalContext.getContext();
                String rutaGuardadoFoto = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + f.getFileName();
                File outputFile = new File(rutaGuardadoFoto);
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                try {
                    inputStream = f.getInputStream();
                    outputStream = new FileOutputStream(outputFile);
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                } catch (IOException ex) {
                    System.err.println("Error al subir la foto");
                }

            }
        }
    }

}
