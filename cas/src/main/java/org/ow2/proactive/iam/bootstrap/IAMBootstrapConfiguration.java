package org.ow2.proactive.iam.bootstrap;

import org.ow2.proactive.iam.util.PropertiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;


public class IAMBootstrapConfiguration   {

    private static final Logger logger = LoggerFactory.getLogger(IAMBootstrapConfiguration.class);
    private static final String propertiesFile = "classpath:/config/iam/identities.ldif";
    private static final String embeddedLDAP = "embeddedLDAP";

    private static String backend;

    public static void boot() throws Exception{

        logger.info("Loading IAM configuration");
        loadProperties();

        if (backend.equals(embeddedLDAP)){
            LDAPBootstrap.boot();
        }
    }

   private static void loadProperties() throws IOException{
       ApplicationContext appContext =
               new ClassPathXmlApplicationContext();

       PropertiesHelper propertiesHelper = new PropertiesHelper(propertiesFile);
       backend = propertiesHelper.getValueAsString("iam.backend",embeddedLDAP);

       //System.out.println(backend);
   }
}