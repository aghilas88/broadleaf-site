/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.service;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.common.util.dao.SearchTemplate;
import org.broadleafcommerce.core.catalog.dao.CategoryExtendDao;
import org.broadleafcommerce.core.catalog.dao.CategoryXrefExtendDao;
import org.broadleafcommerce.core.catalog.dao.ProductExtendDao;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.CatalogServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author javacares@gmail.com
 *
 */
@Service("slCatalogService")
public class SimpleCatalogServiceImpl extends CatalogServiceImpl implements SimpleCatalogService {

	@Resource(name = "slCategoryDao")
	protected CategoryExtendDao categoryExtendDao;
	@Resource(name = "slProductDao")
	protected ProductExtendDao productExtendDao;
	@Resource(name = "")
	protected CategoryXrefExtendDao categoryXrefDao;
	@Override
	public List<Category> findActiveCategoriesByName(String name) {
		return this.categoryExtendDao.readCategoriesByNameAndStatus(name);
	}

	@Override
	public List<CategoryProductXref> findCategoryProductXrefByProductId(Long productId) {
		return this.categoryExtendDao.readCategoryProductXrefByProductId(productId);
	}
	
	@Override
	public List<Product> findProductListBySearch(Product product, SearchTemplate searchTemplate) {
		return this.productExtendDao.readProductListBySearch(product, searchTemplate);
	}

	@Override
	@Transactional("blTransactionManager")
	public long findProductCountBySearch(Product product, SearchTemplate searchTemplate) {
		return 	this.productExtendDao.readProductCountBySearch(product, searchTemplate);
	}

	@Override
	@Transactional("blTransactionManager")
	public void saveProductCategories(Long productId, List<Long> ids) {
		if(null != ids) {
			Product prd = super.productDao.readProductById(productId);
			for(Long id : ids) {
				CategoryProductXref xref = categoryXrefDao.readProductXrefById(productId, id);
				if(null == xref) {
					xref = categoryXrefDao.createCategoryProductXref();
					xref.setProduct(prd);
					xref.setCategory(super.categoryDao.readCategoryById(id));
					xref.setDisplayOrder(categoryXrefDao.readMaxDisplayOrderByCategoryId(id));
					categoryXrefDao.save(xref);
				}
			}
		}		
	}

	@Override
	@Transactional("blTransactionManager")
	public void removeProductCategories(Long productId, List<Long> categoryIds) {
		this.categoryXrefDao.deleteProductCategory(productId, categoryIds);
	}

}
