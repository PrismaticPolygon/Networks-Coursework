#!/usr/bin/env bash
DIR="./out/production/Networks-Coursework/"

[[ ! -d ${DIR} ]] && mkdir -p ${DIR}

javac -cp ${DIR} -sourcepath ./src/ -d ${DIR} ./src/Server.java
java -cp ${DIR} Server 4444