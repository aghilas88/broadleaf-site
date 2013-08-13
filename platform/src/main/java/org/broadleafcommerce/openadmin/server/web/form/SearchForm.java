package org.broadleafcommerce.openadmin.server.web.form;

import static org.hibernate.criterion.Restrictions.isNotNull;
import static org.hibernate.criterion.Restrictions.isNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.common.util.dao.DateRange;
import org.broadleafcommerce.common.util.dao.PageRequest;
import org.broadleafcommerce.common.util.dao.Pageable;
import org.broadleafcommerce.common.util.dao.SearchTemplate;
import org.broadleafcommerce.common.util.dao.Sort;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Holds informations to be displayed and filled by the spring mvc {@link Controller}.
 * <p>
 * This class return its information as a {@link SearchTemplate} that will be consummed by the {@link GenericEntityService}
 */
public abstract class SearchForm<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private SearchParameters searchParameters = new SearchParameters();

    /**
     * @return the {@link SearchParameters} controlling search meta attributes (order, pagination, etc.)
     */
    public SearchParameters getSp() {
        return searchParameters;
    }

    /**
     * Override it in subclass in order to provide specific {@link DateRange} criteria to search.
     */
    protected List<DateRange> getDateRanges() {
        return new ArrayList<DateRange>();
    }
    
    /**
     * Override it in subclass in order to provide specific {@link NullRestriction} criteria in search.
     */
    protected List<NullRestriction> getNullRestrictions() {
        return new ArrayList<NullRestriction>();
    }
    

	public Pageable getPageable() {
		if(this.searchParameters.hasSortColumnKey()) {
			Sort sort = null;
			if(StringUtils.equalsIgnoreCase(searchParameters.getSortOrder(), SearchParameters.ASCENDING)) {
				sort = new Sort(Sort.Direction.ASC, searchParameters.getSortColumnKey());
			} else if(StringUtils.equalsIgnoreCase(searchParameters.getSortOrder(), SearchParameters.DESCENDING)) {
				sort = new Sort(Sort.Direction.DESC, searchParameters.getSortColumnKey());
			}
			return this.getPageable(sort);
		} else {
			return new PageRequest(getSp().getPageNumber() - 1, getSp().getPageSize()); 
		}
	}
	
	public Pageable getPageable(Sort sort) {
		return new PageRequest(getSp().getPageNumber() - 1, getSp().getPageSize(), sort);
	}
	
    /**
     * Copy this search form information to a new {@link SearchTemplate} and returns it.
     */
    public SearchTemplate toSearchTemplate() {
        // search meta parameters
        SearchTemplate searchTemplate = searchParameters.toSearchTemplate();
        
        searchTemplate.setFirstResult(this.getPageable().getOffset());
        searchTemplate.setMaxResults(this.getPageable().getPageSize());

        // date ranges
        searchTemplate.setDateRanges(getDateRanges());

        // null/not null criterion
        for (NullRestriction nr : getNullRestrictions()) {
            if (nr.getRestriction() == NullRestriction.NullRestrictionKind.IS_NULL) {
                searchTemplate.addCriterion(isNull(nr.getProperty()));
            } else if (nr.getRestriction() == NullRestriction.NullRestrictionKind.IS_NOT_NULL) {
                searchTemplate.addCriterion(isNotNull(nr.getProperty()));
            }
        }
        return searchTemplate;
    }
}