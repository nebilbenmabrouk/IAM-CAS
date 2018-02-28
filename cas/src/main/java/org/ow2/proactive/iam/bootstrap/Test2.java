package org.ow2.proactive.iam.bootstrap;

import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.server.core.api.DirectoryService;
import org.ow2.proactive.iam.backend.embedded.ldap.EmbeddedLDAPServer;
import org.ow2.proactive.iam.backend.embedded.ldap.EmbeddedLdapServer;
import org.ow2.proactive.iam.backend.embedded.ldap.LdapUtil;
import org.ow2.proactive.iam.identity.provisioning.LDAPIdentityManagement;

import java.io.BufferedInputStream;

/**
 * Created by nebil on 28/02/18.
 */
public class Test2 {

    public static void main(String [] args){
        try {

            EmbeddedLdapServer.INSTANCE.start("localhost", 11389);

            DirectoryService directoryService = EmbeddedLdapServer.INSTANCE.getdirectoryService();

            LdapUtil ldapUtil = new LdapUtil(directoryService);
            ldapUtil.addRoleAttribute();
            ldapUtil.addPartition(new Dn("dc=activeeon,dc=com"));

            BufferedInputStream bis = new BufferedInputStream(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("config/iam/identities.ldif"));

            ldapUtil.importLdif(bis);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
