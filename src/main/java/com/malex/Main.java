package com.malex;

import com.malex.site.HealthCentral;
import com.malex.site.MedScape;
import com.malex.site.Ncbi;
import com.malex.site.WebMd;

import java.io.IOException;

/**
 * @author malex
 */
public class Main {

   public static void main(String[] args) throws IOException {

      // WEB SITE: www.medscape.com
      MedScape webSiteMedScape = new MedScape();
      webSiteMedScape.getNews();

      // WEB SITE: www.healthcentral.com
      HealthCentral healthCentral = new HealthCentral();
      healthCentral.getNews();

      // WEB SITE: http://www.webmd.com
      WebMd webMd = new WebMd();
      webMd.getNews();

      // WEB SITE:
      Ncbi ncbi = new Ncbi();
      ncbi.getNews();

   }

}
