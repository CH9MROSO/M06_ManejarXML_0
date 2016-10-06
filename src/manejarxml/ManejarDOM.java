/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejarxml;




import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author ADRI
 */
public class ManejarDOM {
    private Document doc;
    
    public int generar_DOMdeXML(File fichero)
    {
        try {
            doc=null;
            
            //Se crea un objeto DocumentBuilderFactory
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            //Se indica que el modelo DOM debe ignorar los comentarios que tenga el XML
            factory.setIgnoringComments(true);
            //Se indica que el modelo DOM debe ignorar los espacios en blanco que tenga el XML
            factory.setIgnoringElementContentWhitespace(true);
            //Crea árbol DOM
            DocumentBuilder builder=factory.newDocumentBuilder();
            //Interpreta (parsea) el documento XML (file) y genera el DOM equivalente
            doc=builder.parse(fichero);
            return 0;
        }catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public String[] procesarLibro(Node n){
        
        String datos[]= new String[3];
        Node ntemp=null;
        int contador=1;
        
        //Obtiene el valor del primer atributo del nodo (publicado_en)
        datos[0]=n.getAttributes().item(0).getNodeValue();
        
        //Obtiene los hijos del Libro (titulo y autor
        NodeList nodos=n.getChildNodes();
        for (int i=0; i<nodos.getLength(); i++){
            ntemp = nodos.item(i);
            if(ntemp.getNodeType()==Node.ELEMENT_NODE){
                //Obtenemos el nodo texto hijo de los elementos título y autor recorridos
                datos[contador]=ntemp.getChildNodes().item(0).getNodeValue();
                contador++;
                        
            }
        }
        
        return datos;
    }
    
    public int annadirLibroDOM(String titulo, String autor, String anno) {
        try {
        //Se crea un nodo tipo Element con nombre 'titulo' (<Titulo>
        Node ntitulo = doc.createElement("Titulo");
        //Se crea un nodo tipo texto con el título del libro
        Node ntitulo_text = doc.createTextNode(titulo);
        //Se añade el nodo de texto con el título como hijo del elemento Titulo
        ntitulo.appendChild(ntitulo_text);
        //Se crea un nodo tipo Element con nombre 'autor' (<Autor>
        Node nautor = doc.createElement("Autor");
        //Se crea un nodo tipo texto con el autor del libro
        Node nautor_text = doc.createTextNode(autor);
        //Se añade el nodo de texto con el autor como hijo del elemento Autor
        nautor.appendChild(nautor_text);
        
        
        //Se crea un nodo de tipo elemento (<libro>)
        Node nlibro= doc.createElement("Libro");
        //Al nuevo nodo libro se le añade un atributo publicado_en
        ((Element)nlibro).setAttribute("publicado_en", anno);
        
        //Se añade a libro el nodo titulo y autor creados
        nlibro.appendChild(ntitulo);
        nlibro.appendChild(nautor);
        
        //Finalmente, se obtiene el primer nodo del documento y a él se le añade
        //como hijo el nodo libro (que ya posee toda la estructura de elementos y atributos creados)
        
        Node raiz = doc.getChildNodes().item(0);
        raiz.appendChild(nlibro);
        
        return 0;
        
        }catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public int guardarDOMcomoFile(File archivo_xml) {
        try {
            //Especifica el formato de salida
            OutputFormat format = new OutputFormat(doc);
            //Especifica que la salida está indentada
            format.setIndenting(true);
            //Escribe el contenido en el FILE
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(archivo_xml), format);
            serializer.serialize(doc);
            return 0;
        }catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public int EjecutarXPath(File archivoXML, String expresionXPath) {
        
        try {
            String salida="";
            //Crea el objeto XPathFactory
            XPath xpath = XPathFactory.newInstance().newXPath();
            //Crea un objeto DocumentBuilderFactory para el DOM (JAXP)
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document XMLDoc = factory.newDocumentBuilder().parse(archivoXML);
            //Crea un XPathExpression con la consulta deseada
            XPathExpression exp = xpath.compile(expresionXPath);
            
            //Ejecuta la consulta indicando que se ejecute sobre el DOM y que devolverá
            //el resultado como una lista de nodos.
            
            Object result = exp.evaluate(XMLDoc, XPathConstants.NODESET);
            NodeList nodeList = (NodeList) result;
            
            //Ahora recorre la lista para sacar los resultados
            for (int i = 0; i < nodeList.getLength(); i++) {
                salida = salida + "\n" + nodeList.item(i).getChildNodes().item(0).getNodeValue();
                               
            }
            System.out.println(salida);
            return 0;
        }catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            return -1;
        }
    }
    
}
