/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.util.dao.SearchTemplate;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.type.ProductType;
import org.broadleafcommerce.openadmin.server.dto.CategoryDto;
import org.broadleafcommerce.openadmin.server.dto.ProductDto;
import org.broadleafcommerce.openadmin.server.web.service.SimpleCatalogService;
import org.broadleafcommerce.openadmin.server.web.service.SolrIndexExtendService;
import org.broadleafcommerce.openadmin.server.web.validator.MoneyEditor;
import org.broadleafcommerce.openadmin.server.web.validator.ProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.domain.FieldMessage;
import org.springframework.web.domain.Result;
import org.springframework.web.servlet.support.RequestContext;

/**
 * @author javacares@gmail.com
 *
 */
@Controller
@RequestMapping("/domain/product/")
public class ProductController {
	protected static final Logger log = LoggerFactory.getLogger(ProductController.class);

	@Resource(name = "slCatalogService")
	protected SimpleCatalogService catalogService;
	@Resource(name = "slProductValidator")
	protected ProductValidator productValidator;
	@Resource(name = "slSolrIndexService")
	protected SolrIndexExtendService solrIndexService;
	
	@RequestMapping(value = "create")
	@ResponseBody
	public Result create(@RequestParam("parentCategory") Long parentCategoryId, HttpServletRequest request) {
		Result result = new Result(Result.SUCCESSMESSAGE);
		Product proudct = this.catalogService.createProduct(ProductType.PRODUCT);
		proudct.setDefaultSku(this.catalogService.createSku());
		ServletRequestDataBinder binder = new ServletRequestDataBinder(proudct);
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
        binder.registerCustomEditor(Money.class, new MoneyEditor(request.getLocale()));
		binder.bind(request);
		BindingResult bindingResult = binder.getBindingResult();
		if(!bindingResult.hasErrors()) {
			if(StringUtils.isNotBlank(proudct.getUrl()) && !StringUtils.startsWith(proudct.getUrl(), "/")) {
				proudct.setUrl("/" + proudct.getUrl());
			}
			if(null == proudct.getActiveStartDate()) {
				proudct.setActiveStartDate(new Date());//Set Default active date.
			}
			if(null != parentCategoryId) {
				Category parent = this.catalogService.findCategoryById(parentCategoryId);
				if(null != parent) {//Should be validated at first.
					proudct.setDefaultCategory(parent);
					productValidator.validate(proudct, bindingResult);
				} else {
					String message = new RequestContext(request).getMessage(
							"product.defaultCategory.tip.notExisted", new String[]{String.valueOf(parentCategoryId)});
					result.addError(new FieldMessage("parentCategory", parentCategoryId, message));
				}
			}
		}
		if(!bindingResult.hasErrors()) {
			try {
				proudct = this.catalogService.saveProduct(proudct);
				result.setObject(new ProductDto(proudct));//Should return to client for later use.
			}catch (Exception e) {
				result.setMessage(e.getMessage());
				result.setHasError(true);
			}
		} else {
			RequestContext context = new RequestContext(request);
			result.initMessages(context, bindingResult);
		}
		return result;
	}
	
	@RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result edit(@PathVariable("id")Long productId, HttpServletRequest request) {
		Result result = new Result(Result.SUCCESSMESSAGE);
		if(null != productId) {
			Product product = this.catalogService.findProductById(productId);
			if(null != product) {
				Long defaultCategoryId = Long.valueOf(request.getParameter("defaultCategoryId"));
				if(null != defaultCategoryId && defaultCategoryId.longValue() != product.getDefaultCategory().getId()) {
					product.setDefaultCategory(this.catalogService.findCategoryById(defaultCategoryId));
				}
				ServletRequestDataBinder binder = new ServletRequestDataBinder(product);
		        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		        binder.registerCustomEditor(Money.class, new MoneyEditor(request.getLocale()));
				binder.bind(request);
				BindingResult bindingResult = binder.getBindingResult();
				if(!bindingResult.hasErrors()) {
					this.productValidator.validateEdit(product, bindingResult);
				}
				if(!bindingResult.hasErrors()) {
					try {
						this.catalogService.saveProduct(product);
					}catch (Exception e) {
						result.setMessage(e.getMessage());
						result.setHasError(true);
					}
				} else {
					RequestContext context = new RequestContext(request);
					result.initMessages(context, bindingResult);
				}
			}
		}
		return result;
	}
	
	@RequestMapping(value = "publish/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result publish(@PathVariable("id")Long productId, HttpServletRequest request) {
		Result result = new Result(Result.SUCCESSMESSAGE);
		solrIndexService.createIndex(this.catalogService.findProductById(productId));
		return result;
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public List<ProductDto> list(@ModelAttribute ProductSearchForm productSearchForm, HttpServletRequest request, HttpServletResponse response) {
		System.err.println(productSearchForm);
		long count = this.catalogService.findProductCountBySearch(productSearchForm.getProduct(), productSearchForm.toSearchTemplate());
		if(count > 0) {
			List<Product> list = this.catalogService.findProductListBySearch(productSearchForm.getProduct(), productSearchForm.toSearchTemplate());
			response.addHeader("Content-Range", getContentRange(productSearchForm.toSearchTemplate(), count));
			return ProductDto.toList(list);
		}
		return new ArrayList<ProductDto>();
	}
	
	public String getContentRange(SearchTemplate searchTemplate, long count) {
		return "items " +  searchTemplate.getFirstResult() + "-" + (searchTemplate.getFirstResult() + searchTemplate.getMaxResults()) + "/" + count;
	}

	/**
	 * 显示产品绑定的所有分类
	 * @param productId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "categories/{id}")
	@ResponseBody
	public List<CategoryDto> categories(@PathVariable(value = "id") Long productId, HttpServletResponse response) {
		List<CategoryProductXref> cats = this.catalogService.findCategoryProductXrefByProductId(productId);
		response.addHeader("Content-Range", "items 0-" + cats.size() + "/" + cats.size());
		return CategoryDto.toXrefList(cats);
	}	
	
	/**
	 * 为产品添加分类
	 * @param productId
	 * @param categoryIds
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "category/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result categoryAdd(@PathVariable("id") Long productId, @RequestParam("categoryIds") String categoryIds, HttpServletRequest request) {
		Result result = new Result(Result.SUCCESSMESSAGE);
		String[] array = categoryIds.split(";");
		List<Long> ids = new ArrayList<Long>();
		try {
			for(String s : array) {
				ids.add(Long.valueOf(s));
			}
		}catch (Exception e) {
			result.setMessage("必须输入用分号分割的整数。");
			result.setHasError(true);
		}
		StringBuffer sb = new StringBuffer();
		if(!result.isHasError()) {
			for(Long id : ids) {
				if(this.catalogService.findCategoryById(id) == null) {
					sb.append(id).append(",");
				}
			}
			if(sb.length() > 0) {
				result.setHasError(true);
				result.setMessage("分类：" + sb.toString() + " 不存在");
			}
		}
		
		if(!result.isHasError()) {
	
			try {
				this.catalogService.saveProductCategories(productId, ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				result.setMessage(e.getMessage());
				result.setHasError(true);
			}
		}
		return result;
	}
	
	/**
	 * 为产品删除分类
	 * @param id
	 * @param categoryIds
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "category/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result categoryDelete(@PathVariable("id") Long productId, @RequestParam("categoryIds") String categoryIds, HttpServletRequest request) {
		Result result = new Result(Result.SUCCESSMESSAGE);
		if(null != categoryIds && categoryIds.trim().length() > 0) {
			String[] array = categoryIds.split(",");
			List<Long> ids = new ArrayList<Long>();
			for(String s : array) {
				ids.add(Long.valueOf(s));
			}
			try {
				this.catalogService.removeProductCategories(productId, ids);
			}catch (Exception e) {
				result.setMessage(e.getMessage());
				result.setHasError(true);
			}
		}
		return result;
	}	
}
