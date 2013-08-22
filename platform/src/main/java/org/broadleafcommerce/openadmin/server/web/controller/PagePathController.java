/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.openadmin.server.web.service.SimpleCatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
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
	/**
	 * 分类列表页，主要是为了选中默认的分类。
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"category/list" })
	public String categoryList(@CookieValue(value = CategoryController.COOKIE_FOR_SELECTED_ITEM, required = false) Long id, 
			HttpServletRequest request) {
		if(null != id) {
			Category cat = this.catalogService.findCategoryById(id);
			request.setAttribute("categoryId", id);
			request.setAttribute("category", cat);
		}
		return "category/list";
	}
	
	@RequestMapping(value = {"category/grid/prd-{id}" })
	public String categoryProductAdd(@PathVariable(value = "id") Long id, HttpServletRequest request) {
		request.setAttribute("id", id);
		request.setAttribute("type", "product");
		return "category/grid";
	}	
}
