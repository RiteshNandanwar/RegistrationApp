package com.ritesh.springsecurity.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object>
{
	private String firstField;
	private String secondField;
	private String message;
	
	
	
	@Override
	public void initialize(FieldMatch constraintAnnotation) {
		firstField = constraintAnnotation.first();
		secondField = constraintAnnotation.second();
		message = constraintAnnotation.message();
	}



	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valid = true;
		try
		{
			final Object firstObject = new BeanWrapperImpl(value).getPropertyValue(firstField);
			final Object secondObject = new BeanWrapperImpl(value).getPropertyValue(firstField);
			
			valid = firstObject==null && secondObject==null || firstObject !=null && firstObject.equals(secondObject);
		}
		catch(Exception e)
		{
			
		}
		if(!valid)
		{
			 context.buildConstraintViolationWithTemplate(message)
             .addPropertyNode(firstField)
             .addConstraintViolation()
             .disableDefaultConstraintViolation();
		}
		return valid;
	}
	
	
}
