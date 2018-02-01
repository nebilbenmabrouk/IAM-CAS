package org.ow2.proactive.iam.bootstrap;

import java.io.*;

import org.ow2.proactive.iam.identity.provisioning.LDAPIdentityManagement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import org.ow2.proactive.iam.backend.embedded.ldap.EmbeddedLDAPServer;
import org.springframework.core.io.ResourceLoader;

public class LDAPBootstrap {

    private ResourceLoader resourceLoader;

    private static final String ldifFile = "classpath:/config/iam/identities.ldif";

    public static void loadLDIF(){

        ApplicationContext appContext =
                new ClassPathXmlApplicationContext();

        Resource ldifResource =
                appContext.getResource(ldifFile);

        try{

            BufferedInputStream bis = new BufferedInputStream(ldifResource.getInputStream());
            LDAPIdentityManagement.importLdif(bis);

        }catch(Exception e){
            System.err.println(e.getMessage());
            //e.printStackTrace();
        }

    }

    public static void startServer(){
        try {

            EmbeddedLDAPServer.INSTANCE.startLDAPServer();

        } catch (Exception e) {
            //log.error(e.getMessage());
            System.err.println(e.getMessage());
            //e.printStackTrace();
        }
    }

}
