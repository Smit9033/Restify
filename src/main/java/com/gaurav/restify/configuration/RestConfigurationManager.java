package com.gaurav.restify.configuration;


import com.gaurav.restify.configuration.configurationBeans.RestConfiguration;
import com.gaurav.restify.configuration.configurationBeans.RestJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.HashMap;

import static com.gaurav.restify.constants.ConfigurationConstants.REST_CONFIG_FILE;

@Component
public class RestConfigurationManager {


    private static RestConfigurationManager restConfigurationManager = null;
    private static RestConfiguration restConfiguration = null;
    private static HashMap<String, RestJob> restJobHashMap = null;
    private static final Logger logger = LoggerFactory.getLogger(RestConfigurationManager.class);

    private RestConfigurationManager() {

    }


    public static RestConfigurationManager getInstance() {
        logger.info("RestConfigurationManager :: Instantiate");

        if (null == restConfigurationManager) {
            try {
                restConfiguration = (RestConfiguration) ConfigurationUtil.XMLtoObject(RestConfiguration.class, REST_CONFIG_FILE);
                instantiateRestJobMap();
                return new RestConfigurationManager();

            } catch (JAXBException e) {

                logger.error( "Could not parse xml file - " + REST_CONFIG_FILE, e);
                System.exit(0);

            } catch (FileNotFoundException e) {
                logger.error("xml file not found- " + REST_CONFIG_FILE, e);
                System.exit(0);


            }


        }

        return restConfigurationManager;
    }


    private static void instantiateRestJobMap() {
        logger.info( "Going to instantiate rest job map");
        if (null != restConfiguration) {
            restJobHashMap = new HashMap<>();
            restConfiguration.getJobs().forEach(job -> restJobHashMap.put(job.getCommand(), job));
        } else {
            logger.debug("restConfiguration is null");

        }
    }

    public RestJob getRestJob(String commandName) {
        return restJobHashMap.get(commandName);
    }


}