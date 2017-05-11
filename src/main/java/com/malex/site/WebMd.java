package com.malex.site;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;

import static java.awt.SystemColor.text;

/**
 * @author malex
 */
public class WebMd {

   private final static String WEB_MD = "http://www.webmd.com";

   /**
    * Get news
    */
   public void getNews() {
      try {

         // pass to news :/news
         Document doc = Jsoup.connect(WEB_MD + "/news").get();

         Elements upper = doc.getElementsByClass("upper");

         for (Element element : upper) {
            for (Element liElem : element.getElementsByTag("li")) {

               // IMAGE
               String imageLink = liElem.getElementsByTag("img").attr("src");
               imageLink = imageLink.substring(0, imageLink.indexOf("?"));
               System.out.print("IMAGE: " + imageLink);

               // TITLE
               System.out.print("  TITLE:  " + liElem.getElementsByTag("a").text());

               // LINK to description
               String linkToDescription = "http:" + liElem.getElementsByTag("a").attr("href");
                 Document document = Jsoup.connect(linkToDescription).get();
               Elements elementsByClass = document.getElementsByClass("article-page active-page");
               String text = elementsByClass.get(0).getElementsByClass("article-page active-page").text();
               System.out.println("  TEXT: " + text);
            }
         }

      } catch (Exception ex) {
         System.err.println(ex.getMessage());
      }
   }

}
