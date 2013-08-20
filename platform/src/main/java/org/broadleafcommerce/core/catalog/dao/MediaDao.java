/**
 * 
 */
package org.broadleafcommerce.core.catalog.dao;

import org.broadleafcommerce.common.media.domain.Media;
import org.broadleafcommerce.core.media.domain.CategoryMediaMap;
import org.broadleafcommerce.core.media.domain.CategoryMediaMap.CategoryMediaMapPK;

/**
 * @author javacares@gmail.com
 *
 */
public interface MediaDao {

	Media create();

	Media saveMedia(Media media);

	void createCategoryMedia(CategoryMediaMap map);

	void removeCategoryMediaMap(CategoryMediaMapPK pk);
}
