package org.ow2.proactive.iam.bootstrap;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.server.core.api.DirectoryService;
import org.ow2.proactive.iam.backend.embedded.ldap.EmbeddedLdapServer;
import org.ow2.proactive.iam.backend.embedded.ldap.LdapUtil;
import org.ow2.proactive.iam.util.PropertiesHelper;
import org.ow2.proactive.iam.util.PropertyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LDAPBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(LDAPBootstrap.class);
    private static final String propertiesFile = "classpath:/config/iam/identities.ldif";

    private static String host;
    private static int port;
    private static String baseDn;
    private static String identitiesLDIF;

    public static void boot() throws Exception {
        loadProperties();
        DirectoryService directoryService = startLDAP(host, port);
        addIdentities(directoryService,baseDn,identitiesLDIF);
    }

    public static void shutdown() throws Exception {
        EmbeddedLdapServer.INSTANCE.stop();
    }

    private static DirectoryService startLDAP(String host, int port) throws Exception{
        logger.info("Starting an embedded LDAP server");
        EmbeddedLdapServer.INSTANCE.start(host, port);
        return EmbeddedLdapServer.INSTANCE.getdirectoryService();
    }

    private static void addIdentities(DirectoryService directoryService, String baseDn, String identitiesLDIF) throws Exception {
        logger.info("Loading identities");
        LdapUtil ldapUtil = new LdapUtil(directoryService);
        ldapUtil.addRoleAttribute();
        ldapUtil.addPartition(new Dn(baseDn));

        ApplicationContext appContext =
                new ClassPathXmlApplicationContext();

        BufferedInputStream bis =  new BufferedInputStream( appContext.getResource(identitiesLDIF).getInputStream());

        ldapUtil.importLdif(bis);
    }

    private static void loadProperties() throws IOException {
        ApplicationContext appContext =
                new ClassPathXmlApplicationContext();

        PropertiesHelper propertiesHelper = new PropertiesHelper(propertiesFile);

        host = propertiesHelper.getValueAsString("ldap.host","localhost");
        port= propertiesHelper.getValueAsInt("ldap.port", PropertyType.INTEGER,11389);
        baseDn =  propertiesHelper.getValueAsString("dn.base","dc=activeeon,dc=com");
        identitiesLDIF = propertiesHelper.getValueAsString("identities.ldif","classpath:/config/iam/identities.ldif");

    }
}
