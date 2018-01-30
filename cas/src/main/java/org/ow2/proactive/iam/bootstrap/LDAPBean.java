package org.ow2.proactive.iam.bootstrap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;

import org.ow2.proactive.iam.backend.embedded.ldap.EmbeddedLDAPServer;
import org.ow2.proactive.iam.identity.provisioning.LocalLDAPIdentityManagement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Scope(value = "singleton")
//@Order(2)
@Slf4j
public class LDAPBean  implements InitializingBean, DisposableBean {

    //private final String iamProperties = "/iam/iam.properties";
    //private final String ldifFile = "/iam/identities.ldif";

    //private org.ow2.proactive.iam.util.PropertiesHelper propHelper = new org.ow2.proactive.iam.util.PropertiesHelper(iamProperties);
    //private String backend = propHelper.getValueAsString("iam.backend", "embeddedLDAP");

    public LDAPBean() {

        System.out.println("Initialize LDAP 1");

        //log.info("Initialize LDAP 1");
        //log.info("Initialize LDAP 1");
        //System.out.println("Initialize LDAP 1");





        try {

            //if (backend.equals("embeddedLDAP")) {

                //log.info("Starting embedded LDAP server");
                //EmbeddedLDAPServer.INSTANCE.startLDAPServer();

                //log.debug("Loading identities");
                //LocalLDAPIdentityManagement idm = new LocalLDAPIdentityManagement(iamProperties);
                //idm.importLdif(ldifFile);
            //}

        } catch (Exception e) {
            //log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Spring Container is destroy! Customer clean up");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //log.info("Initialize LDAP 2");
        System.out.println("Initialize LDAP 2");
    }

    @PostConstruct
    public void postConstruct() {
        //log.info("Initialize LDAP 3");
        System.out.println("Initialize LDAP 3");
    }

    public void start(){

        try {

            //if (backend.equals("embeddedLDAP")) {

            //log.info("Starting embedded LDAP server");
            EmbeddedLDAPServer.INSTANCE.startLDAPServer();

            //log.debug("Loading identities");
            //LocalLDAPIdentityManagement idm = new LocalLDAPIdentityManagement(iamProperties);
            //idm.importLdif(ldifFile);
            //}

        } catch (Exception e) {
            //log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
