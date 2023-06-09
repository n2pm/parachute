#!/usr/bin/env bash

set -a
source .env
set +a

if [ -z MODRINTH_TOKEN ]; then
  echo "No MODRINTH_TOKEN set in .env"
  exit 1
fi

read -p "Is release? (y/n): " is_release
if [ "$is_release" = "y" ]; then
    export BUILD_RELEASE=true
else
    export BUILD_RELEASE=false
fi

if [ ! -f CHANGELOG.md ]; then
  echo "No CHANGELOG.md found"
  exit 1
fi

./gradlew build

