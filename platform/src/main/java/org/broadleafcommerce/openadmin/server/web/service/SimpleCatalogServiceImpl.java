/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.service;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.core.catalog.dao.CategoryExtendDao;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.CatalogServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author javacares@gmail.com
 *
 */
@Service("slCatalogService")
public class SimpleCatalogServiceImpl extends CatalogServiceImpl implements SimpleCatalogService {

	@Resource(name = "slCategoryDao")
	protected CategoryExtendDao categoryExtendDao;
	
	@Override
	public List<Category> findActiveCategoriesByName(String name) {
		return super.findAllCategories();
	}

	@Override
	public List<CategoryProductXref> findCategoryProductXrefByProductId(Long productId) {
		return this.categoryExtendDao.readCategoryProductXrefByProductId(productId);
	}

}
