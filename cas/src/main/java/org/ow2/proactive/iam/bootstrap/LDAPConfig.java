package org.ow2.proactive.iam.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration("LDAPConfig")
@EnableConfigurationProperties(CasConfigurationProperties.class)
@PropertySource("classpath:/iam/iam.properties")
@ConfigurationProperties
@Slf4j
public class LDAPConfig {

    @Autowired
    private CasConfigurationProperties casProperties;

    //@Value("${iam.backend:embeddedLDAP}")
    @Value("${iam.backend}")
    private  String backend;

    //@Autowired
    //private IAMPropertiesBean propertiesBean;

    @Autowired
    @Qualifier("ldapBean")
    private LDAPBean ldapBean;

    /*
    @Bean
    public IAMPropertiesBean propertiesBean() {
        System.out.println("backend: "+getBackend());
        return new IAMPropertiesBean();
    }

    @Bean
    public LDAPRunner ldapRunner() {
        return new LDAPRunner();
    }
    */

    @Bean
    public LDAPBean ldapBean() {
        return new LDAPBean();
    }

    public String getBackend() {
        return backend;
    }

    public void setBackend(String backend) {
        this.backend = backend;
    }

    @PostConstruct
    public void start(){
        System.out.println("backend: "+getBackend());
        if (getBackend().equals("embeddedLDAP")) ldapBean.start();
    }
}