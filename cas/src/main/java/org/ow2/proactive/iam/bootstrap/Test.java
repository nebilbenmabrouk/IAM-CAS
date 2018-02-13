package org.ow2.proactive.iam.bootstrap;

import java.io.BufferedInputStream;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.ow2.proactive.iam.backend.embedded.ldap.EmbeddedLDAPServer;
import org.ow2.proactive.iam.identity.provisioning.LDAPIdentityManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    private final static Logger logger = LoggerFactory.getLogger(Class.class);

    private static Hashtable<String, String> env = new Hashtable<String, String>();

    private static String ldapHost = "localhost";

    private static int ldapPort = 11389;

    private static String securityPrincipal = "uid=admin,ou=system";

    private static String securityCredentials = "secret";

    private String rootDn = "dc=activeeon,dc=com";

    private static String usersBase = "ou=users,dc=activeeon,dc=com";

    private String rolesBase = "ou=roles,dc=activeeon,dc=com";

    private static String encryptionAlgorithm = "SHA";

    public static void main(String [] args){
        try {

            EmbeddedLDAPServer.INSTANCE.startLDAPServer();

            BufferedInputStream bis = new BufferedInputStream(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("config/iam/identities.ldif"));

            LDAPIdentityManagement.importLdif(bis);

            init();
            search("tobias");

            //EmbeddedLDAPServer.INSTANCE.shutdownLDAPServer();

           /* env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.PROVIDER_URL, "ldap://" + "ldap.forumsys.com"   + ":" + "389");
            env.put(Context.SECURITY_PRINCIPAL, "cn=read-only-admin,dc=example,dc=com");
            env.put(Context.SECURITY_CREDENTIALS, "password");

            String id = "einstein";

            DirContext ctx = new InitialDirContext(env);

            SearchControls sc = new SearchControls();
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String filter = "uid="+id;

            NamingEnumeration results = ctx.search("dc=example,dc=com", filter, sc);


            while (results.hasMore()) {
                SearchResult sr = (SearchResult) results.next();

                System.out.println(sr.toString());

                Attributes attrs = sr.getAttributes();

                Attribute attr = attrs.get("uid");
                if (attr != null)
                    System.out.println("Entry found: " + attr.get());
            }
            ctx.close();*/


        } catch (Exception e) {
            //log.error(e.getMessage());
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public static void init() {
        try {
            // configure ldap context
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://" + ldapHost + ":" + ldapPort);
            //env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
            env.put(Context.SECURITY_CREDENTIALS, securityCredentials);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static boolean search(String id) {
        try {

            DirContext ctx = new InitialDirContext(env);

            SearchControls sc = new SearchControls();
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String filter = "uid="+id;

            NamingEnumeration results = ctx.search(usersBase, filter, sc);

            while (results.hasMore()) {
                SearchResult sr = (SearchResult) results.next();
                Attributes attrs = sr.getAttributes();

                Attribute attr = attrs.get("uid");
                if (attr != null)
                    System.out.println("Entry found: " + attr.get());
            }
            ctx.close();

            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

}
