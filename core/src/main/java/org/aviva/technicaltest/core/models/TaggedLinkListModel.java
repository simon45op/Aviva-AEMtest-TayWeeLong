package org.aviva.technicaltest.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.settings.SlingSettingsService;
import org.aviva.technicaltest.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.felix.scr.annotations.Reference;

import java.util.List;

@Model(adaptables=Resource.class)
public class TaggedLinkListModel {

    @Inject
    private SlingSettingsService settings;

    @Inject @Named("sling:resourceType") @Default(values="No resourceType")
    protected String resourceType;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TaggedLinkListModel.class);
    @Inject
    private TestService query;
    
    private List<String>resultList;
    @Inject 
    private String tagsSearched; 
    
    @Inject @Optional
    private String tagsCheckAll;

    @PostConstruct
    protected void init() {
    	initModeList();	
    }

    public void initModeList(){
    	Test Fail Build
    	LOGGER.info("initializing model");
    	LOGGER.info("tagsSearched"+tagsSearched);
    	if(tagsCheckAll == null){
    		resultList = query.getTaggedPages(tagsSearched, false);
    	} else {
    		if(tagsCheckAll.equals("true")){
    			resultList =  query.getTaggedPages(tagsSearched,true);
    		} else {
    			resultList =  query.getTaggedPages(tagsSearched,false);	
    		}
    	}
    }
    public List<String> getResultList() {
        return resultList;
    }
    
    public String getTagsSearched(){
    	return tagsSearched;
    }
    
    public String getCheckAll(){
    	return tagsCheckAll;
    }
}
