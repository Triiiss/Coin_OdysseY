#!/bin/bash

if [ ! -d doc ] ; then
    mkdir doc
fi

javadoc -d doc $(find world_5 -name "*.java")