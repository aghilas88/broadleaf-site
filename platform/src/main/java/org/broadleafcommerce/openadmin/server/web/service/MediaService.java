/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.service;

import java.util.List;

import org.broadleafcommerce.common.media.domain.Media;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.media.domain.CategoryMediaMap;

/**
 * @author javacares@gmail.com
 *
 */
public interface MediaService {

	Media create();

	void saveCategoryMedia(List<Media> medias, Category category, String key);

	String[] getCategoryMediaDirectory();

	String[] getSkuMediaDirectory();

	List<CategoryMediaMap> getCategoryMedias(Long id);

	void removeCategoryMedias(Long categoryId, String[] keys);

}
