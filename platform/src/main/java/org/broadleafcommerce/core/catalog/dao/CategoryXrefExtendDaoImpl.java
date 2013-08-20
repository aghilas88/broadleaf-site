/**
 * 
 */
package org.broadleafcommerce.core.catalog.dao;

import java.util.Date;
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
	public static final String BC_READ_CATEGORY_XREF_BY_PRODUCTID = "SELECT xref " + 
			"FROM org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl xref " + 
            "WHERE xref.categoryProductXref.product.id = :productId ";

	public static final String readProductXrefById = "select xref from org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl xref "
		+ "where xref.categoryProductXref.category.id = :categoryId and xref.categoryProductXref.product.id = :productId";
	
	public static final String readMaxDisplayOrderByCategoryId = "select max(xref.displayOrder) from org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl xref "
		+ " where xref.categoryProductXref.category.id = :categoryId";

	public static final String readActiveProductCountByCategory = "SELECT count(categoryProduct.categoryProductXref.product) FROM org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl categoryProduct "
        + " WHERE categoryProduct.categoryProductXref.category.id = :categoryId " 
        + " AND categoryProduct.categoryProductXref.product.defaultSku.activeStartDate &lt;= :currentDate "
        + " AND (categoryProduct.categoryProductXref.product.defaultSku.activeEndDate &gt; :currentDate OR categoryProduct.categoryProductXref.product.defaultSku.activeEndDate = null) "
        + "        AND (categoryProduct.categoryProductXref.product.archiveStatus.archived IS NULL OR categoryProduct.categoryProductXref.product.archiveStatus.archived = 'N') ";
	
	public static final String readActiveProductsByCategory = "SELECT categoryProduct.categoryProductXref.product FROM org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl categoryProduct "
	        + " WHERE categoryProduct.categoryProductXref.category.id = :categoryId " 
	        + " AND categoryProduct.categoryProductXref.product.defaultSku.activeStartDate &lt;= :currentDate "
	        + " AND (categoryProduct.categoryProductXref.product.defaultSku.activeEndDate &gt; :currentDate OR categoryProduct.categoryProductXref.product.defaultSku.activeEndDate = null) "
	        + "        AND (categoryProduct.categoryProductXref.product.archiveStatus.archived IS NULL OR categoryProduct.categoryProductXref.product.archiveStatus.archived = 'N') "
	        + " ORDER by categoryProduct.displayOrder desc";

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

	@Override
	public void deleteCategoryProduct(Long categoryId, List<Long> productIds) {
		if(null != productIds) {
			for(Long productId : productIds) {
				CategoryProductXref entity = this.readProductXrefById(productId, categoryId);
				if(null != entity) {
					em.remove(entity);
				}
			}
		}		
	}

	@Override
	public Long readActiveProductCountByCategory(Long categoryId, Date currentDate) {
        TypedQuery<Long> query = em.createQuery(readActiveProductCountByCategory, Long.class);
        query.setParameter("categoryId", categoryId);
        query.setParameter("currentDate", currentDate);
        Long value = query.getSingleResult() ;
		return value == null ? 1 : value + 1;
	}

	@Override
	public List<CategoryProductXref> readCategoryProductXrefByProductId(Long productId) {
        TypedQuery<CategoryProductXref> query = em.createQuery(BC_READ_CATEGORY_XREF_BY_PRODUCTID, CategoryProductXref.class);
        query.setParameter("productId", productId);
        return query.getResultList();
	}

	@Override
	public List<CategoryProductXref> readActiveProductsByCategory(
			Long categoryId, Date currentDate, int limit, int offset) {
        TypedQuery<CategoryProductXref> query = em.createQuery(BC_READ_CATEGORY_XREF_BY_PRODUCTID, CategoryProductXref.class);
        query.setParameter("categoryId", categoryId);
        query.setParameter("currentDate", currentDate);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
	}

}
