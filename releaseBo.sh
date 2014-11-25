#!/bin/sh

svn_status=$(svn status)

if [ ! -z "$svn_status" ]
then
	echo "You have local uncommitted changes"
	exit -1
fi

svn update

mvn -q clean install 

e="$?"
if [ $e -ne 0 ]
then
        echo "build failed exiting..."
        exit -1
fi

snapshot=`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | grep -v '\['`
release=${snapshot/"-SNAPSHOT"/}

echo "preparing release version $release"

mvn -q --batch-mode release:prepare -Dbo2.skip.tests=true
 
e="$?"
if [ $e -ne 0 ]
then
	echo "release preparation failed exiting..."
	exit -1
fi

echo "performing release of version $release"

mvn -q release:perform -Dbo2.skip.tests=true

e="$?"
if [ $e -ne 0 ]
then
        echo "release failed exiting..."
        exit -1
fi

mail -s "Bo2 $release released" `cat recipientsOne.txt` < changelog.txt

printf "\n\n$release\n--------\n\n" >> history.txt
cat changelog.txt >> history.txt

:>changelog.txt

svn commit changelog.txt history.txt -m "added to release history"
