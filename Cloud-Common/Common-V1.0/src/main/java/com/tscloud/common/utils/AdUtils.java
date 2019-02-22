package com.tscloud.common.utils;

import com.google.common.collect.Maps;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.*;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by ruixiang.zhao on 2018/9/4.
 */
public class AdUtils {

        private static Logger log = LogManager.getLogger(AdUtils.class);

        private static volatile LdapContext context;

        private static void login() {
            String strURL = "ldap://10.10.0.63:389/";
            String username = "sso";
            String pwd = "9654321";
            String root = "DC=hirain,DC=com";
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, strURL + root);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, username);
            env.put(Context.SECURITY_CREDENTIALS, pwd);
            try {
                context = new InitialLdapContext(env, null);
            } catch (NamingException e) {
                log.error("AdUtil login naming error", e);
            }
        }

        public static void close() throws Exception {
            context.close();
        }

        private static Map<String, Object> load(String key, String name) throws NamingException, IOException {
            login();

            Map<String, Object> resultAll = Maps.newHashMap();
            String searchFilter = key + "=" + name;
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            searchControls.setReturningAttributes(null);
            int pageSize = 100;
            byte[] cookie;
            //newControl
            Control[] controls = new Control[]{new PagedResultsControl(pageSize, true)};
            context.setRequestControls(controls);
            do {
                NamingEnumeration<SearchResult> answer = context.search("", searchFilter, searchControls);
                while (answer.hasMoreElements()) {
                    Map<String, String> resultMap = Maps.newHashMap();
                    SearchResult sr = answer.next();
                    Attributes attrs = sr.getAttributes();
                    if (attrs != null) {
                        NamingEnumeration<?> ne = attrs.getAll();
                        for (; ne.hasMore(); ) {
                            String user = ne.next().toString().replace("", "");
                            String[] buff = user.split(":");
                            resultMap.put(buff[0], buff[1]);
                        }
                    }
                    if (resultMap.containsKey("name")) {
                        resultAll.put(resultMap.get("name"), resultMap);
                    } else {
                        resultAll.put(resultMap.get(key), resultMap);
                    }
                }
                cookie = parseControls(context.getResponseControls());
                context.setRequestControls(new Control[]{new PagedResultsControl(pageSize, cookie, Control.CRITICAL)});
            } while ((cookie != null) && (cookie.length != 0));
            return resultAll;
        }

        private static byte[] parseControls(Control[] controls) {
            byte[] cookie = null;
            if (controls != null) {
                for (Control control : controls) {
                    PagedResultsResponseControl prrc = (PagedResultsResponseControl) control;
                    cookie = prrc.getCookie();
                }
            }
            return (cookie == null) ? new byte[0] : cookie;
        }

        public static Map<String, Object> loadEmployee(String name) throws IOException, NamingException {
            return load("cn", name);
        }

        public static Map<String, Object> loadDepartment(String name) throws IOException, NamingException {
            return load("ou", name);
        }
    }



