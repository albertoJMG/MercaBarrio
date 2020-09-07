/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Alberto JMG
 */
@Named(value = "imagenesSlider")
@RequestScoped
public class ImagenesSlider {

    private List<String> images;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 3; i++) {
            images.add("portada0" + i + ".jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
    
}
