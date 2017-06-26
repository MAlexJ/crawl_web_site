package com.malex.site.service.impl;

import com.malex.site.model.ResultCrawling;
import com.malex.site.service.WebTopic;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author malex
 */
public class Health_Cleveland_Clinic extends WebTopic {


	/**
	 * Category news
	 */
	private static final String CHILDREN_S_HEALTH = "Children’s Health";
	private static final String FAMILY_MEDICINE = "Family Medicine";
	private static final String GENOMIC_MEDICINE = "Genomic Medicine";
	private static final String MENS_HEALTH = "Men’s Health";
	private static final String NEWS_INNOVATION = "News & Innovation";
	private static final String PREGNANCY_CHILDBIRTH = "Pregnancy & Childbirth";
	private static final String SENIOR_HEALTH = "Senior Health";
	private static final String SEX_RELATIONSHIPS = "Sex & Relationships";
	private static final String SLEEP = "Sleep";
	private static final String WELLNESS = "Wellness";
	private static final String WOMENS_HEALTH = "Women’s Health";

	/**
	 * Topics
	 */
	private static Map<String, String> topics = new HashMap<>();

	static {
		topics.put(CHILDREN_S_HEALTH, "childrens-health/");
//		topics.put(FAMILY_MEDICINE, "family-medicine");
//		topics.put(GENOMIC_MEDICINE, "geonomic-medicine");
//		topics.put(MENS_HEALTH, "mens-health");
//		topics.put(NEWS_INNOVATION, "news-and-innovation");
//		topics.put(PREGNANCY_CHILDBIRTH, "pregnancy-childbirth");
//		topics.put(SENIOR_HEALTH, "senior-health");
//		topics.put(SEX_RELATIONSHIPS, "sex-relationships");
//		topics.put(SLEEP, "sleep");
//		topics.put(WELLNESS, "wellness");
//		topics.put(WOMENS_HEALTH, "womens-health");
	}

	/**
	 * The root path to topics
	 */
	private static final String PATH_TO_TOPICS = "https://health.clevelandclinic.org/topics/";

	/**
	 * ROOT_ELEMENT
	 */
	private static final String TOPIC_ELEMENT = "article";


	/**
	 * @return the list of ResultCrawling entity
	 */
	@Override
	public List<ResultCrawling> crawling() {

		List<ResultCrawling> result = new ArrayList<>();

		for (Map.Entry<String, String> entrie : topics.entrySet()) {

			try {

				String pathToTopic = PATH_TO_TOPICS + entrie.getValue();

				Document doc = Jsoup.connect(pathToTopic).get();

				// root
				Elements elements = doc.getElementsByTag(TOPIC_ELEMENT);

				for (Element element : elements) {

					// text
					String text = element.getElementsByClass("entry-title").text();

					// Description
					String description = element.getElementsByClass("entry-content").text();

					// link
					String link = element.getElementsByClass("entry-title-link").attr("href");

					// Image
					Elements elementsByClass = element.getElementsByAttribute("data-min-width-1140");
					String imageSRC = elementsByClass.attr("data-min-width-1140");
					String image = Jsoup.parse(imageSRC).body().getElementsByTag("img").attr("src");

					// check parameters
					if (StringUtils.isNotBlank(text)
							  && StringUtils.isNotBlank(description)
							  && StringUtils.isNotBlank(image)
							  && StringUtils.isNotBlank(image)) {

						ResultCrawling res = new ResultCrawling();

						text = text.replace("\u00A0","");
						text = text.replace("’","'");
						text = text.replace("—","-");
						text = text.replace('\u2013','-');

						res.setTitle(text);
						res.setDescription(description);
						res.setImageUrl(image);
						res.setLink(link);
						res.setCategory(entrie.getKey());

						result.add(res);
					}

					return result;

				}


			} catch (Exception ex) {
				System.out.println("Exception crawling site: " + PATH_TO_TOPICS);
			}



		}

		return result;
	}

}