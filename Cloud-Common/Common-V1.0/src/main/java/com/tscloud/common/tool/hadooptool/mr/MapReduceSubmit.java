package com.tscloud.common.tool.hadooptool.mr;

/**
 * @author lei.yang1
 * @date 2017/7/10
 */
public class MapReduceSubmit {

    /**
     * name
     */
    private String name;

    /**
     * jar 包位置
     */
    private String jar;

    /**
     * 主类
     */
    private String mainClass;

    /**
     * map container占用内存大小
     */
    private String mapMemory;

    /**
     * reduce container占用内存大小
     */
    private String reduceMemory;

    /**
     * map进程的jvm设置
     */
    private String mapJavaOpt;

    /**
     * reduce进程的jvm设置
     */
    private String reduceJavaOpt;

    /**
     * 输入参数
     */
    private String inParams;

    /**
     * 输出参数
     */
    private String outParams;

    public String getMapMemory() {
        return mapMemory;
    }

    public String getReduceMemory() {
        return reduceMemory;
    }

    public String getMapJavaOpt() {
        return mapJavaOpt;
    }

    public String getReduceJavaOpt() {
        return reduceJavaOpt;
    }

    public String getInParams() {
        return inParams;
    }

    public String getOutParams() {
        return outParams;
    }

    public MapReduceSubmit setMapMemory(String mapMemory) {
        this.mapMemory = mapMemory;
        return this;
    }

    public MapReduceSubmit setReduceMemory(String reduceMemory) {
        this.reduceMemory = reduceMemory;
        return this;
    }

    public MapReduceSubmit setMapJavaOpt(String mapJavaOpt) {
        this.mapJavaOpt = mapJavaOpt;
        return this;
    }

    public MapReduceSubmit setReduceJavaOpt(String reduceJavaOpt) {
        this.reduceJavaOpt = reduceJavaOpt;
        return this;
    }

    public MapReduceSubmit setInParams(String inParams) {
        this.inParams = inParams;
        return this;
    }

    public MapReduceSubmit setOutParams(String outParams) {
        this.outParams = outParams;
        return this;
    }

    public String getJar() {
        return jar;
    }

    public MapReduceSubmit setJar(String jar) {
        this.jar = jar;
        return this;
    }

    public String getName() {
        return name;
    }

    public MapReduceSubmit setName(String name) {
        this.name = name;
        return this;
    }

    public String getMainClass() {
        return mainClass;
    }

    public MapReduceSubmit setMainClass(String mainClass) {
        this.mainClass = mainClass;
        return this;
    }

    public String buildShell() {
        StringBuffer stringBuffer = new StringBuffer("hadoop jar ");
        stringBuffer.append(jar).append(" ")
                .append(mainClass).append(" ")
                .append(this.getInParams()).append(" ")
                .append(this.getOutParams());
        if (this.getReduceJavaOpt() != null) {
            stringBuffer.append(" ").append("-Dmapreduce.reduce.java.opts=").append(this.getReduceJavaOpt());
        }
        if (this.getMapJavaOpt() != null) {
            stringBuffer.append(" ").append("-Dmapreduce.map.java.opts=").append(this.getMapJavaOpt());
        }
        if (this.getMapMemory() != null) {
            stringBuffer.append(" ").append("-Dmapreduce.map.memory.mb=").append(this.getMapMemory());
        }
        if (this.getReduceMemory() != null) {
            stringBuffer.append(" ").append("-Dmapreduce.reduce.memory.mb=").append(this.getReduceMemory());
        }
        if (this.getName() != null) {
            stringBuffer.append(" ").append("-Dmapreduce.job.name=").append(this.getName());
        }
        return stringBuffer.toString();
    }
}