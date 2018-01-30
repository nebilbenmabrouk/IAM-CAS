package org.ow2.proactive.iam.bootstrap;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.PropertySource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.apereo.cas.configuration.CasConfigurationProperties;

//@Component
//@Scope(value = "singleton")
//@Order(1)

//@Configuration("IAMPropertiesBean")
//@EnableConfigurationProperties(CasConfigurationProperties.class)
@PropertySource("classpath:/iam/iam.properties")
@ConfigurationProperties
//@ConfigurationProperties(prefix = "iam", ignoreUnknownFields = false)
//@ConfigurationProperties(value = "cas")
@Slf4j
@Getter
@Setter
public class IAMPropertiesBean  {

    //@Autowired
    //private CasConfigurationProperties casProperties;

    @Value("${iam.backend:embeddedLDAP}")
    private  String backend;

    /*public IAMPropertiesBean() {
        System.out.println("backend: "+getBackend());
    }*/

    public String getBackend() {
        return backend;
    }

    public void setBackend(String backend) {
        this.backend = backend;
    }

}
