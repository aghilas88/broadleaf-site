/**
 * 
 */
package org.broadleafcommerce.core.catalog.dao;

import java.util.List;

import org.broadleafcommerce.common.util.dao.SearchTemplate;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.springframework.stereotype.Repository;

/**
 * @author javacares@gmail.com
 *
 */
@Repository("slProductDao")
public class ProductExtendDaoImpl extends ProductDaoImpl implements ProductExtendDao {

	/**
	 * 
	 */
	public ProductExtendDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Product> readProductListBySearch(Product product,
			SearchTemplate searchTemplate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long readProductCountBySearch(Product product,
			SearchTemplate searchTemplate) {
		// TODO Auto-generated method stub
		return 0;
	}

}
