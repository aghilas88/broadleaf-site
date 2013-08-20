/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.search.domain.Field;
import org.broadleafcommerce.core.search.service.solr.SolrContext;
import org.broadleafcommerce.core.search.service.solr.SolrIndexServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author javacares@gmail.com
 *
 */
@Service("slSolrIndexService")
public class SolrIndexExtendServiceImpl extends SolrIndexServiceImpl implements SolrIndexExtendService {
	
	protected static final Logger log = LoggerFactory.getLogger(SolrIndexExtendServiceImpl.class);
	
	/**
	 * 
	 */
	public SolrIndexExtendServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createIndex(Product product) {
        List<Field> fields = fieldDao.readAllProductFields();
        List<Locale> locales = getAllLocales();
        Collection<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		documents.add(buildDocument(product, fields, locales));

        if (log.isDebugEnabled()) {
            for (SolrInputDocument document : documents) {
            	log.debug(document.toString());
            }
        }

        if (!CollectionUtils.isEmpty(documents)) {
            try {
				SolrContext.getReindexServer().add(documents);
	            SolrContext.getReindexServer().commit();
			} catch (SolrServerException e) {
				log.error(e.getMessage(), e);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
        }
	}

}
