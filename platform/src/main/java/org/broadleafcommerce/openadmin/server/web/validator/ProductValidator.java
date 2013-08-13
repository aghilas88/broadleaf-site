/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.validator;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author javacares@gmail.com
 *
 */
@Component("slProductValidator")
public class ProductValidator implements Validator {

	/**
	 * 
	 */
	public ProductValidator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub

	}
	
	public void validateEdit(Product product, Errors errors) {
		
	}

}
