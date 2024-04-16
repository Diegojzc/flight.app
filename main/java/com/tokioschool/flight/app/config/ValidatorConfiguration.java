package com.tokioschool.flight.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@RequiredArgsConstructor
public class ValidatorConfiguration {

    private final MessageSource messageSource;
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
      /*  localValidatorFactoryBean
                        .getValidationPropertyMap()
                        .put("hibernate.validator.fail_fast", "true");*/
        localValidatorFactoryBean.afterPropertiesSet();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }
}
