#!/bin/bash

readonly BROWSER_CONFIG_PATH=selenoid/config/browsers.json
readonly IMAGE_JQ=imega/jq

use_jq() {
    docker run --rm -i $IMAGE_JQ -c $1
}

clear
echo "Download utils images"
docker pull $IMAGE_JQ

echo
echo "Search browswer images from $BROWSER_CONFIG_PATH"
images=$(cat $BROWSER_CONFIG_PATH | use_jq .[].versions | use_jq .[].image)
echo "Pull all browser images (selenoid not provide this functionality)"
for image in $images; do
    echo
    echo ">> Try to download: ${image//\"}"
    docker pull ${image//\"}
done

echo
echo "Run selenoid components (core and ui)"
docker-compose up -d

echo 
echo "Selenoid url set to environemnt variable"
export SELENOID_HUB_HOST=http://localhost:4444/wd/hub
echo ">> $(env | grep SELENOID_HUB_HOST)"

echo
echo "Selenoid was installed and UI available on http://localhost:8080"

