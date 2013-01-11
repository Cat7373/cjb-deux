#!/bin/bash
set -o nounset
set -o allexport
set -o errexit


MOD_VERSION="1.0-SNAPSHOT"
MC_VERSION="1.4.7"

# project style: $BASEDIR/src/main/bin
BASEDIR=$(readlink -f "$CMD_HOME/../../..")
BUILD_DIR="$BASEDIR/target"
MOD_ZIP="CJB-Deux-${MC_VERSION}-${MOD_VERSION}.zip"

CJB_HOME="$BASEDIR"
CJB_SRC_DIR="$CJB_HOME/src/main/java"
CJB_RESOURCE_DIR="$CJB_HOME/src/main/resources"
CJB_TEXTURE_DIR="$CJB_HOME/src/main/textures"

FORGE_HOME="$BASEDIR/forge"

MCP_HOME="$BASEDIR/forge/mcp"
MCP_SRC_DIR="$MCP_HOME/src/minecraft"
MCP_REOBF_DIR="$MCP_HOME/reobf/minecraft"

PAPI_HOME="$BASEDIR/player-api"
PAPI_SRC_DIR="$PAPI_HOME/src/minecraft"
