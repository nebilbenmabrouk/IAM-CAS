package main.java.org.ow2.proactive.iam.bootstrap;

import org.ow2.proactive.iam.backend.embedded.ldap.EmbeddedLDAPServer;
import org.ow2.proactive.iam.identity.provisioning.LocalLDAPIdentityManagement;

public class LDAPBootstrap {

    public static void boot(){
        try {

            EmbeddedLDAPServer.INSTANCE.startLDAPServer();

            //log.debug("Loading identities");
            //LocalLDAPIdentityManagement idm = new LocalLDAPIdentityManagement(iamProperties);
            //idm.importLdif(ldifFile);
            //}

        } catch (Exception e) {
            //log.error(e.getMessage());
            System.err.println(e.getMessage());
        }
    }

}
