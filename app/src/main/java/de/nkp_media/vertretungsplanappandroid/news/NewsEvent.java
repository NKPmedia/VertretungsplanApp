package de.nkp_media.vertretungsplanappandroid.news;

import java.util.ArrayList;

import de.nkp_media.vertretungsplanappandroid.News;

/**
 * Created by paul on 10.09.15.
 */
public class NewsEvent {
    public ArrayList<News> newsList;

    public NewsEvent(ArrayList<News> valueListForNewsView) {
        this.newsList = valueListForNewsView;
    }
}
