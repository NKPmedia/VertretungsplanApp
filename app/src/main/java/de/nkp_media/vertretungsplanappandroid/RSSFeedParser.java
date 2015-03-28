package de.nkp_media.vertretungsplanappandroid;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return null;
    }

    private ArrayList<Ausfall2> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
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
                    if (name.equals("item")){
                        currentProduct = new Ausfall2();
                        String relType = parser.getAttributeValue(null, "faelltAus");
                        if(relType.equals("yes")) {
                            currentProduct.setEntfall(true);
                        }
                        else
                        {
                            currentProduct.setEntfall(false);
                        }
                    } else if (currentProduct != null){
                        if (name.equals("lehrer")){
                            currentProduct.setLehrer(parser.nextText());
                        } else if (name.equals("fach")){
                            currentProduct.setFach(parser.nextText());
                        } else if (name.equals("stunde")){
                            currentProduct.setStunde(Integer.parseInt(parser.nextText()));
                        }else if (name.equals("datum")){
                            String datum = parser.nextText();
                            currentProduct.setZieldatum(new SimpleDateFormat("yyyy-MM-dd").parse(datum));
                            currentProduct.setZieldatumString(datum);
                        } else if (name.equals("vertretung")){
                            currentProduct.setVertretung(parser.nextText());
                        } else if (name.equals("zielfach")){
                            currentProduct.setZielfach(parser.nextText());
                        } else if (name.equals("raum")){
                            currentProduct.setRaum(parser.nextText());
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
        if(ausfaelle == null)
        {
            System.out.println("Ausfälle null List");
            ausfaelle = new ArrayList();
        }
        return ausfaelle;
    }

    private void showFeed(ArrayList<Ausfall2> ausfaelle)
    {

    }
}

