i#!/bin/sh
mvn release:prepare
e="$?"
if [ $e -ne 0 ]
then
	echo "mvn build failed exiting..."
	exit -1
fi
v=`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | grep -v '\['`
mvn release:prepare -Darguments="-DskipTests"
mail -s "Neo BO2 $v" `cat recipientsOne.txt`<changelog.txt
