package sage.domain.repository.nosql;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import sage.entity.nosql.IdAble;
import sage.web.context.JsonUtil;

import com.couchbase.client.CouchbaseClient;

public abstract class BaseCouchbaseRepository<T extends IdAble> {
  protected Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  
  @Autowired
  protected CouchbaseClient client;
  
  public T get(String key) {
    Assert.notNull(key);
    String json = (String) client.get(key);
    if (json ==null) {
      return null;
    }
    T result = JsonUtil.object(json, entityClass);
    result.setId(key);
    return result;
  }
  
  public Future<Boolean> add(String key, T value) {
    Assert.notNull(key);
    Assert.notNull(value);
    return client.add(key, JsonUtil.json(value));
  }

  public Future<Boolean> set(String key, T value) {
    Assert.notNull(key);
    Assert.notNull(value);
    return client.set(key, JsonUtil.json(value));
  }
}
