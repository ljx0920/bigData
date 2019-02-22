package com.tscloud.common.tool.resthttps;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tscloud.common.framework.config.ConfigHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * authentication filter.
 *
 * @author Administrator
 */
@Provider
@PreMatching
public class SecurityFilter implements ContainerRequestFilter {

    private static Logger log = LogManager.getLogger(SecurityFilter.class);
//
//	@Inject
//	javax.inject.Provider<UriInfo> uriInfo;

    @Override
    public void filter(ContainerRequestContext filterContext)
            throws IOException {
        String attrCer = "javax.servlet.request.X509Certificate";
        X509Certificate[] certs = (X509Certificate[]) filterContext.getProperty(attrCer);
//		boolean flag = this.authenticate(certs);
//		if (!flag) {
//            Response response = Response.ok("证书错误，认证未通过，请联系管理员，更换证书！！！").type(MediaType.APPLICATION_JSON).build();
//            throw new WebApplicationException(response);
//		}
    }

    private boolean authenticate(X509Certificate[] certs) {
        boolean isTrue = false;
        if (certs != null) {
            int count = certs.length;
            log.info("共检测到[" + count + "]个客户端证书");
            for (X509Certificate cert : certs) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String info = null;
                // 获得证书版本
                info = String.valueOf(cert.getVersion());
                log.debug("证书版本:" + info);
                // 获得证书序列号
                info = cert.getSerialNumber().toString(16);
                log.debug("证书序列号:" + info);
                // 获得证书有效期
                Date beforeDate = cert.getNotBefore();
                info = dateFormat.format(beforeDate);
                log.debug("证书生效日期:" + info);
                Date afterDate = cert.getNotAfter();
                info = dateFormat.format(afterDate);
                log.debug("证书失效日期:" + info);
                // 获得证书主体信息
                info = cert.getSubjectDN().getName();
                log.debug("证书拥有者:" + info);
                // 获得证书颁发者信息
                info = cert.getIssuerDN().getName();
                log.debug("证书颁发者:" + info);
                // 获得证书签名算法名称
                info = cert.getSigAlgName();
                log.debug("证书签名算法:" + info);
                Map<String, String> params = Maps.newHashMap();
                params.put("certInfo", cert.getSubjectDN().getName());
                params.put("startTime", dateFormat.format(beforeDate));
                String json = JSON.toJSONString(params);
                Map<String, String> body = Maps.newHashMap();
                body.put("body", json);
                if (!cert.getSubjectDN().getName().contains("Root")) {
                    log.info("客户端证书信息:" + json);
                    isTrue = checkCertificate(body);
                }
            }
        }
        return isTrue;
    }

    @SuppressWarnings("static-access")
    private boolean checkCertificate(Map<String, String> body) {
        String url = ConfigHelper.getJettyParameter("rest.certificate.url");
//		String result = HttpClient.createDataGridServer().post(url, body);
//		if(result==null){
//			log.info("post请求失败，Body内容："+body.toString());
//			return false;
//		}
//		JSONObject obj = JSONObject.parseObject(result);
//		String res = (String) obj.get("status");
//		if(res.equalsIgnoreCase("true")){
//			log.info("Post请求成功返回result内容："+result);
//			return true;
//		}else{
//			log.info("Post请求失败返回result内容："+result);
//			return false;
//		}
        return false;
    }
}
