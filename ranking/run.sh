#!/bin/bash

./gradlew clean assemble
docker-compose up --build