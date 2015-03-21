package de.nkp_media.vertretungsplanappandroid;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by paul on 20.03.15.
 */


public class RSSFeedParser {


    public Object parse(InputStream stream) {

        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = stream;
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            parseXML(parser);
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        ArrayList<Ausfall> ausfaelle = null;
        int eventType = parser.getEventType();
        Ausfall currentProduct = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    ausfaelle = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name == "item"){
                        currentProduct = new Ausfall();
                    } else if (currentProduct != null){
                        if (name == "titel"){
                            currentProduct.titel = parser.nextText();
                        } else if (name == "description"){
                            currentProduct.description = parser.nextText();
                        } else if (name == "pubDate"){
                            currentProduct.pubDate= parser.nextText();
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("item") && currentProduct != null){
                        ausfaelle.add(currentProduct);
                    }
            }
            eventType = parser.next();
        }
        this.showFeed(ausfaelle);
    }

    private void showFeed(ArrayList<Ausfall> ausfaelle)
    {

    }
}

