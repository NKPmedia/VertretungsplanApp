package de.nkp_media.vertretungsplanappandroid.Sync;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.nkp_media.vertretungsplanappandroid.Ausfall2;
import de.nkp_media.vertretungsplanappandroid.News;

/**
 * Created by paul on 20.03.15.
 */


public class RSSFeedParser {


    private static final String TAG = "RSSFeedParser";

    public ArrayList<ArrayList> parse(InputStream stream) {

        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);

            return parseXML(parser);
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return null;
    }

    private ArrayList<ArrayList> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
        ArrayList<Ausfall2> ausfaelle = null;
        ArrayList<News> news = null;
        int eventType = parser.getEventType();
        Ausfall2 currentProduct = null;
        News currentNewsProduct = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    ausfaelle = new ArrayList<Ausfall2>();
                    news = new ArrayList<News>();
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
                    } else if (name.equals("news")){
                        currentNewsProduct = new News();
                    }
                    else if (currentProduct != null || currentNewsProduct != null){
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
                        }else if (name.equals("title")) {
                            currentNewsProduct.setTitle(parser.nextText());
                        }else if (name.equals("pubDateNews")){
                            String datum = parser.nextText();
                            currentNewsProduct.setZieldatum(new SimpleDateFormat("yyyy-MM-dd").parse(datum));
                            currentNewsProduct.setDate(datum);
                        }else if (name.equals("description")){
                            currentNewsProduct.setContent(parser.nextText());
                        }

                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("item") && currentProduct != null){
                        ausfaelle.add(currentProduct);
                    }else if (name.equalsIgnoreCase("news") && currentNewsProduct != null){
                        news.add(currentNewsProduct);
                    }
            }
            eventType = parser.next();
        }
        if(ausfaelle == null)
        {
            System.out.println("Ausfälle null List");
            ausfaelle = new ArrayList<>();
        }
        Log.d(TAG,"Produced a PlanList with " +ausfaelle.size()+" items");
        Log.d(TAG,"Produced a NewsList with " +news.size()+" items");
        ArrayList back = new ArrayList<ArrayList>();
        back.add(ausfaelle);
        back.add(news);
        return back;
    }
}

