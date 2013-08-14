/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.openadmin.server.dto.CategoryDto;
import org.broadleafcommerce.openadmin.server.web.service.SimpleCatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author javacares@gmail.com
 *
 */
@Controller
@RequestMapping("/domain/category/")
public class CategoryController {
	protected static final Logger log = LoggerFactory.getLogger(CategoryController.class);

	@Resource(name = "slCatalogService")
	protected SimpleCatalogService catalogService;

	/**
	 * 
	 */
	public CategoryController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 单选框选中默认值
	 * @param categoryId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "filteringselect/{id}")
	@ResponseBody
	public CategoryDto filteringSelected(@PathVariable("id") Long categoryId, HttpServletRequest request) {
		return new CategoryDto(this.catalogService.findCategoryById(categoryId));
	}
	
	@RequestMapping(value = "filteringselect")
	@ResponseBody
	public List<CategoryDto> filteringSelect(@RequestParam(value = "name", required = false) String name, HttpServletRequest request) {
		name = StringUtils.remove(name, "*");
		List<Category> list = catalogService.findActiveCategoriesByName(name);
		return CategoryDto.toList(list);
	}	

}
