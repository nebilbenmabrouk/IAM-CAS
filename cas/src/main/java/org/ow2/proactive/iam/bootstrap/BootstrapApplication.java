package org.ow2.proactive.iam.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.CasEmbeddedContainerUtils;
import org.apereo.cas.web.CasWebApplicationContext;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.actuate.autoconfigure.MetricsDropwizardAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.Map;
import lombok.NoArgsConstructor;

@EnableDiscoveryClient
@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class, JerseyAutoConfiguration.class,
        GroovyTemplateAutoConfiguration.class, JmxAutoConfiguration.class, DataSourceAutoConfiguration.class, RedisAutoConfiguration.class,
        MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, CassandraAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class, MetricsDropwizardAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class })
@EnableConfigurationProperties(CasConfigurationProperties.class)
@EnableAsync
@EnableTransactionManagement(proxyTargetClass = true)
@EnableScheduling
@Slf4j
@NoArgsConstructor
@Configuration
public class BootstrapApplication {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapApplication.class);


    /**
     * Main entry point of the CAS web application.
     * @param args the args
     */
    public static void main(final String[] args) throws Exception {

        //IAM bootstrap (start identity backend, load identities, ..)
        logger.info("ProActive IAM bootstrap");
        IAMBootstrapConfiguration.boot();

        // CAS bootstrap
        final Map<String, Object> properties = CasEmbeddedContainerUtils.getRuntimeProperties(Boolean.TRUE);
        final Banner banner = CasEmbeddedContainerUtils.getCasBannerInstance();
        new SpringApplicationBuilder(BootstrapApplication.class).banner(banner).web(true).properties(properties).logStartupInfo(true).contextClass(CasWebApplicationContext.class).run(args);

    }
}