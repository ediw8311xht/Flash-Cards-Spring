#!/bin/bash

####################
( ##START_SUBSHELL##
####################

# If error or var not set exit
set -eu

check_create_db() {
    echo "${DB_USERNAME} LOGIN"
    if ! su -l "${DB_USERNAME}" -c 'psql -lqtA -0 -F ","' | grep -Pq '(\x00|^)'"${DB_NAME},"
    then
        echo "postgres LOGIN"
        su -l 'postgres' -c "psql -c 'CREATE DATABASE ${DB_NAME} OWNER ${DB_USERNAME}'"
    fi
}

main() {

    DB_NAME="${1:-"${DB_NAME}"}"
    DB_USERNAME="${2:-"${DB_USERNAME}"}"
    DB_SCHEMA_FILE="${3:-"${DB_SCHEMA_FILE}"}"

    check_create_db

    echo "${DB_USERNAME} LOGIN"
    su -l "${DB_USERNAME}" -c "psql '${DB_NAME}' -c '$(cat "${DB_SCHEMA_FILE}")'"
}

main "${@}"

####################
) ####END_SUBSHELL##
####################
