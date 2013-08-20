/**
 * 
 */
package org.broadleafcommerce.core.catalog.dao;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.broadleafcommerce.common.media.domain.Media;
import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.core.media.domain.CategoryMediaMap;
import org.broadleafcommerce.core.media.domain.CategoryMediaMap.CategoryMediaMapPK;
import org.springframework.stereotype.Repository;

/**
 * @author javacares@gmail.com
 *
 */
@Repository("slMediaDao")
public class MediaDaoImpl implements MediaDao {

	@PersistenceContext(unitName="blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

	/**
	 * 
	 */
	public MediaDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.broadleafcommerce.core.catalog.dao.MediaDao#create()
	 */
	@Override
	public Media create() {
		return entityConfiguration.createEntityInstance(Media.class.getName(), Media.class);
	}

	@Override
	public Media saveMedia(Media media) {
		return em.merge(media);
	}

	@Override
	public void createCategoryMedia(CategoryMediaMap map) {
		em.merge(map);
	}

	@Override
	public void removeCategoryMediaMap(CategoryMediaMapPK primaryKey) {
		em.remove(em.find(CategoryMediaMap.class, primaryKey));
	}

}
