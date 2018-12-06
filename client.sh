#!/usr/bin/env bash
DIR="./out/production/Networks-Coursework/"

[[ ! -d ${DIR} ]] && mkdir -p ${DIR}

javac -cp ${DIR} -sourcepath ./src/ -d ${DIR} ./src/Client.java
java -cp ${DIR} Client "127.0.0.1" 4444