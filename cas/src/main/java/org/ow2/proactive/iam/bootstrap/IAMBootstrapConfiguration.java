package org.ow2.proactive.iam.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.config.LdapAuthenticationConfiguration;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration("IAMBootstrapConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
@PropertySource("classpath:/config/iam/iam.properties")
@ConfigurationProperties
@Slf4j
@RefreshScope
//@AutoConfigureBefore(LdapAuthenticationConfiguration.class)
//@Order(0)
public class IAMBootstrapConfiguration  implements InitializingBean, DisposableBean, PriorityOrdered {

    private static final String ldapBackend = "embeddedLDAP";

    @Autowired
    private CasConfigurationProperties casProperties;

    //@Value("${iam.backend:embeddedLDAP}")
    @Value("${iam.backend}")
    private  String backend;

    public IAMBootstrapConfiguration(){
        /*if (getBackend().equals(ldapBackend)){
            LDAPBootstrap.startServer();
            LDAPBootstrap.loadLDIF();
        }*/
    }

    public String getBackend() {
        return backend;
    }

    public void setBackend(String backend) {
        this.backend = backend;
    }

    @PostConstruct
    public void boot(){
        if (getBackend().equals(ldapBackend)){
           // LDAPBootstrap.startServer();
           // LDAPBootstrap.loadLDIF();
        }
    }

    @PreDestroy
    public void stop(){
        if (getBackend().equals(ldapBackend)){
            //Stop LDAP Server
        }
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("Spring Container is destroy! Customer clean up");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // TO DO

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}