/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.controller;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductImpl;
import org.broadleafcommerce.openadmin.server.web.form.SearchForm;

/**
 * @author javacares@gmail.com
 *
 */
public class ProductSearchForm extends SearchForm<Product>{
	private Product product = new ProductImpl();
	/**
	 * 
	 */
	private static final long serialVersionUID = 5964738575990723300L;

	/**
	 * 
	 */
	public ProductSearchForm() {
		// TODO Auto-generated constructor stub
	}

	public Product getProduct() {
		return this.product;
	}

}
