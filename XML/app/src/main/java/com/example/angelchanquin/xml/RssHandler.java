package com.example.angelchanquin.xml;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelchanquin on 4/18/2015.
 */
public class RssHandler extends DefaultHandler {

    private List<Noticia> noticias;
    private Noticia noticiaActual;
    private StringBuilder sbTexto;

    public List<Noticia> getNoticias() {
        return noticias;
    }

    @Override
    public void characters(char[] ch, int start, int lenght)
            throws SAXException{
        super.characters(ch, start, lenght);
        if (this.noticiaActual != null) {
            sbTexto.append(ch, start, lenght);
        }
    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException{

        super.endElement(uri, localName, name);

        if (this.noticiaActual != null){
            if(localName.equals("title")){
                noticiaActual.setTitulo(sbTexto.toString());
            }else if(localName.equals("link")){
                noticiaActual.setLink(sbTexto.toString());
            }else if(localName.equals("description")){
                noticiaActual.setDescription(sbTexto.toString());
            }else if(localName.equals("guid")){
                noticiaActual.setGuid(sbTexto.toString());
            }else if(localName.equals("pubDate")){
                noticiaActual.setPubDate(sbTexto.toString());
            }else if(localName.equals("category")){
                noticiaActual.setCategory(sbTexto.toString());
            }else if(localName.equals("item")){
                this.noticias.add(noticiaActual);
            }
            sbTexto.setLength(0);
        }
    }

    @Override
    public void startDocument() throws SAXException{
        super.startDocument();
        noticias = new ArrayList<Noticia>();
        sbTexto = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name
    , Attributes attributes) throws SAXException{

        super.startElement(uri, localName, name, attributes);

        if(localName.equals("item")){
            noticiaActual = new Noticia();
        }
    }


}
