package org.aviva.technicaltest.service;

  
  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;


import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;


import org.apache.felix.scr.annotations.Reference;

import javax.jcr.Session;


//QueryBuilder APIs
import com.day.cq.search.QueryBuilder; 
import com.day.cq.search.Query; 
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.result.SearchResult;
import com.day.cq.search.result.Hit; 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.HashMap;



//Sling Imports
import org.apache.sling.api.resource.ResourceResolverFactory ; 
import org.apache.sling.api.resource.ResourceResolver; 
import org.apache.sling.api.resource.Resource; 

import com.day.cq.search.Query;
import com.day.cq.wcm.api.Page; 
 
//This is a component so it can provide or consume services
@Component
 
  
@Service(TestService.class)
public class TestService {
 
//Inject a Sling ResourceResolverFactory
@Reference
private ResourceResolverFactory resolverFactory;
@Reference
private QueryBuilder builder;

private static final Logger LOGGER = LoggerFactory.getLogger(TestService.class);	

	public List<String> getTaggedPages(String tagGroup, Boolean searchAllTags) {
		List<String> searchResultPage =  new ArrayList<String>();
		try{
			LOGGER.info("gettingTaggedPages");
			Map<String, String> map = new HashMap<String, String>();
		    //query parameter
		    map.put("type", "cq:Page");
		    //return all results instead of default 10
		    map.put("p.limit","-1");
		    //return all result whose last modified date is 
            map.put("group.1_relativedaterange.property","jcr:content/cq:lastModified");
            map.put("group.1_relativedatarange.lowerBound","-5d");
            map.put("group.1_relativedatarange.upperBound","0");
            if(searchAllTags){
            map.put("property.and","true");
            } else {
            map.put("property.and","false");
            }
            map.put("property","jcr:content/cq:tags");
            String[] tagArray =  tagGroup.split(",");
            for(int i=0; i<tagArray.length; i++){
            	int propertyValue = i+ 1;
            	String propertyName =  "property." + propertyValue + "_value";
            	LOGGER.info("searchMapPropertyValue:"+propertyName);
            	LOGGER.info("tagName:"+tagArray[i]);
            	map.put(propertyName,tagArray[i]);
            }
            Map<String,Object> param = new HashMap<String, Object>();
            param.put(ResourceResolverFactory.SUBSERVICE, "taggedlist");
			ResourceResolver resourceResolver  =  resolverFactory.getServiceResourceResolver(param);
			Session session = resourceResolver.adaptTo(Session.class);
		    Query query = builder.createQuery(PredicateGroup.create(map),session);
		    SearchResult queryResult = query.getResult();
		    String hitPath = "";
			for (Hit hit : queryResult.getHits()){
				try {
					hitPath = hit.getPath() + ".html";
					searchResultPage.add(hitPath);
					LOGGER.info("adding page"+hitPath);
				} catch (Exception e){
					LOGGER.warn("querybuilder error:"+e);
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.info("TestService Error"+ e);
		}
         
			return searchResultPage;
    }
}


