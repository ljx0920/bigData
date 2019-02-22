package com.tscloud.common.framework.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;


public class WSRestClient {
    private static final Logger log = LogManager.getLogger(WSRestClient.class);
	
	private static WebTarget webResource;
	
	/**
	 * 获取WebTarget
	 * @param url
	 * @return
	 */
	public static WebTarget getRestWebResource(String url){
		if( webResource == null ){
			try {
				Client client = null;
				//http方式
				client = ClientBuilder.newClient();
				webResource = client.target(url);
			
			} catch (Exception e) {
				log.error("WSRestClient 加载失败！", e);
			}
			
		}
		return webResource;
	}
}
