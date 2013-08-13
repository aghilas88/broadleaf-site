/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.service;

import java.util.List;

import org.broadleafcommerce.common.util.dao.SearchTemplate;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.CatalogService;

/**
 * @author javacares@gmail.com
 *
 */
public interface SimpleCatalogService extends CatalogService {

	List<Category> findActiveCategoriesByName(String name);

	List<CategoryProductXref> findCategoryProductXrefByProductId(Long productId);

	List<Product> findProductListBySearch(Product product, SearchTemplate searchTemplate);

	long findProductCountBySearch(Product product, SearchTemplate searchTemplate);

	void saveProductCategories(Long productId, List<Long> ids);

	void removeProductCategories(Long productId, List<Long> ids);
}
