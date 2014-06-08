package sage.domain.nosql;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.couchbase.client.CouchbaseClient;

@Component
public class Couchbase {
  CouchbaseClient client;
  
  public Couchbase() throws IOException {
    client = new CouchbaseClient(Arrays.asList(URI.create("http://127.0.0.1:8091/pools")), "default", "");
  }
  
  @Bean
  public CouchbaseClient client() {
    return client;
  }
  
  @PreDestroy
  public void shutdown() {
    client.shutdown(10, TimeUnit.SECONDS);
  }
}
