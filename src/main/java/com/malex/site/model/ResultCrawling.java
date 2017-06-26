package com.malex.site.model;

/**
 * @author malex
 */
public class ResultCrawling {

	private String title;

	private String description;

	private String imageUrl;

	private String link;

	private String category;

	private TypeOfTopic type;

	public ResultCrawling() {
	}

	public TypeOfTopic getType() {
		return type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setType(TypeOfTopic type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
