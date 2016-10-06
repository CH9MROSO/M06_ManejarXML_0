/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejarxml;

import java.io.File;
import java.util.List;
import javalibros.Libros;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author ADRI
 */
public class ManejarJAXB {


    
    private Libros libros;

    public Libros getLibros() {
        return libros;
    }

    public void setLibros(Libros libros) {
        this.libros = libros;
    }
    

    public int abrir_XML_JAXB(File fichero) throws JAXBException {
        JAXBContext contexto;
        try {
            //Crea una instancia JAXB
            contexto = JAXBContext.newInstance(Libros.class);
            //Crea un objeto Unmarsheller.
            Unmarshaller u = contexto.createUnmarshaller();
            //Deserializa (unmarshal) el fichero
            libros = (Libros) u.unmarshal(fichero);

            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }

    }

    public String recorrerJAXByMostrar() {

        String cadena_resultado = "";

        //Crea una lista con objetos de tipo libro.
        List<Libros.Libro> lLibros = this.libros.getLibro();

        //Recorre la lista para sacar los valores
        for (int i = 0; i < lLibros.size(); i++) {
            cadena_resultado = cadena_resultado + "\n " + "Publicado en: " + lLibros.get(i).getPublicadoEn();
            cadena_resultado = cadena_resultado + "\n " + "El Título es: " + lLibros.get(i).getTitulo();
            cadena_resultado = cadena_resultado + "\n " + "Autor: " + lLibros.get(i).getAutor();
            cadena_resultado = cadena_resultado + "\n ------------------------------------------";
        }

        return cadena_resultado;
    }
}
