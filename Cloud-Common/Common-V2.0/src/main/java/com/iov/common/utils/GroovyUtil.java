package com.iov.common.utils;

import com.google.common.collect.Maps;
import groovy.lang.GroovyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.*;
import java.io.*;
import java.util.Map;

/**
 * @author daowan.hu
 * @date 2018-04-11
 */
public class GroovyUtil {
    private static Logger logger = LoggerFactory.getLogger(GroovyUtil.class);

    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
    private static Map<Object, CompiledScript> compiledScriptMap = Maps.newHashMap();
    private static Map<Object, Invocable> invocableMap = Maps.newHashMap();


    /**
     * 执行一段文本表示的脚本片段，得到返回值
     *
     * @param script 脚本片段，如：return "hello $name!"
     * @param params 脚本中使用的变量名称和值的映射，如：{"name":"Jack"}
     * @return
     */
    public static Object evalScript(String script, Map<String, Object> params){
        Object object = null;
        try{
            Bindings bindings = new SimpleBindings(params);
            CompiledScript cs = compiledScriptMap.get(script);
            if (cs == null) {
                cs = ((Compilable) engine).compile(script);
                compiledScriptMap.put(script, cs);
            }
            object =  cs.eval(bindings);
        }catch (Exception e){
            logger.error("[GroovyUtil]脚本运行失败！",e);
        }

        return object;
    }

    /**
     * 执行一个脚本文件，得到返回值
     *
     * @param file   脚本文件路径
     * @param params 脚本中使用的变量名称和值的映射
     * @return
     */
    public static Object runScriptFile(String file, Map<String, Object> params) throws ScriptException, FileNotFoundException {
        logger.debug("run script file: " + file);
        Reader reader = new FileReader(new File(file));
        Bindings bindings = new SimpleBindings(params);
        CompiledScript cs = compiledScriptMap.get(file);
        if (cs == null) {
            cs = ((Compilable) engine).compile(reader);
            compiledScriptMap.put(file, cs);
        }
        return cs.eval(bindings);
    }

    /**
     * 调用脚本片段中的一个函数
     *
     * @param script 脚本片段
     * @param fn     函数名称
     * @param params 脚本中使用的变量名称和值的映射
     * @param args   函数需要的参数
     * @return
     */
    public static Object invokeFnInScript(String script, String fn, Map<String, Object> params, Object... args) throws ScriptException, NoSuchMethodException {
        Bindings bindings = new SimpleBindings(params);
        engine.eval(script, bindings);
        Invocable invocable = invocableMap.get(script);
        if (invocable == null) {
            invocable = (Invocable) engine;
        }
        return invocable.invokeFunction(fn, args);
    }

    /**
     * 调用一个脚本文件中定义的函数
     *
     * @param file   脚本文件
     * @param fn     函数名称
     * @param params 脚本中使用的变量名称和值的映射
     * @param args   函数需要的参数
     * @return
     */
    public static Object invokeFnInFile(String file, String fn, Map<String, Object> params, Object... args) throws ScriptException, NoSuchMethodException, FileNotFoundException {
        Bindings bindings = new SimpleBindings(params);
        engine.eval(new FileReader(new File(file)), bindings);
        Invocable invocable = invocableMap.get(file);
        if (invocable == null) {
            invocable = (Invocable) engine;
        }
        return invocable.invokeFunction(fn, args);
    }

    /**
     * 从文件中加载groovy class
     *
     * @param file 脚本文件
     * @return
     */
    public static Class loadClassFromFile(String file) {
        GroovyClassLoader cl = new GroovyClassLoader(GroovyUtil.class.getClassLoader());
        try {
            return cl.parseClass(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从文本中加载groovy class
     *
     * @param text 脚本片段
     * @return
     */
    public static Class loadClassFromText(String text) {
        GroovyClassLoader cl = new GroovyClassLoader(GroovyUtil.class.getClassLoader());
        return cl.parseClass(text);
    }

    /**
     * 删除已编译脚本
     *
     * @param script
     */
    public static void removeCache(String script) {
        compiledScriptMap.remove(script);
        invocableMap.remove(script);
    }
}
