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

    private static Pattern pattern = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$");

    @Override
    public void initialize(Phone constraintAnnotation) {}


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Matcher m = pattern.matcher(value);
        return m.matches();
    }

}
