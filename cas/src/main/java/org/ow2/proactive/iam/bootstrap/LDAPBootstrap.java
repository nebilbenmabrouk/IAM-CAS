package org.ow2.proactive.iam.bootstrap;

import java.io.BufferedInputStream;

import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.server.core.api.DirectoryService;
import org.ow2.proactive.iam.backend.embedded.ldap.EmbeddedLdapServer;
import org.ow2.proactive.iam.backend.embedded.ldap.LdapUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LDAPBootstrap {

    public static void boot(String host, int port, String baseDn, String identitiesLDIF) throws Exception {
        DirectoryService directoryService = startLDAP(host, port);
        addIdentities(directoryService,baseDn,identitiesLDIF);
    }

    public static void shutdown() throws Exception {
        EmbeddedLdapServer.INSTANCE.stop();
    }

    private static DirectoryService startLDAP(String host, int port) throws Exception{
        EmbeddedLdapServer.INSTANCE.start(host, port);
        return EmbeddedLdapServer.INSTANCE.getdirectoryService();
    }

    private static void addIdentities(DirectoryService directoryService, String baseDn, String identitiesLDIF) throws Exception {
        LdapUtil ldapUtil = new LdapUtil(directoryService);
        ldapUtil.addRoleAttribute();
        ldapUtil.addPartition(new Dn(baseDn));

        ApplicationContext appContext =
                new ClassPathXmlApplicationContext();

        BufferedInputStream bis =  new BufferedInputStream( appContext.getResource(identitiesLDIF).getInputStream());

        ldapUtil.importLdif(bis);
    }


    /*private ResourceLoader resourceLoader;

    private static final String ldifFile = "classpath:/config/iam/identities.ldif";

    public static void loadLDIF(){

        ApplicationContext appContext =
                new ClassPathXmlApplicationContext();

        Resource ldifResource =
                appContext.getResource(ldifFile);

        try{

            BufferedInputStream bis = new BufferedInputStream(ldifResource.getInputStream());

            LdapUtil ldapUtil = new LdapUtil(EmbeddedLDAPServer.INSTANCE.getdirectoryService());
            ldapUtil.importLdif(bis);
            //LDAPIdentityManagement.importLdif(bis);

        }catch(Exception e){
            System.err.println(e.getMessage());
            //e.printStackTrace();
        }

    }*/

}
