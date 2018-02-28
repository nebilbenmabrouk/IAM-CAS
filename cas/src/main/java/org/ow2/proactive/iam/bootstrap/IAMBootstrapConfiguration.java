package org.ow2.proactive.iam.bootstrap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration("iamBootstrap")
@PropertySource("classpath:/config/iam/iam.properties")
@ConfigurationProperties
@Slf4j
@RefreshScope
public class IAMBootstrapConfiguration   {

    private static final String ldapBackend = "embeddedLDAP";

    @Value("${iam.backend}")
    private  String backend;

    @Value("${ldap.host}")
    private  String host;

    @Value("${ldap.port}")
    private  int port;

    @Value("${dn.base}")
    private  String baseDn;

    @Value("${identities.ldif}")
    private  String identitiesLDIF;

    /*public IAMBootstrapConfiguration(){

    }*/

    @PostConstruct
    public void boot() throws Exception{
        System.out.println(this.backend);
        System.out.println(this.host);
        System.out.println(this.port);
        System.out.println(this.identitiesLDIF);

        if (this.backend.equals(ldapBackend)){
            LDAPBootstrap.boot(host,port,baseDn,identitiesLDIF);
        }
    }

    @PreDestroy
    public void stop() throws Exception {
        if (this.backend.equals(ldapBackend)){
            LDAPBootstrap.shutdown();
        }
    }
}