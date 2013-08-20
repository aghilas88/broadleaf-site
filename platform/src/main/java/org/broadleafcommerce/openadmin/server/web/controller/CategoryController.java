/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.common.media.domain.Media;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.openadmin.server.dto.CategoryDto;
import org.broadleafcommerce.openadmin.server.dto.MediaDto;
import org.broadleafcommerce.openadmin.server.dto.ProductDto;
import org.broadleafcommerce.openadmin.server.util.ServletUtils;
import org.broadleafcommerce.openadmin.server.web.service.MediaService;
import org.broadleafcommerce.openadmin.server.web.service.RelatedProductsExtendService;
import org.broadleafcommerce.openadmin.server.web.service.SimpleCatalogService;
import org.broadleafcommerce.openadmin.server.web.validator.CategoryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.domain.Result;

/**
 * @author javacares@gmail.com
 *
 */
@Controller
@RequestMapping("/domain/category/")
public class CategoryController {
	public static final String COOKIE_FOR_SELECTED_ITEM = "INIT_TREE_NODE";
	protected static final Logger log = LoggerFactory.getLogger(CategoryController.class);

	@Resource(name = "slCatalogService")
	protected SimpleCatalogService catalogService;
	@Resource(name="slMediaService")
	protected MediaService mediaService;
	@Resource(name = "slCategoryValidator")
	protected CategoryValidator categoryValidator;
	@Resource(name = "slRelatedProductsService")
	protected RelatedProductsExtendService relatedProductsService;
	

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
	
	@RequestMapping(value = "tree")
	@ResponseBody	
	public List<CategoryDto> tree() {
		return CategoryDto.toList(catalogService.findAllCategories());
	}
	
	@RequestMapping(value = "tree/{id}")
	@ResponseBody	
	public CategoryDto children(@PathVariable("id") String categoryId, HttpServletRequest request) {
		Long catId = 1l;
		if(StringUtils.isNotBlank(categoryId) && StringUtils.isNumeric(categoryId)) {
			catId = Long.valueOf(categoryId);
		}
		Category category = this.catalogService.findCategoryById(catId);
		if(null != category) {
			CategoryDto dto = new CategoryDto(category);
			List<Category> list = this.catalogService.findAllSubCategories(category);
			dto.addChildren(list);
			return dto;
		}
		return new CategoryDto();
	}

	@RequestMapping(value = "media/{id}")
	@ResponseBody
	public List<MediaDto> media(@RequestParam(required = false, value="id") Long id, 
			@CookieValue(value=COOKIE_FOR_SELECTED_ITEM, required = false) Long cid, 
			HttpServletRequest request, HttpServletResponse resp) {
		List<MediaDto> list = new LinkedList<MediaDto>();
		if(null == id) {
			id = cid;
		}
		if(null != id){
			Category category = this.catalogService.findCategoryById(id);
			if(null != category) {
				Map<String, Media> map = category.getCategoryMedia();
				for(Map.Entry<String, Media> entry : map.entrySet()) {
					MediaDto value = new MediaDto(entry.getKey(), entry.getValue());
					list.add(value);
				}
			}
			resp.addCookie(getSelectedTreeNodeCookie(id));
		}
		return list;
	}
	@RequestMapping(value = "media/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result product(@PathVariable("id") Long categoryId, @RequestParam("mediaIds") String mediaIds) {
		Result result = new Result(Result.SUCCESSMESSAGE);
		String[] array = mediaIds.split(",");
		Long[] ids = new Long[array.length];
		for(int i=0;i<array.length;i++) {
			ids[i] = Long.valueOf(array[i]);
		}
		
		try {
			this.catalogService.removeCategoryMedias(categoryId, Arrays.asList(ids));
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setMessage(e.getMessage());
			result.setHasError(true);
		}
		return result;
	}
	
	@RequestMapping(value = "product/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductDto> product(@RequestParam(required = false, value="id") Long categoryId, 
			@CookieValue(value=COOKIE_FOR_SELECTED_ITEM, required = false) Long cid, 
			HttpServletRequest request, HttpServletResponse response) {
		Date currentDate = new Date();
//		Category category = this.catalogService.findCategoryById(categoryId);
		Long total = catalogService.findActiveProductCountByCategory(categoryId, currentDate);
		if(null != total && total.intValue() > 0) {
			int[] limit = ServletUtils.getRangeLimitAndOffset(request);
			response.addHeader("Content-Range", ServletUtils.getContentRange(total, limit[0], limit[1]));
			return ProductDto.toXrefList(this.catalogService.findActiveProductsByCategory(categoryId, currentDate, limit[0], limit[1]));
		}
		return new ArrayList<ProductDto>();
	}
	
	@RequestMapping(value = "product/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result product(@PathVariable("id") Long categoryId, @RequestParam("productIds") String productIds, HttpServletRequest request) {
		Result result = new Result(Result.SUCCESSMESSAGE);
		String[] array = productIds.split(",");
		Long[] ids = new Long[array.length];
		for(int i=0;i<array.length;i++) {
			ids[i] = Long.valueOf(array[i]);
		}
		try {
			this.catalogService.removeCategoryProducts(categoryId, Arrays.asList(ids));
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setMessage(e.getMessage());
			result.setHasError(true);
		}
		return result;
	}
	
	protected Cookie getSelectedTreeNodeCookie(Long id) {
		Cookie c = new Cookie(COOKIE_FOR_SELECTED_ITEM, String.valueOf(id));
		c.setMaxAge(60 * 60 * 24 * 30);
		return c;
	}
}
