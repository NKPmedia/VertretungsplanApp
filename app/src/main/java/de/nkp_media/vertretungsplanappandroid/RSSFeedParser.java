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


    public  ArrayList<Ausfall2> parse(InputStream stream) {

        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = stream;
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            ArrayList<Ausfall2> ausfaelle =  parseXML(parser);
            return ausfaelle;
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private ArrayList<Ausfall2> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        ArrayList<Ausfall2> ausfaelle = null;
        int eventType = parser.getEventType();
        Ausfall2 currentProduct = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    ausfaelle = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("ausfall")){
                        currentProduct = new Ausfall2();
                    } else if (currentProduct != null){
                        if (name.equals("lehrer")){
                            currentProduct.setLehrer(parser.nextText());
                        } else if (name.equals("fach")){
                            currentProduct.setFach(parser.nextText());
                        } else if (name.equals("stunde")){
                            currentProduct.setStunde(Integer.getInteger(parser.nextText()));
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("ausfall") && currentProduct != null){
                        ausfaelle.add(currentProduct);
                    }
            }
            eventType = parser.next();
        }
        this.showFeed(ausfaelle);
        return ausfaelle;
    }

    private void showFeed(ArrayList<Ausfall2> ausfaelle)
    {

    }
}

