/**
 * 
 */
package org.broadleafcommerce.core.catalog.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.springframework.stereotype.Repository;

/**
 * @author javacares@gmail.com
 *
 */
@Repository("slCategoryDao")
public class CategoryExtendDaoImpl extends CategoryDaoImpl implements CategoryExtendDao {
	
	protected static final String readCategoriesByNameAndStatus = "select cat from org.broadleafcommerce.core.catalog.domain.CategoryImpl cat "
			+ " where cat.name like :name";
	protected static final String deleteCategoryMedia = "delete from org.broadleafcommerce.core.media.domain.CategoryMediaMap map "
			+ " where map.categoryMediaMapPK.mediaId in(:mediaIds) and map.categoryMediaMapPK.categoryId = :categoryId";
	
	/**
	 * 
	 */
	public CategoryExtendDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Category> readCategoriesByNameAndStatus(String name) {
		TypedQuery<Category> query = em.createQuery(readCategoriesByNameAndStatus, Category.class);
		if(!StringUtils.endsWith(name, "%")) {
			name = name + "%";
		}
		query.setParameter("name", name);
		return query.getResultList();
	}

	@Override
	public void deleteMedia(Long categoryId, List<Long> mediaIds) {
		Query query = em.createQuery(deleteCategoryMedia);
		query.setParameter("categoryId", categoryId);
		query.setParameter("mediaIds", mediaIds);
		query.executeUpdate();
	}

}
