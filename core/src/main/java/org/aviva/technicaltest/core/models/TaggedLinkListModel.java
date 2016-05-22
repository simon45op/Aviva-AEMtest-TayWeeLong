package org.aviva.technicaltest.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
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

    @PostConstruct
    protected void init() {
    	initModeList();	
    }

    public void initModeList(){
    	
    	LOGGER.info("initializing model");
        if(query !=null){
        	LOGGER.info("query is not null");
        } else {
        	LOGGER.info("query is null");
        }
    	resultList =  query.getTaggedPages("geometrixx-outdoors:gender",false);
        resultList.add("testing123");
    }
    public List<String> getResultList() {
        return resultList;
    }
}
