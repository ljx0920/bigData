package com.iov.common.framework.rest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author xiuyou.xu
 * @date 2017/7/3
 */

public class MyValidator {
    /**
     * 手动调用bean验证，需要提供validation groups
     *
     * @param entity
     * @param groups
     * @param <T>
     * @return
     */
    public static <T> Set<ConstraintViolation<T>> validate(T entity, Class... groups) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(entity, groups);
    }

    /**
     * 构造bean约束违反信息
     *
     * @param violations
     * @param <T>
     * @return
     */
    public static <T> String buildConstraintViolationMessage(Set<ConstraintViolation<T>> violations) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<T> violation : violations) {
            sb.append("," + violation.getMessage());
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }
}
