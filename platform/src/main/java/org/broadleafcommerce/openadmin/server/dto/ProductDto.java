/**
 * 
 */
package org.broadleafcommerce.openadmin.server.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.Sku;

/**
 * @author javacares@gmail.com
 *
 */
public class ProductDto {
	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    /** The id. */
    protected Long id;
    protected String url;
    protected String urlKey;
    protected String displayTemplate;
    protected String model;
    protected String manufacturer;
    protected Boolean isFeaturedProduct = false;
    protected Boolean canSellWithoutOptions = false;
    protected String promoMessage;
    protected Long defaultCategory;
    protected String defaultCategoryName;
    
    protected Long skuId;
    protected BigDecimal salePrice;
    protected BigDecimal retailPrice;
    protected String name;
    protected String description;
    protected String longDescription;
    protected boolean taxable;
    protected boolean discountable;
    protected boolean available;
    protected String activeStartDate;
    protected String activeEndDate;
    protected Boolean isMachineSortable = true;
    protected String inventoryType;
    protected String fulfillmentType;  
	/**
	 * 
	 */
	public ProductDto() {
		// TODO Auto-generated constructor stub
	}

	
	public ProductDto(Product product) {
		this.id = product.getId();
		this.url = product.getUrl();
		this.urlKey = product.getUrlKey();
		this.displayTemplate = product.getDisplayTemplate();
		this.manufacturer = product.getManufacturer();
		this.model = product.getModel();
		this.canSellWithoutOptions = product.getCanSellWithoutOptions();
		this.promoMessage = product.getPromoMessage();
		Category cat = product.getDefaultCategory();
		if(null != cat) {
			this.defaultCategory = product.getDefaultCategory().getId();
			this.defaultCategoryName = product.getDefaultCategory().getName();
		}
		
		Sku sku = product.getDefaultSku();
		if(null != sku) {
			this.skuId = sku.getId();
			this.salePrice = sku.getSalePrice() == null ? null : sku.getSalePrice().getAmount();
			this.retailPrice = sku.getRetailPrice() == null ? null : sku.getRetailPrice().getAmount();
			this.name = sku.getName();
			this.description = sku.getDescription();
			this.longDescription = sku.getLongDescription();
			this.taxable = sku.getTaxable();
			this.discountable = sku.isDiscountable() == null ? false : sku.isDiscountable();
			if(null != sku.getActiveStartDate()) {
				this.activeStartDate = sdf.format(sku.getActiveStartDate());
			}
			if(null != sku.getActiveEndDate()) {
				this.activeEndDate = sdf.format(sku.getActiveEndDate());
			}
		}
	}


	public SimpleDateFormat getSdf() {
		return sdf;
	}


	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public String getDisplayTemplate() {
		return displayTemplate;
	}


	public void setDisplayTemplate(String displayTemplate) {
		this.displayTemplate = displayTemplate;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getManufacturer() {
		return manufacturer;
	}


	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}


	public Boolean getIsFeaturedProduct() {
		return isFeaturedProduct;
	}


	public void setIsFeaturedProduct(Boolean isFeaturedProduct) {
		this.isFeaturedProduct = isFeaturedProduct;
	}


	public Boolean getCanSellWithoutOptions() {
		return canSellWithoutOptions;
	}


	public void setCanSellWithoutOptions(Boolean canSellWithoutOptions) {
		this.canSellWithoutOptions = canSellWithoutOptions;
	}


	public String getPromoMessage() {
		return promoMessage;
	}


	public void setPromoMessage(String promoMessage) {
		this.promoMessage = promoMessage;
	}


	public Long getDefaultCategory() {
		return defaultCategory;
	}


	public void setDefaultCategory(Long defaultCategory) {
		this.defaultCategory = defaultCategory;
	}


	public String getDefaultCategoryName() {
		return defaultCategoryName;
	}


	public void setDefaultCategoryName(String defaultCategoryName) {
		this.defaultCategoryName = defaultCategoryName;
	}


	public Long getSkuId() {
		return skuId;
	}


	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}


	public BigDecimal getSalePrice() {
		return salePrice;
	}


	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}


	public BigDecimal getRetailPrice() {
		return retailPrice;
	}


	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getLongDescription() {
		return longDescription;
	}


	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}


	public boolean isTaxable() {
		return taxable;
	}


	public void setTaxable(boolean taxable) {
		this.taxable = taxable;
	}


	public boolean isDiscountable() {
		return discountable;
	}


	public void setDiscountable(boolean discountable) {
		this.discountable = discountable;
	}


	public boolean isAvailable() {
		return available;
	}


	public void setAvailable(boolean available) {
		this.available = available;
	}


	public String getActiveStartDate() {
		return activeStartDate;
	}


	public void setActiveStartDate(String activeStartDate) {
		this.activeStartDate = activeStartDate;
	}


	public String getActiveEndDate() {
		return activeEndDate;
	}


	public void setActiveEndDate(String activeEndDate) {
		this.activeEndDate = activeEndDate;
	}


	public Boolean getIsMachineSortable() {
		return isMachineSortable;
	}


	public void setIsMachineSortable(Boolean isMachineSortable) {
		this.isMachineSortable = isMachineSortable;
	}


	public String getInventoryType() {
		return inventoryType;
	}


	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}


	public String getFulfillmentType() {
		return fulfillmentType;
	}


	public void setFulfillmentType(String fulfillmentType) {
		this.fulfillmentType = fulfillmentType;
	}


	public static List<ProductDto> toList(List<Product> list) {
		if(null != list && list.size() > 0) {
			List<ProductDto> dtos = new LinkedList<ProductDto>();
			for(Product p : list) {
				dtos.add(new ProductDto(p));
			}
			return dtos;
		}
		return null;
	}
	
	
}
