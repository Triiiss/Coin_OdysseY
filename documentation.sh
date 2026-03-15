#!/bin/bash

if(( ! doc -d ))
then
    mkdir doc
fi
javadoc -d doc world_4/*.java 