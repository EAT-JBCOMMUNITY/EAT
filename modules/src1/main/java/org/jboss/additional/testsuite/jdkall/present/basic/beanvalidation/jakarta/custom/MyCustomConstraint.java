package org.jboss.additional.testsuite.jdkall.present.basic.beanvalidation.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/basic/src/main/java#27.0.0.Alpha4"})
@Target({ ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyCustomConstraint.Validator.class)
public @interface MyCustomConstraint {

    String message() default "{MyCustomConstraint.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<MyCustomConstraint, MyOtherBean> {

        @Override
        public boolean isValid(MyOtherBean value, ConstraintValidatorContext context) {
            return value.getName() != null;
        }
    }
}
