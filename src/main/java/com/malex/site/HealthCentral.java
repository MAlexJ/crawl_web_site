package com.malex.site;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

            // TODO NEW
            String link = HEALTH_CENTRAL + element.getElementsByClass("title").get(0).getElementsByTag("a").attr("href");


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
               System.out.print("  TEXT: " + description);

               // LINK
               System.out.println(" LINK " + link);
            }
         }
      } catch (Exception ex) {
         // ignore
      }
   }

   /**
    * get new by tag
    */
   public void getCatalog(String subLink) {

      try {

         Document doc = Jsoup.connect(HEALTH_CENTRAL + subLink).get();

         Element topic = doc.getElementById("editorspicks");
         Elements div = topic.getElementsByClass("Editor-picks-teaser-container");

         // get all links of topics
         for (Element element : div) {

            String title = element.getElementsByClass("Teaser-title").text();
            String text = element.getElementsByClass("Editor-picks-content").text();
            String link = element.getElementsByClass("Teaser-title").get(0).getElementsByTag("a").attr("href");

            if (!link.isEmpty()) {
               link = HEALTH_CENTRAL + link;

               doc = Jsoup.connect(link).get();
               Elements section = doc.getElementsByClass("article--body");
               String image = section.get(0).getElementsByTag("img").attr("src");

               if (image != null && !image.isEmpty() && title != null && text != null) {

                  if (image.startsWith("//")) {
                     image = "http:" + image.substring(0, image.indexOf('?'));
                  } else {
                     image = image.substring(0, image.indexOf('?'));
                  }
                  System.out.print(" TITLE: " + title);
                  System.out.print(" TEXT: " + text);
                  System.out.print(" IMAGE: " + image);
                  System.out.println(" LINK: " + link);
               }
            }
         }
      } catch (IOException e) {
         // ignore
      }
   }
}
