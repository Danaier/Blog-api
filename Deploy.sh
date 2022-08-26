#!/bin/bash

echo 'start auto deploying~'
echo 'start git pulling'

git pull

echo 'git pull done'

echo 'trying maven package'

#mvn clean
#mvn clean >/dev/null 2>/dev/null
mvn package >/dev/null #2>/dev/null

echo 'maven package done'

echo 'seeing if the old project is running now'

PID=$(ps -ef | grep Blog-api-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
    echo 'seems it is not running'
else
    echo 'shutting down the pre service'
    kill $PID
fi

echo 'starting this new service now'
nohup java -jar target/Blog-api-0.0.1-SNAPSHOT.jar > Blog-api.log 2>&1 &

echo 'now its actually running perfectly'


