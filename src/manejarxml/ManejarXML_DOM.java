/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejarxml;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import manejarxml.ManejarDOM.*;
/**
 *
 * @author ADRI
 */
public class ManejarXML_DOM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Creamos un manejador DOM generado a partir de un documento XML
        File archivoXML = new File("libreria.xml");
        
        ManejarDOM manejadorDOM = new ManejarDOM();
        int docGenerado = manejadorDOM.generar_DOMdeXML(archivoXML);
        if (docGenerado==0){
            System.out.println("Documento DOM generado con exito");
        }
        
        //Procedemos a añadir un Libro, una vez definido el título, el autor y el año de publicación
        try{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));     
        System.out.println("Se va a proceder a añadir un libro.");
                System.out.println("----------------------------------------");
        System.out.println("Introduce el Título del libro:");
        String titulo = br.readLine();
        System.out.println("Introduce el Autor del libro:");
        String autor = br.readLine();
        System.out.println("Introduce el año de publicación del libro:");
        String anno = br.readLine();
                       
        int libroAnnadido = manejadorDOM.annadirLibroDOM(titulo, autor, anno);
        if (libroAnnadido==0){
            System.out.println("Libro añadido con exito");
        }
        
        }catch (Exception e){
            e.printStackTrace();
        }
        
        //Guardamos la estructura DOM modificada en el archivo XML "salida.xml"
        int xmlGuardado = manejadorDOM.guardarDOMcomoFile(new File("salida.xml"));
        if (xmlGuardado==0){
            System.out.println("XML guardado con exito");
        };
        
        //Realizamos una consulta mediante una expresión XPath sobre un archivo determinado XML
        
        String expresionXPath= "//Libro/*/Autor";
        int consultaCorrecta = manejadorDOM.EjecutarXPath(archivoXML, expresionXPath);
        
        if (consultaCorrecta==0){
            System.out.println("consulta realizada con exito");
        }
        
    }
    
}
