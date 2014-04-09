#!/bin/sh
mvn -q clean install 

e="$?"
if [ $e -ne 0 ]
then
        echo "build failed exiting..."
        exit -1
fi

snapshot=`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | grep -v '\['`
release=${snapshot/"-SNAPSHOT"/}

mvn -q --batch-mode release:prepare -Darguments="-DskipTests"
 
e="$?"
if [ $e -ne 0 ]
then
	echo "release preparation failed exiting..."
	exit -1
fi

echo "prepared release version $release"

mvn -q release:perform -Darguments="-DskipTests"

echo $release | mail -s "Bo2 $release released" `cat recipientsOne.txt`
