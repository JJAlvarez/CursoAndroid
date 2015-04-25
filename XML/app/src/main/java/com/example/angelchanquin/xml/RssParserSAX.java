package com.example.angelchanquin.xml;

import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.Element;
import android.sax.StartElementListener;
import android.util.Xml;

import org.xml.sax.Attributes;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelchanquin on 4/25/2015.
 */
public class RssParserSAX {
    private URL rssUrl;
    private Noticia noticiaActual;

    public RssParserSAX(String Url){
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
        final List<Noticia> noticias = new ArrayList<Noticia>();
        RootElement root = new RootElement("rss");
        Element channel = root.getChild("channel");
        Element item = channel.getChild("item");

        item.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                noticiaActual = new Noticia();
            }
        });
        item.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                noticias.add(noticiaActual);
            }
        });
        item.getChild("title").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                noticiaActual.setTitulo(body);
            }
        });
        item.getChild("link").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                noticiaActual.setLink(body);
            }
        });
        item.getChild("descripcion").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                noticiaActual.setDescription(body);
            }
        });
        item.getChild("guid").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                noticiaActual.setGuid(body);
            }
        });
        item.getChild("pubDate").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                noticiaActual.setPubDate(body);
            }
        });
        item.getChild("category").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                noticiaActual.setCategory(body);
            }
        });

        try{
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return noticias;
    }



}
