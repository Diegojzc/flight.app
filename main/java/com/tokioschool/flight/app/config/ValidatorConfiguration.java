package com.tokioschool.flight.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration

public class ValidatorConfiguration {

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
      /*  localValidatorFactoryBean
                        .getValidationPropertyMap()
                        .put("hibernate.validator.fail_fast", "true");*/
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }
}
