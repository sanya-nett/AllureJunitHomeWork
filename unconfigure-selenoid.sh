#!/bin/bash

remove_images() {
    echo
    echo ">> Try to delete $1 images"

    if [ -z "$2" ]
    then
        echo ">>>> Not found images for delete"
    else
        docker rmi -f $2
    fi
}

clear
echo "Stop selenoid components (core and ui)"
docker-compose down

echo
echo "Remove docker images for selenoid and browser images:"
remove_images aerokube "$(docker images "aerokube/*" -aq)"
remove_images selenoid "$(docker images "selenoid/*" -aq)"

echo
echo "Remove utils images"
remove_images jq "$(docker images imega/jq -aq)"

echo
echo "Selenoid artifacts was deleted"
