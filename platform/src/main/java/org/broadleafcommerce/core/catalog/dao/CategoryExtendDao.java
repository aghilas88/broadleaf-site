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
public interface CategoryExtendDao extends CategoryDao {

	List<CategoryProductXref> readCategoryProductXrefByProductId(Long productId);

}
