#!/bin/bash
export className=App
echo "## Running $className..."
mvn exec:java -Dexec.mainClass="br.com.elasticsearch.$className"