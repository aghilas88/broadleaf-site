/**
 * 
 */
package org.broadleafcommerce.core.catalog.dao;

import java.util.List;

import org.broadleafcommerce.core.catalog.domain.Category;

/**
 * @author javacares@gmail.com
 *
 */
public interface CategoryExtendDao extends CategoryDao {

	List<Category> readCategoriesByNameAndStatus(String name);

	void deleteMedia(Long categoryId, List<Long> mediaIds);

}
