/**
 * 
 */
package org.broadleafcommerce.core.catalog.dao;

import java.util.List;

import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;

/**
 * @author javacares@gmail.com
 *
 */
public interface CategoryXrefExtendDao extends CategoryXrefDao {

	CategoryProductXref readProductXrefById(Long productId, Long categoryId);

	CategoryProductXref createCategoryProductXref();

	Long readMaxDisplayOrderByCategoryId(Long id);

	void save(CategoryProductXref xref);

	void deleteProductCategory(Long productId, List<Long> categoryIds);

}
