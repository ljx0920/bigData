/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.tscloud.common.tool.resthttps;


/**
 * https rest server
 * @author Administrator
 */
public class HttpsServer {
//	private static final Logger log = LogManager.getLogger(HttpsServer.class);
//	private HttpServer webServer;
//    private static HttpsServer instance;
//
//	public synchronized static  HttpsServer getInstance(){
//		if(instance == null){
//			instance = new HttpsServer();
//		}
//		return instance ;
//	}
//
//	public int getPort(int defaultPort) {
//	    String port = AccessController.doPrivileged(PropertiesHelper.getSystemProperty("jersey.config.test.container.port"));
//		if (null != port) {
//			try {
//				return Integer.parseInt(port);
//			} catch (Exception e) {
//				log.error("https port fails.");
//			}
//		}
//		return defaultPort;
//	}
//
//	public URI createHttpsUrl(String ip, int port) {
//		URI uri = null;
//		uri = UriBuilder.fromUri("https://" + ip + "/").port(getPort(port)).build();
//		return uri;
//	}
//
//	/**
//	 * 启动https server
//	 * @param ip
//	 * @param port
//	 * className = org.glassfish.jersey.examples.httpsclientservergrizzly.RootResource
//	 */
//	public boolean startServer(String ip, int port,String className,String keystore_server,String keystore_password,String truststore_server,String truststore_password) {
//		boolean flag = false;
//		SSLContextConfigurator sslContext = new SSLContextConfigurator();
//
//        String deTrustPd = truststore_password;//AesUtil.decode(truststore_password);
//        String deKeyPd = keystore_password;//AesUtil.decode(keystore_password);
////        if (StringUtils.isBlank(deKeyPd) || StringUtils.isBlank(deKeyPd)) {
////            throw new NullPointerException("证书密匙解密失败，请确认密匙是否进行AES加密.");
////        }
//
//        sslContext.setKeyStoreFile(keystore_server);
//        sslContext.setKeyStorePass(deKeyPd);
//        sslContext.setTrustStoreFile(truststore_server);
//        sslContext.setTrustStorePass(deTrustPd);
//		try {
//			ResourceConfig rc = new ResourceConfig();
//			rc.registerClasses(Class.forName(className),SecurityFilter.class,AuthenticationExceptionMapper.class);
////			rc.registerClasses(Class.forName(className));
//			URI uri = this.createHttpsUrl(ip, port);
//			webServer = GrizzlyHttpServerFactory.createHttpServer(uri,rc, true,
//					      new SSLEngineConfigurator(sslContext).setClientMode(false).setNeedClientAuth(true));
//			log.info("https server started. " + uri);
//			webServer.start();
//			flag = true;
//			log.info("https server  Start successfully.");
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("https server  Start fails.");
//		}
//		return flag;
//	}
//
//	/**
//	 * stop https server
//	 */
//	public void stopServer() {
//		webServer.shutdownNow();
//	}
//
//	public static void main(String[] args) throws Exception {
////		String ip = "";
////		int port = Integer.valueOf(ConfigHelper.getJettyParameter("https.restserver.port"));
////		String className = "org.glassfish.jersey.examples.httpsclientservergrizzly.RootResource";
//////		String keystore_server = FileUtil.getFilePath(ConfigHelper.getJettyParameter("keystore.server"),"base.home");
////		String dir = FileUtil.getFilePath("config","base.home");
////		String keystore_server = dir+"/"+ConfigHelper.getValue("keystore.server");
////		String keystore_password = ConfigHelper.getJettyParameter("keystore.password");
//////		String truststore_server = FileUtil.getFilePath(ConfigHelper.getJettyParameter("truststore.server"),"base.home");
////		String truststore_server = dir+"/"+ConfigHelper.getValue("truststore.server");
////		String truststore_password = ConfigHelper.getJettyParameter("truststore.password");
////		HttpsServer httpsServer = new HttpsServer();
////		httpsServer.startServer(ip, port, className, keystore_server, keystore_password, truststore_server, truststore_password);
//
//	}
}
