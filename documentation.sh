#!/bin/bash

if(( ! doc -d ))
then
    mkdir doc
fi
javadoc -d doc world_3/*.java 