/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.openadmin.server.web.service.SimpleCatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author javacares@gmail.com
 *
 */
@Controller
@RequestMapping("/page/")
public class PagePathController {
	@Resource(name = "slCatalogService")
	protected SimpleCatalogService catalogService;
	/**
	 * 
	 */
	public PagePathController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = {"product/edit/{id}" })
	public String productEdit(@PathVariable(value = "id") Long id, HttpServletRequest request) {
		if(null != id) {
			Product product = catalogService.findProductById(id);
			if(null != product) {
				request.setAttribute("product", product);
				request.setAttribute("productId", id);
			}
		}
		return "product/edit";
	}
}
