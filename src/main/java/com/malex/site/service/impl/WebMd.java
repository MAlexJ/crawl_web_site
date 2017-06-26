package com.malex.site.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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


               String image = liElem.getElementsByTag("img").attr("src");
               image = image.substring(0, image.indexOf("?"));                                                    // IMAGE

               String title = liElem.getElementsByTag("a").text();                                                // TITLE

               if (StringUtils.isNotBlank(image) && StringUtils.isNotBlank(title)) {
                  // IMAGE
                  System.out.print("IMAGE: " + image);

                  // TITLE
                  System.out.print("  TITLE:  " + title);

                  // LINK to description
                  String linkToDescription = "http:" + liElem.getElementsByTag("a").attr("href");
                  Document document = Jsoup.connect(linkToDescription).get();
                  Elements elementsByClass = document.getElementsByClass("article-page active-page");
                  String text = elementsByClass.get(0).getElementsByClass("article-page active-page").text();     // TEXT

                  if (StringUtils.isNotBlank(text)) {
                     System.out.println("  TEXT: " + text);
                  }
               }
            }
         }
      } catch (Exception ex) {
         System.err.println(ex.getMessage());
      }
   }

}
