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
public class MedScape {

   private final static String MED_SCAPE_URL = "http://www.medscape.com";

   public void init() {

      // map: key is name of category, value is link
      Map<String, String> listCategory = new HashMap<>();

      try {
         Document doc = Jsoup.connect(MED_SCAPE_URL).get();

         // div block = specialties
         Element specialties = doc.getElementById("specialties");
         Elements bucketContent = specialties.getElementsByClass("bucketContent");
         Elements liList = bucketContent.select("li");

         // find all categories and links
         for (Element element : liList) {
            try {
               listCategory.put(element.text(), element.getElementsByTag("a").get(0).attr("href"));
            } catch (Exception ex) {
               System.err.println(ex.getMessage());
            }
         }

         // find all sub-category
         subCategory(listCategory);

      } catch (Exception ex) {
         System.err.println(ex.getMessage());
      }
   }

   private void subCategory(Map<String, String> listCategory) {
      try {

         for (Map.Entry<String, String> map : listCategory.entrySet()) {

            System.out.println("CATEGORY: " + map.getKey() + "\n");

            Document doc = Jsoup.connect(MED_SCAPE_URL + map.getValue()).get();

            // specialtyNews
            Element specialtyNews = doc.getElementById("specialtyNews");
            // bucketL
            Elements bucketL = specialtyNews.getElementsByClass("bucketL");

            Elements liNews = bucketL.select("li");

            for (Element element : liNews) {
               if (StringUtils.isNotBlank(element.getElementsByClass("title").text()))
                  System.out.println("TITLE: " + element.getElementsByClass("title").text() + " TEXT: " + element.getElementsByClass("teaser").text() + "   IMAGE: " + element.getElementsByTag("span").attr("data-src"));
            }

            System.out.println();
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
         Document doc = Jsoup.connect(MED_SCAPE_URL).get();

         //topNews
         Element topNews = doc.getElementById("topNews");

         // first topic
         Elements col2Feature = topNews.getElementsByClass("col2Feature");
         Elements elemLi = col2Feature.get(0).getElementsByTag("li");
         if (StringUtils.isNotBlank(elemLi.get(0).getElementsByTag("img").attr("src")) && StringUtils.isNotBlank(elemLi.get(0).getElementsByClass("title").text())) {
            // first news
            System.out.print("  IMAGE: " + elemLi.get(0).getElementsByTag("img").attr("src"));
            System.out.print("  TITLE: " + elemLi.get(0).getElementsByClass("title").text().trim());
            System.out.println("  DESCRIPTION: " + elemLi.get(0).getElementsByClass("teaser").text());
         }

         // next topics
         Elements col2 = topNews.getElementsByClass("col2");
         for (Element element : col2) {
            Elements liNews = element.getElementsByTag("li");
            for (Element elemenLi : liNews) {
               if (StringUtils.isNotBlank(elemenLi.getElementsByTag("img").attr("src")) && StringUtils.isNotBlank(elemenLi.getElementsByClass("title").text())) {
                  // next news
                  System.out.print("  IMAGE: " + elemenLi.getElementsByTag("img").attr("src"));
                  System.out.print("  TITLE: " + elemenLi.getElementsByClass("title").text());
                  System.out.println("  DESCRIPTION: " + elemenLi.getElementsByClass("teaser").text());
               }
            }
         }
      } catch (Exception ex) {
         System.out.println(ex.getMessage());
      }
   }
}