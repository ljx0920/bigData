#!/bin/sh
#
# start dataShow-server
#
###EOF

prog=dataShow-server

export JAVA_HOME=${JAVA_HOME}
pwd=$(cd `dirname $0`; pwd)
echo $pwd
export DEPLOY_PATH=${pwd%/*}
echo ${DEPLOY_PATH}
export DEBUG_PORT=5289

if [ ! -d $JAVA_HOME ];then
    echo "please set right JAVA_HOME in this file"
    exit 0
fi

if [ ! -d $DEPLOY_PATH ];then
    echo "please set right DEPLOY_PATH in this file"
    exit 0
fi

function usage(){
cat << EOF
Usage: startServer.sh --port <port>  [options]

Options:
    --help | -h Print usage information.
    --port | -p Set java debug port, default value(5100).
EOF

exit $1
}

while [ $# -gt 0 ]; do
    case "$1" in
        -h|--help) usage 0 ;;
        -p|--port) shift; DEBUG_PORT=$1 ;;
        *) shift ;; # ignore
    esac
    shift
done


export JAVA_OPTIONS="-Xmx1024m -Xms1024m -XX:MaxPermSize=2048m"
export CLASSPATH=.:${CLASSPATH}:${DEPLOY_PATH}/resources:${DEPLOY_PATH}/lib

export JAVA_DEBUG="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=${DEBUG_PORT},server=y,suspend=n"

${JAVA_HOME}/bin/java -cp ${DEPLOY_PATH}/resources:${DEPLOY_PATH}/lib/*:${DEPLOY_PATH}/lib/DataQuery-Server-1.0.jar:. ${JAVA_OPTIONS} ${JAVA_DEBUG} com.dq.manage.DataQueryServerApplication $prog &
