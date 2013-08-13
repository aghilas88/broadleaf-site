/**
 * 
 */
package org.broadleafcommerce.core.catalog.dao;

import java.util.List;

import org.broadleafcommerce.common.util.dao.SearchTemplate;
import org.broadleafcommerce.core.catalog.domain.Product;

/**
 * @author javacares@gmail.com
 *
 */
public interface ProductExtendDao extends ProductDao {

	List<Product> readProductListBySearch(Product product,
			SearchTemplate searchTemplate);

	long readProductCountBySearch(Product product, SearchTemplate searchTemplate);

}
