package com.wdl.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 
 * @author bin
 *
 */
public class ResourcesUtil {
	private static Properties props=null;
	private static org.springframework.core.io.Resource resource = new ClassPathResource("/config.properties");
	static{
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getValue(String key){
		if(props==null){
			return null;
		}
		return props.getProperty(key);
	} 
	////////////////////////////////////////////////////////////////////////////////
	private static Properties providerProps=null;
    private static org.springframework.core.io.Resource providerResource = new ClassPathResource("/provider.properties");
    static{
        try {
            providerProps = PropertiesLoaderUtils.loadProperties(providerResource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public static String getProviderValue(String key){
	    if(providerProps==null){
            return null;
        }
        return providerProps.getProperty(key);
	} 
	//////////////////////////
	private final static Map<String,Properties> propertiesMap=new HashMap<String,Properties>();
	public static String getResourcesValue(String rescourceClassPath,String key){
		try {
		    if (propertiesMap.get(rescourceClassPath)!=null){
		    	return propertiesMap.get(rescourceClassPath).getProperty(key,null);
		    }else{
		    	Properties  properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(rescourceClassPath));
		    	propertiesMap.put(rescourceClassPath, properties);
		    	return properties.getProperty(key, null);
		    }
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	} 
}
