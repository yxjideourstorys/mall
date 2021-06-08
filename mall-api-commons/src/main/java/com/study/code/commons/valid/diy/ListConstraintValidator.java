package com.study.code.commons.valid.diy;

import cn.hutool.core.util.ArrayUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class ListConstraintValidator implements ConstraintValidator<ListValue, Integer> {

    Set<Integer> set = new HashSet<>();
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        if (ArrayUtil.isNotEmpty(vals)){
            for (int val : vals) {
                set.add(val);
            }
        }
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return set.contains(integer);
    }
}
