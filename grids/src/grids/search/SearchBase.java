package grids.search;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;

import static org.elasticsearch.index.query.QueryBuilders.*;
import grids.transfer.BlogData;
import grids.transfer.TweetCard;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SearchBase {
	public static final String BD = "bd";
	public static final String TC = "tc";
	
	private static final String INDEX = "sage";
	private static final Map<Class<?>, String> typeMap = new HashMap<>();
	static {
		typeMap.put(BlogData.class, BD);
		typeMap.put(TweetCard.class, TC);
	}
	
	private Client client;
	private ObjectMapper om = new ObjectMapper();
	
	SearchBase() {
		client = new TransportClient()
		  .addTransportAddresses(new InetSocketTransportAddress("localhost", 9300));
		putMapping(getMappingSource("bd-mapping.json"), BD);
		putMapping(getMappingSource("tc-mapping.json"), TC);
	}
	
	private String getMappingSource(String filename) {
		InputStream in = getClass().getClassLoader()
				.getResourceAsStream(filename);
		byte[] buf = new byte[100];
		StringBuilder sb = new StringBuilder();
		try {
			while (in.available() > 0) {
				sb.append(in.read(buf));
			}
			return sb.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private void putMapping(String mappingSource, String type) {
		client.admin().indices().preparePutMapping(INDEX)
			.setType(type).setSource(mappingSource).execute();
	}
	
	@PreDestroy
	void shutdown() {
//		node.close();
	}
	
	/**
	 * Only accepts transfer object
	 * @param id key
	 * @param object a transfer object
	 */
	public void index(long id, Object object) {
		if (object == null) {
			throw new IllegalArgumentException("object is null");
		}
		String json;
		try {
			json = om.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		client.prepareIndex(INDEX, mapType(object.getClass()), String.valueOf(id))
			.setSource(json)
			.execute();
	}
	
	public void delete(Class<?> clazz, long id) {
		client.prepareDelete().setIndex(INDEX)
			.setType(mapType(clazz)).setId(String.valueOf(id))
			.execute();
	}
	
	public SearchResponse search(String q ) {
		return client.prepareSearch(INDEX)
				.setTypes("bd", "tc")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(queryString(q))
				.setFrom(0).setSize(60).setExplain(true)
				.execute()
				.actionGet();
	}
	
	private static String mapType(Class<?> clazz) {
		String type = typeMap.get(clazz);
		if (type == null) {
			throw new IllegalArgumentException(
					clazz.getName() + " is not indexable!");
		}
		return type;
	}
}
