/**
 * 
 */
package org.broadleafcommerce.core.catalog.dao;

import java.util.Date;
import java.util.List;

import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;

/**
 * @author javacares@gmail.com
 *
 */
public interface CategoryXrefExtendDao extends CategoryXrefDao {
	
	List<CategoryProductXref> readCategoryProductXrefByProductId(Long productId);

	CategoryProductXref readProductXrefById(Long productId, Long categoryId);

	CategoryProductXref createCategoryProductXref();

	Long readMaxDisplayOrderByCategoryId(Long id);

	void save(CategoryProductXref xref);

	void deleteProductCategory(Long productId, List<Long> categoryIds);

	void deleteCategoryProduct(Long categoryId, List<Long> productIds);

	Long readActiveProductCountByCategory(Long id, Date currentDate);

	List<CategoryProductXref> readActiveProductsByCategory(Long categoryId,
			Date currentDate, int limit, int offset);

}
