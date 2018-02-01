/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive.iam.backend.embedded.ldap;

//import org.apache.directory.server.core.api.*;
import org.apache.directory.server.core.api.DirectoryService;
import org.apache.directory.server.core.api.InstanceLayout;
import org.apache.directory.server.core.factory.DefaultDirectoryServiceFactory;
import org.apache.directory.server.core.factory.DirectoryServiceFactory;
import org.apache.directory.server.ldap.LdapServer;
import org.apache.directory.server.protocol.shared.transport.TcpTransport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public enum EmbeddedLDAPServer {

    //singleton instance of EmbeddedLDAPServer
    INSTANCE;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final DirectoryServiceFactory lFactory = new DefaultDirectoryServiceFactory();
    private static DirectoryService lService;
    private static LdapServer lServer = new LdapServer();

    private static String host = "localhost";
    private static int port = 11389;

    public void startLDAPServer() throws Exception {

            lFactory.init("ProActiveEmbeddedLDAP");
            logger.debug("Factory initialized");

            lService = lFactory.getDirectoryService();
            lService.getChangeLog().setEnabled(false);
            lService.setShutdownHookEnabled(true);

            InstanceLayout il = new InstanceLayout("/tmp/ProActiveEmbeddedLDAP");
            lService.setInstanceLayout(il);

            lServer.setTransports(new TcpTransport("localhost", port));
            lServer.setDirectoryService(lService);
            logger.debug("LDAP Server initialized");

            if (lServer.isStarted()){
                logger.warn("LDAP Server already started !!");
                System.out.println("LDAP Server already started !!");
            } else {

                lService.startup();
                lServer.start();

                logger.info("LDAP Server started");
                System.out.println("LDAP Server started");
            }
    }

    public void shutdownLDAPServer() throws Exception {
        lServer.stop();
        lService.shutdown();
        logger.info("LDAP Server stopped");
        System.out.println("LDAP Server stopped");
    }

    public DirectoryService getlService() {
        return lService;
    }

    public void setlService(DirectoryService lService) {
        EmbeddedLDAPServer.INSTANCE.lService = lService;
    }

    public LdapServer getlServer() {
        return lServer;
    }

    public void setlServer(LdapServer lServer) {
        EmbeddedLDAPServer.INSTANCE.lServer = lServer;
    }
}
