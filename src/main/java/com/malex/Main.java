package com.malex;

import com.malex.site.model.ResultCrawling;
import com.malex.site.service.WebTopic;
import com.malex.site.service.impl.Health_Cleveland_Clinic;

import java.io.IOException;
import java.util.List;

/**
 * @author malex
 */
public class Main {

   public static void main(String[] args) throws IOException {

      // WEB SITE: www.medscape.com
//      MedScape webSiteMedScape = new MedScape();
//      webSiteMedScape.getDrugsDiseases();
//      webSiteMedScape.getNews();
//
//      // WEB SITE: www.healthcentral.com
//      HealthCentral healthCentral = new HealthCentral();
//      healthCentral.getCatalog(HealthCentral.FAMILY_HEALTH_MENOPAUSE);
//      healthCentral.getCatalog(HealthCentral.FAMILY_HEALTH_PROSTATE);
//
//      healthCentral.getCatalog(HealthCentral.HEALTHY_LIVING_DIET_EXERCISE);
//      healthCentral.getCatalog(HealthCentral.HEALTHY_LIVING_DIET_OBESITY);
//      healthCentral.getCatalog(HealthCentral.HEALTHY_LIVING_VISION_CARE);
//
//      healthCentral.getCatalog(HealthCentral.BODY_MIND_ACID_REFLUX);
//      healthCentral.getCatalog(HealthCentral.BODY_MIND_ALLERGY);
//      healthCentral.getCatalog(HealthCentral.BODY_MIND_COLD_FLU);
//      healthCentral.getCatalog(HealthCentral.BODY_MIND_HEART_DISEASE);
//      healthCentral.getCatalog(HealthCentral.BODY_MIND_HIGH_BLOOD_PRESSURE);
//      healthCentral.getCatalog(HealthCentral.BODY_MIND_HIGH_SLEEP_DISORDERS);
//      healthCentral.getCatalog(HealthCentral.BODY_MIND_HIGH_SEXUAL_HEALTH);
//      healthCentral.getCatalog(HealthCentral.BODY_MIND_HIGH_DIABETES);
//
//      healthCentral.getNews();
//
      // WEB SITE: http://www.webmd.com
//      WebMd webMd = new WebMd();
//      webMd.getNews();
//
      // WEB SITE: https://www.ncbi.nlm.nih.gov/
//      Ncbi ncbi = new Ncbi();
//      ncbi.getNews();


      WebTopic health = new Health_Cleveland_Clinic();
      List<ResultCrawling> crawling = health.crawling();

      for(ResultCrawling res: crawling){
         System.out.println(res.getTitle());
      }



   }
}