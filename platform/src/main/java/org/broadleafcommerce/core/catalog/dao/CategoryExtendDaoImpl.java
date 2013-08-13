/**
 * 
 */
package org.broadleafcommerce.core.catalog.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;
import org.springframework.stereotype.Repository;

/**
 * @author javacares@gmail.com
 *
 */
@Repository("slCategoryDao")
public class CategoryExtendDaoImpl extends CategoryDaoImpl implements CategoryExtendDao {
	
	protected static final String BC_READ_CATEGORY_XREF_BY_PRODUCTID = "SELECT xref " + 
			"FROM org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl xref " + 
            "WHERE xref.categoryProductXref.product.id = :productId ";
	
	/**
	 * 
	 */
	public CategoryExtendDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<CategoryProductXref> readCategoryProductXrefByProductId(Long productId) {
        TypedQuery<CategoryProductXref> query = em.createQuery(BC_READ_CATEGORY_XREF_BY_PRODUCTID, CategoryProductXref.class);
        query.setParameter("productId", productId);
        return query.getResultList();
	}

}
