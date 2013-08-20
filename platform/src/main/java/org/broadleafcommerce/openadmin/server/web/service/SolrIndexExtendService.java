/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.service;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.search.service.solr.SolrIndexService;

/**
 * @author javacares
 *
 */
public interface SolrIndexExtendService extends SolrIndexService {

	void createIndex(Product findProductById);

}
