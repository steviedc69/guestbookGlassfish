/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Steven
 */
public class MessageValidator implements ConstraintValidator<MessageValid, String>{

    @Override
    public void initialize(MessageValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value !=null&&value.length()<=500;
    }
    
    
}
