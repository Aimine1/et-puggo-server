package com.etrade.puggo.config;

import javax.sql.DataSource;
import org.jooq.ConnectionProvider;
import org.jooq.ExecuteListenerProvider;
import org.jooq.ExecutorProvider;
import org.jooq.RecordListenerProvider;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordUnmapperProvider;
import org.jooq.TransactionListenerProvider;
import org.jooq.TransactionProvider;
import org.jooq.VisitListenerProvider;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jooq.JooqProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by huangqian on 2020/11/12 4:34 下午
 *
 * @author huangqian
 */
@Configuration
@EnableConfigurationProperties({JooqProperties.class})
public class JooqConfig {

    public JooqConfig() {
    }

    @Bean
    public DefaultDSLContext dslContext(org.jooq.Configuration configuration) {

        Settings settings = new Settings();
        settings.setRenderSchema(false);

        org.jooq.Configuration configuration1 = new DefaultConfiguration()
            .set(configuration.connectionProvider())
            .set(configuration.dialect())
            .set(settings)
            .set(new DefaultExecuteListenerProvider(new JooqSqlLoggerListener()));

        return new DefaultDSLContext(configuration1);
    }

    @Bean
    @ConditionalOnMissingBean({org.jooq.Configuration.class})
    public DefaultConfiguration jooqConfiguration(JooqProperties properties,
        ConnectionProvider connectionProvider,
        DataSource dataSource, ObjectProvider<TransactionProvider> transactionProvider,
        ObjectProvider<RecordMapperProvider> recordMapperProvider,
        ObjectProvider<RecordUnmapperProvider> recordUnmapperProvider,
        ObjectProvider<Settings> settings,
        ObjectProvider<RecordListenerProvider> recordListenerProviders,
        ObjectProvider<ExecuteListenerProvider> executeListenerProviders,
        ObjectProvider<VisitListenerProvider> visitListenerProviders,
        ObjectProvider<TransactionListenerProvider> transactionListenerProviders,
        ObjectProvider<ExecutorProvider> executorProvider) {
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.set(properties.determineSqlDialect(dataSource));
        configuration.set(connectionProvider);
        transactionProvider.ifAvailable(configuration::set);
        recordMapperProvider.ifAvailable(configuration::set);
        recordUnmapperProvider.ifAvailable(configuration::set);
        settings.ifAvailable(configuration::set);
        executorProvider.ifAvailable(configuration::set);
        configuration.set(recordListenerProviders.orderedStream().toArray(
            RecordListenerProvider[]::new));
        configuration.set(executeListenerProviders.orderedStream().toArray(
            ExecuteListenerProvider[]::new));
        configuration.set(visitListenerProviders.orderedStream().toArray(
            VisitListenerProvider[]::new));
        configuration.setTransactionListenerProvider(
            transactionListenerProviders.orderedStream().toArray(
                TransactionListenerProvider[]::new));
        return configuration;
    }
}
