package org.ow2.proactive.iam.bootstrap;

import java.io.BufferedInputStream;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.schema.LdapSyntax;
import org.apache.directory.api.ldap.model.schema.MutableAttributeType;
import org.apache.directory.api.ldap.model.schema.ObjectClass;
import org.apache.directory.api.ldap.model.schema.registries.Schema;
import org.ow2.proactive.iam.backend.embedded.ldap.EmbeddedLDAPServer;
import org.ow2.proactive.iam.backend.embedded.ldap.LdapUtil;
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

    private static String rootDn = "dc=activeeon,dc=com";

    private static String usersBase = "ou=users,dc=activeeon,dc=com";

    private String rolesBase = "ou=roles,dc=activeeon,dc=com";

    private static String encryptionAlgorithm = "SHA";

    public static void main(String [] args){
        try {

            EmbeddedLDAPServer.INSTANCE.startLDAPServer();

            //EmbeddedLDAPServer.INSTANCE.addRoleAttribute();

            init();
            addRoleAttribute();

            BufferedInputStream bis = new BufferedInputStream(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("config/iam/identities.ldif"));

            LdapUtil ldapUtil = new LdapUtil(EmbeddedLDAPServer.INSTANCE.getdirectoryService());
            ldapUtil.importLdif(bis);

            //LDAPIdentityManagement.importLdif(bis);

            search("tobias");

           // EmbeddedLDAPServer.INSTANCE.shutdownLDAPServer();

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

    public static void addRoleAttribute() throws NamingException, LdapException{


        //Attributes attrs = ctx.getAttributes("cn=YourName, ou=People", new String[] { "cn" });
        //Attribute cnAttr = attrs.get("cn");
        // Get its attribute definition
        //DirContext cnSchema = cnAttr.getAttributeDefinition();
        // Get cnSchema's attributes
        //Attributes cnAttrs = cnSchema.getAttributes("");

        /*DirContext ctx = new InitialDirContext(env);

        DirContext schema = ctx.getSchema("");

        Attributes attrs = new BasicAttributes(true);
        attrs.put("NUMERICOID", "2.25.128424792425578037463837247958458780603.4");
        attrs.put("NAME", "role");
        attrs.put("DESC", "role of the user");
        attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.15{1024}");
        attrs.put("SINGLE-VALUE", "true");
        schema.createSubcontext("AttributeDefinition/role", attrs);

        attrs = new BasicAttributes(true);
        attrs.put("NUMERICOID", "2.16.840.1.113719.1.131.6.1.23");
        attrs.put("NAME", "PAUser");
        attrs.put("DESC", "An entry which represents a PAUser");
        attrs.put("SUP", "top");
        attrs.put("AUXILIARY", "true");

        Attribute must = new BasicAttribute("MUST");
        must.add("role");
        attrs.put(must);

        //Attribute may = new BasicAttribute("MAY");
        //may.add("numberOfGuns");
        //may.add("description");
        //attrs.put(may);

        // attrs.put("X-SCHEMA", "sevenSeas");

        schema.createSubcontext("ClassDefinition/PAUser", attrs);*/


        // Add an AttributeType with an ORDERING MatchingRule
        /*MutableAttributeType attributeType = new MutableAttributeType("");
        attributeType.setSyntaxOid( "2.25.128424792425578037463837247958458780603.4" );
        attributeType.setNames( "role" );
        attributeType.setSyntax(new LdapSyntax("1.3.6.1.4.1.1466.115.121.1.15{1024}"));
        attributeType.setSingleValued(true);
        attributeType.setEnabled( true );

        ObjectClass objectClass = new ObjectClass("");
        objectClass.setOid("");
        objectClass.setNames("");
        objectClass.setEnabled(true);

        // Add the AttributeType
        EmbeddedLDAPServer.INSTANCE.getlService().getSchemaManager().add( attributeType );*/

        MutableAttributeType attributeType2 = new MutableAttributeType( "1.1.1.1.1.1" );
        attributeType2.setSyntaxOid( "1.3.6.1.4.1.1466.115.121.1.27" );
        attributeType2.setNames( "testInt" );
        attributeType2.setEqualityOid( "2.5.13.14" );
        attributeType2.setOrderingOid( "2.5.13.15" );
        attributeType2.setSubstringOid( null );
        attributeType2.setEnabled( true );

        EmbeddedLDAPServer.INSTANCE.getdirectoryService().getSchemaManager().add( attributeType2 );



        MutableAttributeType attributeType3 = new MutableAttributeType( "1.1.1.1.1.2" );
        attributeType3.setSyntaxOid( " 1.3.6.1.4.1.1466.115.121.1.44" );
        attributeType3.setNames( "role" );
        //attributeType3.setEqualityOid( "2.5.13.14" );
        //attributeType3.setOrderingOid( "2.5.13.15" );
        //attributeType3.setSubstringOid( null );
        attributeType3.setEnabled( true );

        EmbeddedLDAPServer.INSTANCE.getdirectoryService().getSchemaManager().add( attributeType3 );


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
            //String filter = "objectclass=*";

            NamingEnumeration results = ctx.search(rootDn, filter, sc);

            while (results.hasMore()) {
                SearchResult sr = (SearchResult) results.next();
                Attributes attrs = sr.getAttributes();

               // for (int i=0; i< attrs.size(); i++){
                System.out.println(attrs.get("uid").get());
                System.out.println(attrs.get("sn").get());
                System.out.println(attrs.get("cn").get());
                //}

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
