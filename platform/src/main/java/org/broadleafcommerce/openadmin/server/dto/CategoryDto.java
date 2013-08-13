/**
 * 
 */
package org.broadleafcommerce.openadmin.server.dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;

/**
 * @author javacares@gmail.com
 *
 */
public class CategoryDto {
	protected Long id;
	protected String name;
    protected String url;
    protected String urlKey;
    protected String description;
    protected Date activeStartDate;
    protected Date activeEndDate;
    protected Long displayOrder;
    protected String displayTemplate;
    protected Long defaultParentCategoryId;
    protected String defaultParentCategoryName;

	/**
	 * 
	 */
	public CategoryDto() {
		// TODO Auto-generated constructor stub
	}

	public CategoryDto(Category cat) {
		this.id = cat.getId();
		this.name = cat.getName();
		this.url = cat.getUrl();
		this.urlKey = cat.getUrlKey();
		this.description = cat.getDescription();
		this.displayTemplate = cat.getDisplayTemplate();
		if(null != cat.getDefaultParentCategory()) {
			this.defaultParentCategoryId = cat.getDefaultParentCategory().getId();
			this.defaultParentCategoryName = cat.getDefaultParentCategory().getName();
		}
		this.activeEndDate = cat.getActiveEndDate();
		this.activeStartDate = cat.getActiveStartDate();
		
	}

	public CategoryDto(CategoryProductXref xref) {
		this(xref.getCategory());
		this.displayOrder = xref.getDisplayOrder();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlKey() {
		return urlKey;
	}

	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getActiveStartDate() {
		return activeStartDate;
	}

	public void setActiveStartDate(Date activeStartDate) {
		this.activeStartDate = activeStartDate;
	}

	public Date getActiveEndDate() {
		return activeEndDate;
	}

	public void setActiveEndDate(Date activeEndDate) {
		this.activeEndDate = activeEndDate;
	}

	public String getDisplayTemplate() {
		return displayTemplate;
	}

	public void setDisplayTemplate(String displayTemplate) {
		this.displayTemplate = displayTemplate;
	}

	public Long getDefaultParentCategoryId() {
		return defaultParentCategoryId;
	}

	public void setDefaultParentCategoryId(Long defaultParentCategoryId) {
		this.defaultParentCategoryId = defaultParentCategoryId;
	}

	public String getDefaultParentCategoryName() {
		return defaultParentCategoryName;
	}

	public void setDefaultParentCategoryName(String defaultParentCategoryName) {
		this.defaultParentCategoryName = defaultParentCategoryName;
	}
	
	public Long getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

	public static List<CategoryDto> toList(List<Category> list) {
		if(null != list) {
			List<CategoryDto> dtos = new LinkedList<CategoryDto>();
			for(Category cat : list) {
				dtos.add(new CategoryDto(cat));
			}
			return dtos;
		}
		return null;
	}

	public static List<CategoryDto> toXrefList(List<CategoryProductXref> cats) {
		if(null != cats) {
			List<CategoryDto> list = new LinkedList<CategoryDto>();
			for(CategoryProductXref xref : cats) {
				if(null != xref) {
					list.add(new CategoryDto(xref));
				}
			}
			return list;
		}
		return null;
	}
}
