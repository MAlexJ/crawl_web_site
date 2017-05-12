package com.malex.site;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author malex
 */
public class HealthCentral {

   private final static String HEALTH_CENTRAL = "http://www.healthcentral.com";

   // FAMILY_HEALTH
   public final static String FAMILY_HEALTH_MENOPAUSE = "/menopause";
   public final static String FAMILY_HEALTH_PROSTATE = "/prostate";

   // HEALTHY LIVING
   public final static String HEALTHY_LIVING_DIET_EXERCISE = "/diet-exercise";
   public final static String HEALTHY_LIVING_DIET_OBESITY = "/obesity";
   public final static String HEALTHY_LIVING_VISION_CARE = "/vision-care";

   // BODY & MIND
   public final static String BODY_MIND_ACID_REFLUX = "/acid-reflux";
   public final static String BODY_MIND_ALLERGY = "/allergy";
   public final static String BODY_MIND_COLD_FLU = "/cold-flu";
   public final static String BODY_MIND_HEART_DISEASE = "/heart-disease";
   public final static String BODY_MIND_HIGH_BLOOD_PRESSURE = "/high-blood-pressure";
   public final static String BODY_MIND_HIGH_SLEEP_DISORDERS = "/sleep-disorders";
   public final static String BODY_MIND_HIGH_SEXUAL_HEALTH = "/sexual-health";
   public final static String BODY_MIND_HIGH_DIABETES = "/diabetes";



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

            String image = element.getElementsByTag("img").attr("src");                            // IMAGE

            String title = element.getElementsByClass("title").text();                             // TITLE

            String text = element.getElementsByClass("body").text();                               // TEXT


            if (StringUtils.isNotBlank(image) && StringUtils.isNotBlank(title) && StringUtils.isNotBlank(text)) {
               // IMAGE
               String imageLink = image;
               imageLink = "http:" + imageLink.substring(0, imageLink.indexOf("?"));
               System.out.print(" IMAGE: " + imageLink);

               // TITLE
               String titleN = title.trim();
               System.out.print("  TITLE: " + titleN);

               // TEXT
               String description = text.trim();
               System.out.println("  TEXT: " + description);
            }
         }
      } catch (Exception ex) {
         // ignore
      }
   }

   /**
    * FAMILY HEALTH: Menopause, Prostate
    */
   public void getCatalog(String subLink) {
      // pass to news: /dailydose
      try {

         Document doc = Jsoup.connect(HEALTH_CENTRAL + subLink).get();

         Element editorSpicks = doc.getElementById("editorspicks");
         Elements div = editorSpicks.getElementsByTag("div");

         // get all links of topics
         Set<String> links = new TreeSet<>();
         for (Element element : div) {
            String link_topic = element.getElementsByTag("a").attr("href");
            if (StringUtils.isNotBlank(link_topic)) {
               links.add(HEALTH_CENTRAL + link_topic);
            }
         }

         if (!links.isEmpty()) {
            for (String linkOfTopic : links) {

               try {
                  Document document = Jsoup.connect(linkOfTopic).get();

                  // IMAGE
                  Elements section = document.getElementsByClass("article--body");
                  String imageElem = section.get(0).getElementsByTag("img").attr("src");

                  if (StringUtils.isNotBlank(imageElem)) {

                     if (imageElem.startsWith("//")) {
                        imageElem = "http:" + imageElem.substring(0, imageElem.indexOf('?'));
                     } else {
                        imageElem = imageElem.substring(0, imageElem.indexOf('?'));
                     }

                     // SELECTOR: article--title-block-secondary
                     Elements titleElem = document.getElementsByClass("article--title-block-secondary");
                     String title = titleElem.get(0).getElementsByTag("h2").text();
                     if(StringUtils.isNotBlank(title)){

                        String text = document.getElementsByClass("article--body").text();
                        if(StringUtils.isNotBlank(text)){

                           // IMAGE
                           System.out.print(" IMAGE : " + imageElem);
                           // TITLE
                           System.out.print("  TITLE:  " + title);
                           // TEXT
                           System.out.println("  TEXT:  " + text);
                        }

                     }
                  }

               } catch (Exception ex) {
                  // ignore
               }
            }
         }
      } catch (IOException e) {
         // ignore
      }
   }
}
