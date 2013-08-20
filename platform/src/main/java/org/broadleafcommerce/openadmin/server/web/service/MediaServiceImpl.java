/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.service;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.common.media.domain.Media;
import org.broadleafcommerce.core.catalog.dao.MediaDao;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.media.domain.CategoryMediaMap;
import org.broadleafcommerce.core.media.domain.CategoryMediaMap.CategoryMediaMapPK;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author javacares@gmail.com
 *
 */
@Service("slMediaService")
public class MediaServiceImpl implements MediaService {

	@Resource(name = "slMediaDao")
	protected MediaDao mediaDao;
	private String[] categoryMediaDirectory;
	private String[] skuMediaDirectory;
	/**
	 * 
	 */
	public MediaServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Media create() {
		return mediaDao.create();
	}

	@Override
    @Transactional("blTransactionManager")
	public void saveCategoryMedia(List<Media> medias, Category category, String key) {
		for(Media media : medias) {
			media = this.mediaDao.saveMedia(media);
			CategoryMediaMap map = new CategoryMediaMap();
			map.getCategoryMediaMapPK().setCategoryId(category.getId());
//			map.setMediaId(media.getId());
//			map.getCategoryMediaMapPK().setKey(key);
			this.mediaDao.createCategoryMedia(map);
		}
	}

	@Override
	public String[] getCategoryMediaDirectory() {
		return this.categoryMediaDirectory;
	}
	@Override
	public String[] getSkuMediaDirectory() {
		return skuMediaDirectory;
	}

	public void setSkuMediaBase(String skuMediaDirectory) {
		this.skuMediaDirectory = skuMediaDirectory.split(";");
	}

	public void setCategoryMediaBase(String categoryMediaDirectory) {
		this.categoryMediaDirectory = categoryMediaDirectory.split(";");
	}

	@Override
	public List<CategoryMediaMap> getCategoryMedias(Long id) {
		return null;
	}

	@Override
    @Transactional("blTransactionManager")	
	public void removeCategoryMedias(Long categoryId, String[] keys) {
		if(null != keys && keys.length > 0) {
			for(String key : keys) {
				CategoryMediaMapPK pk = new CategoryMediaMapPK();
				pk.setCategoryId(categoryId);
//				pk.setKey(key);
				this.mediaDao.removeCategoryMediaMap(pk);
			}
		}
	}

}
