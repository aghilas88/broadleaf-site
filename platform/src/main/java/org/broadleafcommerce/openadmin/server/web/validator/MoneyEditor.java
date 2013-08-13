/**
 * 
 */
package org.broadleafcommerce.openadmin.server.web.validator;

import java.beans.PropertyEditorSupport;
import java.util.Currency;
import java.util.Locale;

import org.broadleafcommerce.common.money.Money;

/**
 * @author javacares@gmail.com
 * 
 */
public class MoneyEditor extends PropertyEditorSupport {
	private Locale locale;
	/**
	 * 
	 */
	public MoneyEditor() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param source
	 */
	public MoneyEditor(Object source) {
		super(source);
	}

	public MoneyEditor(Locale locale) {
		this.locale = locale;
	}
	
	public Locale getDefaultLocale() {
		return Locale.CHINESE;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (null != text && text.trim().length() > 0) {
			Currency currency = Currency.getInstance(locale);
			setValue(new Money(text, currency));
		}
	}

	@Override
	public String getAsText() {
		Money value = (Money) super.getValue();
		if (null != value) {
			return String.valueOf(value.getAmount());
		} else {
			return "";
		}
	}

}
