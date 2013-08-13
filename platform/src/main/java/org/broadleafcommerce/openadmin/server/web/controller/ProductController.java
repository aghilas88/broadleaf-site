/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.type.ProductType;
import org.broadleafcommerce.openadmin.server.dto.CategoryDto;
import org.broadleafcommerce.openadmin.server.web.service.SimpleCatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping(value = "create")
	@ResponseBody
	public Result create(@RequestParam("parentCategory") Long parentCategoryId, HttpServletRequest request) {
		Result result = new Result(Result.SUCCESSMESSAGE);
		Product proudct = this.catalogService.createProduct(ProductType.PRODUCT);
		proudct.setDefaultSku(this.catalogService.createSku());
		ServletRequestDataBinder binder = new ServletRequestDataBinder(proudct);
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
//        binder.registerCustomEditor(Money.class, new MoneyEditor(request.getLocale()));
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
//					productValidator.validate(proudct, bindingResult);
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
//				result.setObject(new ProductValue(proudct));//Should return to client for later use.
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
	
	
	@RequestMapping(value = "categories/{id}")
	@ResponseBody
	public List<CategoryDto> categories(@PathVariable(value = "id") Long productId, HttpServletResponse response) {
		List<CategoryProductXref> cats = this.catalogService.findCategoryProductXrefByProductId(productId);
		response.addHeader("Content-Range", "items 0-" + cats.size());
		return CategoryDto.toXrefList(cats);
	}	
}
