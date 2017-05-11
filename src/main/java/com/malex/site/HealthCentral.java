package com.malex.site;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * @author malex
 */
public class HealthCentral {

   private final static String HEALTH_CENTRAL = "http://www.healthcentral.com";

   public void init() {

      // map: key is name of category, value is link
      Map<String, String> listCategory = new HashMap<>();

      try {
         Document doc = Jsoup.connect(HEALTH_CENTRAL).get();
         // div block = specialties
         Elements elementsByClass = doc.getElementsByClass("Nav-listGroup-list--General");
         Elements div = elementsByClass.get(0).getElementsByTag("div");

         for (Element elementDiv : div) {
            for (Element elementLi : elementDiv.getElementsByTag("li")) {
               if (StringUtils.isNotBlank(elementLi.text()) && StringUtils.isNotBlank(elementLi.getElementsByTag("a").attr("href"))) {
                  listCategory.put(elementLi.text(), elementLi.getElementsByTag("a").attr("href"));
               }
            }
         }

         // fill sub-category
         subCategory(listCategory);

      } catch (Exception ex) {
         System.err.println(ex.getMessage());
      }
   }

   private void subCategory(Map<String, String> listCategory) {
      try {
         for (Map.Entry<String, String> map : listCategory.entrySet()) {

            System.out.println("CATEGORY: " + map.getKey() + "\n");

            Document doc = Jsoup.connect(HEALTH_CENTRAL + map.getValue()).get();


            return;
         }
      } catch (Exception ex) {
         System.err.println(ex.getMessage());
      }
   }

   /**
    * Get news
    */
   public void getNews() {
      try {
         // pass to news: /dailydose
         Document doc = Jsoup.connect(HEALTH_CENTRAL + "/dailydose").get();

         // latest-posts
         Elements lastPosts = doc.getElementsByClass("promo daily-dose--promo");
         for (Element element : lastPosts) {
            if (StringUtils.isNotBlank(element.getElementsByTag("img").attr("src")) && StringUtils.isNotBlank(element.getElementsByClass("title").text())) {
               // IMAGE
               String imageLink = element.getElementsByTag("img").attr("src");
               imageLink = "http:" + imageLink.substring(0, imageLink.indexOf("?"));
               System.out.print(" IMAGE: " + imageLink);

               // TITLE
               String title = element.getElementsByClass("title").text().trim();
               System.out.print("  TITLE: " + title);

               // TEXT
               String description = element.getElementsByClass("body").text().trim();
               System.out.println("  TEXT: " + description);
            }
         }
      } catch (Exception ex) {
         System.err.println(ex.getMessage());
      }
   }
}
