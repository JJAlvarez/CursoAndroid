package com.example.angelchanquin.xml;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by angelchanquin on 4/25/2015.
 */
public class RssParserDom {
    private URL rssUrl;

    public RssParserDom(String Url){
        try{
            this.rssUrl = new URL(Url);
        }catch (MalformedURLException e){
            throw new RuntimeException(e);
        }
    }

    private InputStream getInputStream(){
        try{
            return rssUrl.openConnection().getInputStream();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public List<Noticia> parse(){
        // Instanciamos la fabrica para DOM
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        List<Noticia> noticias = new ArrayList<Noticia>();

        try{
            // Creamos un nuevo parser DOM
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Lectura completa del XML
            Document dom = builder.parse(this.getInputStream());

            //Nos posicionamos en el nodo principal del arbol <rss>
            Element root = dom.getDocumentElement();

            // Localizamos todos los elementos <item>
            NodeList items = root.getElementsByTagName("item");

            // Recoremos la lista de noticias

            for(int i=0; i<items.getLength(); i++){

                Noticia noticia = new Noticia();

                // Obtenemos la noticia actual
                Node item = items.item(i);

                //Obtenemos la lista de datos de la noticia actual
                NodeList datosNoticia = item.getChildNodes();

                for (int y=0; y<datosNoticia.getLength(); y++){
                    Node dato = datosNoticia.item(y);
                    String etiqueta = dato.getNodeName();

                    switch (etiqueta){
                        case "title":
                            String texto = obtenerTexto(dato);
                            noticia.setTitulo(texto);
                            break;
                        case "link":
                            noticia.setLink(dato.getFirstChild().getNodeValue());
                            break;
                        case "description":
                            String textoD = obtenerTexto(dato);
                            noticia.setDescription(textoD);
                            break;
                        case "guid":
                            noticia.setGuid(dato.getFirstChild().getNodeValue());
                            break;
                        case "pubDate":
                            noticia.setPubDate(dato.getFirstChild().getNodeValue());
                            break;
                        case "category":
                            noticia.setCategory(dato.getFirstChild().getNodeValue());
                            break;
                    }
                }
                noticias.add(noticia);
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return noticias;
    }

    private String obtenerTexto(Node dato){
        StringBuilder texto = new StringBuilder();
        NodeList fragmentos = dato.getChildNodes();
        for (int k=0; k<fragmentos.getLength(); k++){
            texto.append(fragmentos.item(k).getNodeValue());
        }
        return texto.toString();
    }
}
