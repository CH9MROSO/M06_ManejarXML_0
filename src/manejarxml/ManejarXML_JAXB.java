/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejarxml;

import java.io.File;
import javax.xml.bind.JAXBException;

/**
 *
 * @author ADRI
 */
public class ManejarXML_JAXB {
        public static void main(String[] args) throws JAXBException {
        
        ManejarJAXB manejadorJAXB = new ManejarJAXB();
        manejadorJAXB.abrir_XML_JAXB(new File("libreria.xml"));
        String resultado = manejadorJAXB.recorrerJAXByMostrar();
        System.out.println(resultado);
                
    }
}
