/**
 * 
 */
package org.broadleafcommerce.core.catalog.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;
import org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl;
import org.springframework.stereotype.Repository;

/**
 * @author javacares@gmail.com
 *
 */
@Repository("slCategoryXrefExtendDao")
public class CategoryXrefExtendDaoImpl extends CategoryXrefDaoImpl implements CategoryXrefExtendDao {

	public static final String readProductXrefById = "select xref from org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl xref "
		+ "where xref.categoryProductXref.category.id = :categoryId and xref.categoryProductXref.product.id = :productId";
	
	public static final String readMaxDisplayOrderByCategoryId = "select max(xref.displayOrder) from org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl xref "
		+ " where xref.categoryProductXref.category.id = :categoryId";
	/**
	 * 
	 */
	public CategoryXrefExtendDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CategoryProductXref readProductXrefById(Long productId, Long categoryId) {
		TypedQuery<CategoryProductXref> query = em.createQuery(readProductXrefById, CategoryProductXref.class);
		query.setParameter("categoryId", categoryId);
		query.setParameter("productId", productId);
		try {
			return query.getSingleResult();
		}catch (NoResultException ne) {
			return null;
		}		
	}

	@Override
	public CategoryProductXref createCategoryProductXref() {
		return new CategoryProductXrefImpl();
	}

	@Override
	public Long readMaxDisplayOrderByCategoryId(Long categoryId) {
        TypedQuery<Long> query = em.createQuery(readMaxDisplayOrderByCategoryId, Long.class);
        query.setParameter("categoryId", categoryId);
        Long value = query.getSingleResult() ;
		return value == null ? 1 : value + 1;
	}

	@Override
	public void save(CategoryProductXref xref) {
		em.merge(xref);
	}

	@Override
	public void deleteProductCategory(Long productId, List<Long> categoryIds) {
		if(null != categoryIds) {
			for(Long categoryId : categoryIds) {
				CategoryProductXref entity = this.readProductXrefById(productId, categoryId);
				if(null != entity) {
					em.remove(entity);
				}
			}
		}	
	}

}
