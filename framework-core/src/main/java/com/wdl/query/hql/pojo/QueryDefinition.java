package com.wdl.query.hql.pojo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import com.wdl.util.ConfigurationUtil;

public class QueryDefinition {

	public static final String DEFAULT_CONFIG_LOCATION = "/query/*.xml";
	static final Logger log = Logger
			.getLogger(QueryDefinition.class);
	private static QueryDefinition instance = new QueryDefinition();
	private final Map<String,Query> querys = new HashMap<String,Query>();
	private final Map<Resource,Long> cachedFiles = new HashMap<Resource,Long>();
	private int cachedFilesCount;

	private QueryDefinition() { 
		cachedFilesCount = 0;   
		Resource resources[] = ConfigurationUtil.getAllResources("query/*.xml");
		if (resources != null) {
			for (int i = 0; i < resources.length; i++) {  
				Resource resource = resources[i];
				log.info("Loading query from {"+resource.toString()+"}" );
				try {
					QueryContext queryContext = (QueryContext) ConfigurationUtil.parseXMLObject(QueryContext.class, resource);
					List list = queryContext.getQueries();
					Iterator it = list.iterator();
					do {  
						if (!it.hasNext())
							break;
						Query query = (Query) it.next();
						Query previous = (Query) querys.put(query.getId(), query);
						if (previous != null) 
							log.error("Duplicated Query register! id[{"+query.getId()+"}], in file {"+resource.toString()+"}");
					} while (true);
					if (resource.getURL().getProtocol().equals("file"))
						cachedFiles.put(resource, Long.valueOf(resource.getFile().lastModified()));
				} catch (IOException e) {
					log.error("Could not load query from {"+resource.toString()+"}, reason:",e);
				} 
			}

			cachedFilesCount = cachedFiles.size();
			log.debug("cached query files: {"+cachedFilesCount+"}");
		}
	}

	public static Query getQueryById(String queryId) {
		instance.update();
		return (Query) instance.querys.get(queryId);
	} 

	public static Map getQuerys() {
		return instance.querys;
	}

	public static QueryDefinition getInstance() {
		return instance;
	} 

	public void update() {
		if (cachedFilesCount > 0) {
			for (Iterator i = cachedFiles.keySet().iterator(); i.hasNext();) {
				Resource resource = (Resource) i.next();
				synchronized (cachedFiles) { 
					try {  
						if (resource.getFile().lastModified() > ((Long) cachedFiles
								.get(resource)).longValue()) {  
							QueryContext queryContext = (QueryContext) ConfigurationUtil.parseXMLObject(QueryContext.class,resource);
							List list = queryContext.getQueries();
							Query query;
							for (Iterator it = list.iterator(); it.hasNext(); log.debug("Update Query id["+query.getId()+"], in {"+resource.toString()+"}")) {
								query = (Query) it.next(); 
								instance.querys.put(query.getId(), query);
							}
							cachedFiles.put(resource, Long.valueOf(resource.getFile().lastModified()));
						}
					} catch (IOException e) {
						log.error("Could not load query from {"+resource.toString()+"}, reason:",e);
					} 
				}
			}

		}
	}

}