/**
 * 
 */
package org.springframework.web.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.support.RequestContext;

/**
 * @author javacares@gmail.com
 *
 */
public class Result {
	public static final String SUCCESSMESSAGE = "操作成功！";
	private boolean hasError = false;
	private String message;//when hasError is false, should show tips;
	private List<FieldMessage> objectErrors;
	private Object object;
	/**
	 * 
	 */
	public Result() {
		// TODO Auto-generated constructor stub
	}
	
	public Result(String message) {
		this.message = message;
	}

	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	public List<FieldMessage> getObjectErrors() {
		return objectErrors;
	}
	public void setObjectErrors(List<FieldMessage> objectErrors) {
		this.objectErrors = objectErrors;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	
	public void addError(FieldMessage error) {
		if(this.objectErrors == null) {
			this.objectErrors = new ArrayList<FieldMessage>();
		}
		this.objectErrors.add(error);
		this.hasError = true;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void initMessages(RequestContext context, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for(FieldError error : bindingResult.getFieldErrors()) {
				FieldMessage field = new FieldMessage(error.getField(), error.getRejectedValue(), context.getMessage(error, true));
				addError(field);
				sb.append(field.getMessage()).append("\n");
			}
			this.message = sb.toString();
		}
	}
	
}
