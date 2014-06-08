package sage.domain.repository.nosql;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;

import sage.web.context.JsonUtil;

import com.couchbase.client.CouchbaseClient;

public abstract class BaseCouchbaseRepository<T> {
  protected Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  
  @Autowired
  protected CouchbaseClient client;
  
  public T get(String key) {
    String json = (String) client.get(key);
    return JsonUtil.object(json, entityClass);
  }
  
  public Future<Boolean> set(String key, T value) {
    return client.set(key, JsonUtil.json(value));
  }
  
}
