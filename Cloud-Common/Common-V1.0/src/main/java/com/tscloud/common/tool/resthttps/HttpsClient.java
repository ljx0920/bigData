package com.tscloud.common.tool.resthttps;

public class HttpsClient {
	    private static final String TRUSTORE_CLIENT_FILE = "c://key/truststore_client";
	    private static final String TRUSTSTORE_CLIENT_PWD = "asdfgh";
	    private static final String KEYSTORE_CLIENT_FILE = "c://key/keystore_client";
	    private static final String KEYSTORE_CLIENT_PWD = "asdfgh";
	    
//	    public void testSSLWithBasicAndSSLAuthGrizzlyConnector() {
//	    	ClientConfig clientConfig = new ClientConfig().connectorProvider(new GrizzlyConnectorProvider());
//	    	_testSSLWithBasicAndSSLAuth(clientConfig);
//	    }
//
//
//	    public String _testSSLWithBasicAndSSLAuth(ClientConfig clientConfig) {
//	    	String rs= "";
//	        SslConfigurator sslConfig = SslConfigurator.newInstance()
//	                .trustStoreFile(TRUSTORE_CLIENT_FILE)
//	                .trustStorePassword(TRUSTSTORE_CLIENT_PWD)
//	                .keyStoreFile(KEYSTORE_CLIENT_FILE)
//	                .keyPassword(KEYSTORE_CLIENT_PWD);
//
//	        final SSLContext sslContext = sslConfig.createSSLContext();
//	        Client client = ClientBuilder.newBuilder().withConfig(clientConfig).sslContext(sslContext).build();
//	        WebTarget target = client.target("https://127.0.0.1:5033/iswapnode/restful");
//	        rs = target.path("iswapRest").path("workFlowForRest").queryParam( "nodeCode","sadafdasfd-afasfd-asdfdasfqw-asfdsad").request().get(String.class);
//	        System.out.println(rs);
//	        client.close();
//			return rs;
//	    }
	    
	    public static void main(String[] args) {
	    	HttpsClient httpsClient= new HttpsClient();
//	    	httpsClient.testSSLWithBasicAndSSLAuthGrizzlyConnector();
		}
}
