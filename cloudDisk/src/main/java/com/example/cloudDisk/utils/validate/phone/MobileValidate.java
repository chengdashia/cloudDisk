package com.example.cloudDisk.utils.validate.phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 成大事
 * @date 2022/3/15 18:47
 */

public class MobileValidate implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone constraintAnnotation) {}


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0,1,3,6-8])|(18[0-9])|(19[8,9])|(166))[0-9]{8}$");
        Matcher m = p.matcher(value);
        return m.matches();
    }

}
