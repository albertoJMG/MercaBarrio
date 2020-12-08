/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 *
 * @author Alberto JMG
 */
@FacesValidator("validarImagen")
public class ValidarImagen implements Validator {

    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        System.out.println("--------------------------------------" + arg2);
        if (arg2 != null) {
            Part imagen = (Part) arg2;
            String mensaje = "Hola";
            boolean esImagen = true;
            if (imagen.getSubmittedFileName().endsWith("jpg") || imagen.getSubmittedFileName().endsWith("png") || imagen.getSubmittedFileName().endsWith("jpeg")) {
                esImagen = true;

                if (imagen.getSize() <= 1000000) {
                    esImagen = true;
                } else {
                    esImagen = false;
                    mensaje = "El tamaño de la imagen es superior a 1MB";
                }

            } else {
                esImagen = false;
                mensaje = "Solo son validos los archivos JPG, PNG, JPEG";
            }

            System.out.println(" - - - - - - " + esImagen);
            if (!esImagen) {
                FacesMessage msg = new FacesMessage(mensaje);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }

    }

}
