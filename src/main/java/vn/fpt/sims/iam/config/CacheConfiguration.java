package vn.fpt.sims.iam.config;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import org.redisson.Redisson;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private String LANGUAGES = ".languages";

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(ApplicationProperties applicationProperties) {
        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();

        URI redisUri = URI.create(applicationProperties.getCache().getRedis().getServer()[0]);

        Config config = new Config();
        if (applicationProperties.getCache().getRedis().isCluster()) {
            ClusterServersConfig clusterServersConfig = config
                    .useClusterServers()
                    .setMasterConnectionPoolSize(applicationProperties.getCache().getRedis().getConnectionPoolSize())
                    .setMasterConnectionMinimumIdleSize(applicationProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                    .setSubscriptionConnectionPoolSize(applicationProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                    .addNodeAddress(applicationProperties.getCache().getRedis().getServer());

            if (redisUri.getUserInfo() != null) {
                clusterServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            SingleServerConfig singleServerConfig = config
                    .useSingleServer()
                    .setConnectionPoolSize(applicationProperties.getCache().getRedis().getConnectionPoolSize())
                    .setConnectionMinimumIdleSize(applicationProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                    .setSubscriptionConnectionPoolSize(applicationProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                    .setAddress(applicationProperties.getCache().getRedis().getServer()[0]);

            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }
        jcacheConfig.setStatisticsEnabled(true);
        jcacheConfig.setExpiryPolicyFactory(
                CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, applicationProperties.getCache().getRedis().getExpiration()))
        );
        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer(javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        return cm -> {
            createCache(cm, vn.fpt.sims.iam.service.impl.GroupServiceImpl.class.getName(), jcacheConfiguration);
            createCache(cm, vn.fpt.sims.iam.service.impl.GroupServiceImpl.class.getName() + LANGUAGES, jcacheConfiguration);
            createCache(cm, vn.fpt.sims.iam.service.impl.RoleServiceImpl.class.getName(), jcacheConfiguration);
            createCache(cm, vn.fpt.sims.iam.service.impl.RoleServiceImpl.class.getName() + LANGUAGES, jcacheConfiguration);
            createCache(cm, vn.fpt.sims.iam.service.impl.UserServiceImpl.class.getName(), jcacheConfiguration);
            createCache(cm, vn.fpt.sims.iam.service.impl.UserServiceImpl.class.getName() + LANGUAGES, jcacheConfiguration);
        };
    }

    private void createCache(
            javax.cache.CacheManager cm,
            String cacheName,
            javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }
}
