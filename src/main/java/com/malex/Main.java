package com.malex;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by User on 11.05.2017.
 */
public class Main {

   public final static String MED_SCAPE ="http://www.medscape.com/";

   public static void main(String[] args) throws IOException {

      Document doc = Jsoup.connect(MED_SCAPE).get();

      // div block = specialties
      Element specialties = doc.getElementById("specialties");

      System.out.println(specialties);
   }

}
