#!/bin/sh
mvn clean install 

e="$?"
if [ $e -ne 0 ]
then
        echo "build failed exiting..."
        exit -1
fi

mvn --batch-mode release:prepare -Darguments="-DskipTests"
 
e="$?"
if [ $e -ne 0 ]
then
	echo "release preparation failed exiting..."
	exit -1
fi

v=`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | grep -v '\['`

echo "prepared release version $v"

mvn release:perform -Darguments="-DskipTests"

mail -s "Bo2 $v released" `cat recipientsOne.txt`
