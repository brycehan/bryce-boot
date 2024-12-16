package com.brycehan.boot.common.base.validator;

import cn.hutool.core.util.StrUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * 集合中元素不能为空校验器
 *
 * @author Bryce Han
 * @since 2023/5/8
 */
public class NotBlankElementsValidator implements ConstraintValidator<NotBlankElements, List<CharSequence>> {

    @Override
    public boolean isValid(List<CharSequence> list, ConstraintValidatorContext context) {
        if (list == null) {
            return true; // 如果列表本身为 null，则不进行进一步检查
        }
        for (CharSequence element : list) {
            if (StrUtil.isBlank(element)) {
                return false;
            }
        }
        return true;
    }
}
