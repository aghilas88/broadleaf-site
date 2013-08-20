/**
 * 
 */
package org.broadleafcommerce.openadmin.server.dto;

import org.broadleafcommerce.common.media.domain.Media;

/**
 * @author javacares@gmail.com
 *
 */
public class MediaDto {
    private long id;
    private String url = "";
    private String title = "";
    private String altText = "";
    private String tags = "";
    
    private String key;
    
	/**
	 * 
	 */
	public MediaDto() {
		// TODO Auto-generated constructor stub
	}

	public MediaDto(String key, Media media) {
		this(media);
		this.key = key;
	}

	public MediaDto(Media media) {
		this.id = media.getId();
		this.url = media.getUrl();
		this.altText = media.getAltText();
		this.tags = media.getTags();
		this.title = media.getTitle();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
