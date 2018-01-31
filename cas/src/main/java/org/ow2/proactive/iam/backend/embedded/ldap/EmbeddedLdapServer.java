package org.ow2.proactive.iam.backend.embedded.ldap;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.directory.server.core.api.DirectoryService;
import org.apache.directory.server.core.api.InstanceLayout;
import org.apache.directory.server.core.factory.DefaultDirectoryServiceFactory;
import org.apache.directory.server.core.partition.impl.avl.AvlPartition;
import org.apache.directory.server.ldap.LdapServer;
import org.apache.directory.server.protocol.shared.store.LdifFileLoader;
import org.apache.directory.server.protocol.shared.transport.TcpTransport;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.api.ldap.model.name.Dn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum EmbeddedLdapServer {

    //singleton instance of EmbeddedLDAPServer
    INSTANCE;

    private static final String INSTANCE_NAME = "ProActiveEmbeddedLDAP";
    private static final String INSTANCE_PATH = "/tmp/ProActiveEmbeddedLDAP";
    private static final String BASE_DN = "dc=activeeon,dc=com";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private DirectoryService directoryService;
    private LdapServer ldapService;

    private String host;
    private Integer port;

    /*public EmbeddedLdapServer(final String host, final Integer port) {
        this.host = host;
        this.port = port;

        try {
            init();
        } catch (IOException e) {
            log.error("IOException while initializing EmbeddedLdapServer", e);
        } catch (LdapException e) {
            log.error("LdapException while initializing EmbeddedLdapServer", e);
        } catch (NamingException e) {
            log.error("NamingException while initializing EmbeddedLdapServer",
                    e);
        } catch (Exception e) {
            log.error("Exception while initializing EmbeddedLdapServer", e);
        }
    }*/

    private void init() throws Exception, IOException, LdapException,
            NamingException {

        try {
            DefaultDirectoryServiceFactory factory = new DefaultDirectoryServiceFactory();
            factory.init(INSTANCE_NAME);

            directoryService = factory.getDirectoryService();
            directoryService.getChangeLog().setEnabled(false);
            directoryService.setShutdownHookEnabled(true);

            InstanceLayout il = new InstanceLayout(INSTANCE_PATH);
            directoryService.setInstanceLayout(il);

            AvlPartition partition = new AvlPartition(
                    directoryService.getSchemaManager());
            partition.setId(INSTANCE_NAME);
            partition.setSuffixDn(new Dn(directoryService.getSchemaManager(),
                    BASE_DN));
            partition.initialize();
            directoryService.addPartition(partition);

            ldapService = new LdapServer();
            ldapService.setTransports(new TcpTransport(host, port));
            ldapService.setDirectoryService(directoryService);
        } catch (IOException e) {
            log.error("IOException while initializing EmbeddedLdapServer", e);
        } catch (LdapException e) {
            log.error("LdapException while initializing EmbeddedLdapServer", e);
        } catch (NamingException e) {
            log.error("NamingException while initializing EmbeddedLdapServer",
                    e);
        } catch (Exception e) {
            log.error("Exception while initializing EmbeddedLdapServer", e);
        }
    }

    public void start(String host, Integer port) throws Exception {

        if (ldapService.isStarted()) {
            throw new IllegalStateException("Service already running");
        }

        this.host = host;
        this.port = port;

        init();

        directoryService.startup();
        ldapService.start();
    }

    public void stop() throws Exception {

        if (!ldapService.isStarted()) {
            throw new IllegalStateException("Service is not running");
        }

        ldapService.stop();
        directoryService.shutdown();
    }

    public void applyLdif(final File ldifFile) throws Exception {
        new LdifFileLoader(directoryService.getAdminSession(), ldifFile, null)
                .execute();
    }

    public void createEntry(final String id,
                            final Map<String, String[]> attributes) throws LdapException,
            LdapInvalidDnException {

        if (!ldapService.isStarted()) {
            throw new IllegalStateException("Service is not running");
        }

        Dn dn = new Dn(directoryService.getSchemaManager(), id);
        if (!directoryService.getAdminSession().exists(dn)) {
            Entry entry = directoryService.newEntry(dn);
            for (String attributeId : attributes.keySet()) {
                entry.add(attributeId, attributes.get(attributeId));
            }
            directoryService.getAdminSession().add(entry);
        }
    }
}