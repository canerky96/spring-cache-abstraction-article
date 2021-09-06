package com.kaya.springcachedemo;

import com.couchbase.client.java.Cluster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.cache.CouchbaseCacheManager;

@Configuration
public class CacheConfig {

  @Value("${couchbase.url}")
  private String url;

  @Value("${couchbase.username}")
  private String username;

  @Value("${couchbase.password}")
  private String password;

  @Value("${couchbase.bucket}")
  private String bucket;

  @Bean(destroyMethod = "disconnect")
  public Cluster cluster() {
    return Cluster.connect(url, username, password);
  }

  @Bean
  public CacheManager cacheManager() {
    return CouchbaseCacheManager.create(new SimpleCouchbaseClientFactory(cluster(), bucket, null));
  }
}
