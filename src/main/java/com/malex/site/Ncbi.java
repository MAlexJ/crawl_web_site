package com.malex.site;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author malex
 */
public class Ncbi {

   //https://www.ncbi.nlm.nih.gov/
   private final static String NCBI_URL = "https://www.ncbi.nlm.nih.gov";

   /**
    * Get news
    */
   public void getNews() {
      try {

         // pass to news : /news
         Document doc = Jsoup.connect(NCBI_URL + "/news").get();
         // SELECTOR:  'news-items'
         Element elementById = doc.getElementById("news-items");
         // SELECTOR: 'iconblock clearfix'
         Elements elementsByClass = elementById.getElementsByClass("iconblock clearfix");
         // find element
         for (Element element : elementsByClass) {

            String image = NCBI_URL + element.getElementsByTag("img").attr("src");           // IMAGE

            String title = element.getElementsByTag("h3").text();                            // TITLE

            if (StringUtils.isNotBlank(image) && StringUtils.isNotBlank(title)) {
               // IMAGE
               System.out.print("IMAGE: " + image);
               // TITLE
               System.out.print(" TITLE: " + title);

               //TEXT
               String linkText = NCBI_URL + element.getElementsByTag("a").attr("href");
               Document document = Jsoup.connect(linkText).get();
               String text = document.getElementsByClass("content").get(1).text();           // TEXT

               if (StringUtils.isNotBlank(text)) {
                  // TEXT
                  System.out.print(" TEXT: " + text);
               }
            }

            System.out.println();
         }

      } catch (Exception ex) {
         System.out.println(ex.getMessage());
      }
   }
}
