package org.ow2.proactive.iam.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.ow2.proactive.iam.bootstrap.LDAPBootstrap;
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

@Configuration("IAMBootstrapConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
@PropertySource("classpath:/iam/iam.properties")
@ConfigurationProperties
@Slf4j
public class IAMBootstrapConfiguration {

    private static final String ldapBackend = "embeddedLDAP";

    @Autowired
    private CasConfigurationProperties casProperties;

    //@Value("${iam.backend:embeddedLDAP}")
    @Value("${iam.backend}")
    private  String backend;



    public String getBackend() {
        return backend;
    }

    public void setBackend(String backend) {
        this.backend = backend;
    }


    @PostConstruct
    public void boot(){
        if (getBackend().equals(ldapBackend)){
            LDAPBootstrap.boot();
        }
    }

    @PreDestroy
    public void stop(){
        if (getBackend().equals(ldapBackend)){
            //Stop LDAP Server
        }
    }
}