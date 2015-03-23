/*package de.nkp_media.vertretungsplanappandroid;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklas on 21.03.2015.
 */
/*
public class AusfallParser {

    public ArrayList<Ausfall2> parse(InputStream stream) {

        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(stream);

            return parseXML(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Ausfall2> parseXML(Document doc) {
        List ausfallNodes = doc.selectNodes("//vertretungsplan/ausfall");
        ArrayList<Ausfall2> ausfaelle = new ArrayList<>(ausfallNodes.size());

        for (Object ausfall : ausfallNodes) {
            Node ausfallNode = (Node) ausfall;
            Ausfall2 ausfallObj = new Ausfall2();

            if (ausfallNode.valueOf("@faelltAus").equalsIgnoreCase("yes")) {
                // Stunde fällt aus
                ausfallObj.setEntfall(true);
                ausfallObj.setLehrer(ausfallNode.selectSingleNode("lehrer").getStringValue());
                try {
                    ausfallObj.setZieldatum(new SimpleDateFormat().parse(ausfallNode.selectSingleNode("datum").getStringValue()));
                }
                catch (ParseException ex) {
                    ausfallObj.setZieldatum(null);
                    System.err.println("Failed to parse date from XML feed.");
                    ex.printStackTrace();
                }
                ausfallObj.setFach(ausfallNode.selectSingleNode("fach").getStringValue());
                ausfallObj.setStunde(Integer.parseInt(ausfallNode.selectSingleNode("stunde").getStringValue()));
            }
            else {
                // Stunde fällt nicht aus, sondern wird vertreten
                ausfallObj.setEntfall(false);
                ausfallObj.setLehrer(ausfallNode.selectSingleNode("lehrer").getStringValue());
                ausfallObj.setVertretung(ausfallNode.selectSingleNode("vertretung").getStringValue());
                try {
                    ausfallObj.setZieldatum(new SimpleDateFormat().parse(ausfallNode.selectSingleNode("datum").getStringValue()));
                }
                catch (ParseException ex) {
                    ausfallObj.setZieldatum(null);
                    System.err.println("Failed to parse date from XML feed.");
                    ex.printStackTrace();
                }
                ausfallObj.setFach(ausfallNode.selectSingleNode("fach").getStringValue());
                ausfallObj.setZielfach(ausfallNode.selectSingleNode("zielfach").getStringValue());
                ausfallObj.setRaum(ausfallNode.selectSingleNode("raum").getStringValue());
                ausfallObj.setStunde(Integer.parseInt(ausfallNode.selectSingleNode("stunde").getStringValue()));
            }
        }

        return ausfaelle;
    }

}*/
