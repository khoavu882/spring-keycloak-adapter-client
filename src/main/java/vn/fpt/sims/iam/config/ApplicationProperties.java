package vn.fpt.sims.iam.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to IAM.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@Getter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Cache cache = new Cache();
    private final CorsConfiguration cors = new CorsConfiguration();
    private final ClientApp clientApp = new ClientApp();
    private final Http http = new Http();
    private final Logging logging = new Logging();

    @Getter
    @Setter
    public static class Cache {
        private final Ehcache ehcache = new Ehcache();
        private final Memcached memcached = new Memcached();
        private final Redis redis = new Redis();

        public Cache() {
            // TODO document why this constructor is empty
        }

        @Getter
        @Setter
        public static class Redis {
            private String[] server;
            private int expiration;
            private boolean cluster;
            private int connectionPoolSize;
            private int connectionMinimumIdleSize;
            private int subscriptionConnectionPoolSize;
            private int subscriptionConnectionMinimumIdleSize;

            public Redis() {
                this.server = new String[]{"redis://localhost:6379"};
                this.expiration = 300;
                this.cluster = false;
                this.connectionPoolSize = 64;
                this.connectionMinimumIdleSize = 24;
                this.subscriptionConnectionPoolSize = 50;
                this.subscriptionConnectionMinimumIdleSize = 1;
            }
        }

        @Getter
        @Setter
        public static class Memcached {
            private boolean enabled = false;
            private String servers = "localhost:11211";
            private int expiration = 300;
            private boolean useBinaryProtocol = true;
            private Authentication authentication = new Authentication();

            public Memcached() {
                // TODO document why this constructor is empty
            }

            @Getter
            @Setter
            public static class Authentication {
                private boolean enabled = false;
                private String username;
                private String password;

                public Authentication() {
                    // TODO document why this constructor is empty
                }
            }
        }

        @Getter
        @Setter
        public static class Ehcache {
            private int timeToLiveSeconds = 3600;
            private long maxEntries = 100L;

            public Ehcache() {
                // TODO document why this constructor is empty
            }
        }
    }

    @Getter
    @Setter
    public static class ClientApp {
        private String name = "iamApp";

        public ClientApp() {
            // TODO document why this constructor is empty
        }
    }

    @Getter
    @Setter
    public static class Http {
        private final Cache cache = new Cache();

        public Http() {
            // TODO document why this constructor is empty
        }

        @Getter
        @Setter
        public static class Cache {
            private int timeToLiveInDays = 1461;

            public Cache() {
                // TODO document why this constructor is empty
            }
        }
    }

    @Getter
    @Setter
    public static class Logging {
        private boolean useJsonFormat = false;
        private final Logstash logstash = new Logstash();

        public Logging() {
            // TODO document why this constructor is empty
        }

        @Getter
        @Setter
        public static class Logstash {
            private boolean enabled = false;
            private String host = "localhost";
            private int port = 5000;
            private int ringBufferSize = 512;

            public Logstash() {
                // TODO document why this constructor is empty
            }

            /** @deprecated */
            @Deprecated
            public int getQueueSize() {
                return this.getRingBufferSize();
            }

            /** @deprecated */
            @Deprecated
            public void setQueueSize(int queueSize) {
                this.setRingBufferSize(queueSize);
            }

            public int getRingBufferSize() {
                return this.ringBufferSize;
            }

            public void setRingBufferSize(int ringBufferSize) {
                this.ringBufferSize = ringBufferSize;
            }
        }
    }
}
