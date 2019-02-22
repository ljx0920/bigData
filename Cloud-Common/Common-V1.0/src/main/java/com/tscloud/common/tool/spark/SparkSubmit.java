package com.tscloud.common.tool.spark;

/**
 *
 * @author lei.yang1
 * @date 2017/5/4
 */
public class SparkSubmit {

    public static final String DEPLOY_MODEL_CLUSTER = "cluster";
    public static final String DEPLOY_MODEL_CLIENT = "client";
    /**
     *  master url 如：spark://host:port, mesos://host:port, yarn, or local.
     */
    private String master;
    /**
     *  部署模式，client 或者cluster，默认为client
     */
    private String deployModel;
    /**
     * mainclass
     */
    private String mainClass;
    /**
     * 应用名称
     */
    private String name;
    /**
     * Driver依赖的第三方jar包
     */
    private String jars;
    /**
     * 以","隔开的依赖包描述（maven），将搜索本地仓库和远程仓库，格式为
     * groupId:artifactId:version,groupId:artifactId:version
     */
    private String packages;
    /**
     * 被排除的包描述，中间用","隔开，格式为groupId:artifactId,groupId:artifactId
     * 主要用来解决包冲突
     */
    private String excludePackages;
    /**
     * 以逗号分隔的额外的远程存储库，用来搜索packages中配置的内容。
     */
    private String repositories;
    /**
     * 用逗号隔开的放置在Python应用程序PYTHONPATH上的.zip,  .egg, .py文件列表
     */
    private String pyFile;
    /**
     * 用逗号隔开的要放置在每个executor工作目录的文件列表
     */
    private String files;
    /**
     * spark的配置格式为：key=value,key=value
     */
    private String conf;
    /**
     * 设置应用程序属性的文件路径，默认是conf/spark-defaults.conf
     */
    private String propertiesFile;
    /**
     * Driver程序使用内存大小
     */
    private String driverMemory;
    /**
     * Driver程序使用jvm配置
     */
    private String driverJavaOptions;
    /**
     * Driver程序的库路径
     */
    private String driverLibraryPath;
    /**
     * Driver程序的类路径，如果设置jars则设置的内容被包含到这里
     */
    private String driverClassPath;
    /**
     * executor内存大小，默认1G
     */
    private String executorMemory;
    /**
     * 提交时的用户
     */
    private String proxyUser;
    /**
     * Driver程序的使用CPU个数，仅限于Spark Alone的cluster模式
     */
    private int driverCores = 0;

    /**
     * 失败后是否重启Driver，仅限于Spark  Alone或者mesos的cluster模式
     */
    private boolean supervise = false;

    /**
     * 所有executor使用的cpu个数，仅限于Spark  Alone或者mesos
     */
    private int totalExecutorCores = 0;

    /**
     * 每个executor使用的cpu个数，仅限于Spark  Alone或者YARN
     * YARN默认为1个
     * Alone默认为在worker上所有活动的cpu
     */
    private int executorCores;

    /**
     * executor的数量，yarn的独有参数，默认为2
     */
    private int executorNumber;

    /**
     * application jar
     */
    private String applicationJar;

    /**
     * application 参数列表，中间用" "（空格）隔开
     */
    private String applicationArguments;

    public String getMaster() {
        return master;
    }

    public SparkSubmit setMaster(String master) {
        this.master = master;
        return this;
    }

    public String getDeployModel() {
        return deployModel;
    }

    public SparkSubmit setDeployModel(String deployModel) {
        this.deployModel = deployModel;
        return this;
    }

    public String getMainClass() {
        return mainClass;
    }

    public SparkSubmit setMainClass(String mainClass) {
        this.mainClass = mainClass;
        return this;
    }

    public String getName() {
        return name;
    }

    public SparkSubmit setName(String name) {
        this.name = name;
        return this;
    }

    public String getJars() {
        return jars;
    }

    public SparkSubmit setJars(String jars) {
        this.jars = jars;
        return this;
    }

    public String getPackages() {
        return packages;
    }

    public SparkSubmit setPackages(String packages) {
        this.packages = packages;
        return this;
    }

    public String getExcludePackages() {
        return excludePackages;
    }

    public SparkSubmit setExcludePackages(String excludePackages) {
        this.excludePackages = excludePackages;
        return this;
    }

    public String getRepositories() {
        return repositories;
    }

    public SparkSubmit setRepositories(String repositories) {
        this.repositories = repositories;
        return this;
    }

    public String getPyFile() {
        return pyFile;
    }

    public SparkSubmit setPyFile(String pyFile) {
        this.pyFile = pyFile;
        return this;
    }

    public String getFiles() {
        return files;
    }

    public SparkSubmit setFiles(String files) {
        this.files = files;
        return this;
    }

    public String getConf() {
        return conf;
    }

    public SparkSubmit setConf(String conf) {
        this.conf = conf;
        return this;
    }

    public String getPropertiesFile() {
        return propertiesFile;
    }

    public SparkSubmit setPropertiesFile(String propertiesFile) {
        this.propertiesFile = propertiesFile;
        return this;
    }

    public String getDriverMemory() {
        return driverMemory;
    }

    public SparkSubmit setDriverMemory(String driverMemory) {
        this.driverMemory = driverMemory;
        return this;
    }

    public String getDriverJavaOptions() {
        return driverJavaOptions;
    }

    public SparkSubmit setDriverJavaOptions(String driverJavaOptions) {
        this.driverJavaOptions = driverJavaOptions;
        return this;
    }

    public String getDriverLibraryPath() {
        return driverLibraryPath;
    }

    public SparkSubmit setDriverLibraryPath(String driverLibraryPath) {
        this.driverLibraryPath = driverLibraryPath;
        return this;
    }

    public String getDriverClassPath() {
        return driverClassPath;
    }

    public SparkSubmit setDriverClassPath(String driverClassPath) {
        this.driverClassPath = driverClassPath;
        return this;
    }

    public String getExecutorMemory() {
        return executorMemory;
    }

    public SparkSubmit setExecutorMemory(String executorMemory) {
        this.executorMemory = executorMemory;
        return this;
    }

    public String getProxyUser() {
        return proxyUser;
    }

    public SparkSubmit setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
        return this;
    }

    public int getDriverCores() {
        return driverCores;
    }

    public SparkSubmit setDriverCores(int driverCores) {
        this.driverCores = driverCores;
        return this;
    }

    public boolean isSupervise() {
        return supervise;
    }

    public SparkSubmit setSupervise(boolean supervise) {
        this.supervise = supervise;
        return this;
    }

    public int getTotalExecutorCores() {
        return totalExecutorCores;
    }

    public SparkSubmit setTotalExecutorCores(int totalExecutorCores) {
        this.totalExecutorCores = totalExecutorCores;
        return this;
    }

    public int getExecutorCores() {
        return executorCores;
    }

    public SparkSubmit setExecutorCores(int executorCores) {
        this.executorCores = executorCores;
        return this;
    }

    public String getApplicationArguments() {
        return applicationArguments;
    }

    public SparkSubmit setApplicationArguments(String applicationArguments) {
        this.applicationArguments = applicationArguments;
        return this;
    }

    public int getExecutorNumber() {
        return executorNumber;
    }

    public SparkSubmit setExecutorNumber(int executorNumber) {
        this.executorNumber = executorNumber;
        return this;
    }

    public String getApplicationJar() {
        return applicationJar;
    }

    public SparkSubmit setApplicationJar(String applicationJar) {
        this.applicationJar = applicationJar;
        return this;
    }

    public String buildShell(){
        StringBuffer sb = new StringBuffer("spark-submit \\\n");
        if ( !isEmpty( this.master ) ){
            sb.append( " --master " ).append( this.master ).append( " \\\n" );
        }
        if ( !isEmpty( this.deployModel ) ){
            sb.append( " --deploy-mode " ).append( this.deployModel ).append( " \\\n" );
        }
        if ( !isEmpty( this.mainClass ) ){
            sb.append( " --class " ).append( this.mainClass ).append( " \\\n" );
        } else {
            throw new RuntimeException( "no main class." );
        }
        if ( !isEmpty( this.name ) ){
            sb.append( " --name " ).append( this.name ).append( " \\\n" );
        }
        if ( !isEmpty( this.jars ) ){
            sb.append( " --jars " ).append( this.jars ).append( " \\\n" );
        }
        if ( !isEmpty( this.packages ) ){
            sb.append( " --packages " ).append( this.packages ).append( " \\\n" );
        }
        if ( !isEmpty( this.excludePackages ) ){
            sb.append( " --exclude-packages " ).append( this.excludePackages ).append( " \\\n" );
        }
        if ( !isEmpty( this.repositories ) ){
            sb.append( " --repositories " ).append( this.repositories ).append( " \\\n" );
        }
        if ( !isEmpty( this.pyFile ) ){
            sb.append( " --py-files " ).append( this.pyFile ).append( " \\\n" );
        }
        if ( !isEmpty( this.files ) ){
            sb.append( " --name " ).append( this.files ).append( " \\\n" );
        }
        if ( !isEmpty( this.conf ) ){
            sb.append( " --conf " ).append( this.conf ).append( " \\\n" );
        }
        if ( !isEmpty( this.propertiesFile ) ){
            sb.append( " --properties-file " ).append( this.propertiesFile ).append( " \\\n" );
        }
        if ( !isEmpty( this.driverMemory ) ){
            sb.append( " --driver-memory " ).append( this.driverMemory ).append( " \\\n" );
        }
        if ( !isEmpty( this.driverJavaOptions ) ){
            sb.append( " --driver-java-options " ).append( this.driverJavaOptions ).append( " \\\n" );
        }
        if ( !isEmpty( this.driverLibraryPath ) ){
            sb.append( " --driver-library-path " ).append( this.driverLibraryPath ).append( " \\\n" );
        }
        if ( !isEmpty( this.driverClassPath ) ){
            sb.append( " --driver-class-path " ).append( this.driverClassPath ).append( " \\\n" );
        }
        if ( !isEmpty( this.executorMemory ) ){
            sb.append( " --executor-memory " ).append( this.executorMemory ).append( " \\\n" );
        }
        if ( !isEmpty( this.proxyUser ) ){
            sb.append( " --proxy-user " ).append( this.proxyUser ).append( " \\\n" );
        }
        if ( this.driverCores > 0 ){
            sb.append( " --driver-cores " ).append( this.driverCores ).append( " \\\n" );
        }
        if ( this.executorNumber > 0 ){
            sb.append( "--num-executors " ).append( this.executorNumber ).append( " \\\n" );
        }
        if ( supervise ){
            sb.append( " --supervise " ).append( " \\\n" );
        }
        if ( this.totalExecutorCores > 0 ){
            sb.append( " --total-executor-cores " ).append( this.totalExecutorCores ).append( " \\\n" );
        }
        if ( this.executorCores > 0 ){
            sb.append( " --executor-cores " ).append( this.executorCores ).append( " \\\n" );
        }
        if ( !this.isEmpty( applicationJar ) ) {
            sb.append( applicationJar ).append( " \\\n" );
        } else {
            throw new RuntimeException( "no application jar." );
        }
        if ( !this.isEmpty( applicationArguments ) ) {
            sb.append( applicationArguments );
        }
        return sb.toString();
    }

    private boolean isEmpty( String str ){
        return str == null || str.length() == 0;
    }
}
