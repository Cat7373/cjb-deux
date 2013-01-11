#!/bin/bash
set -o nounset
set -o errexit
set -o xtrace

# self references
CMD=$(readlink -f $0)
CMD_NAME=$(basename $CMD)
CMD_HOME=$(dirname $CMD)

# configuration
source "$CMD_HOME/cjbd-config.sh"


function exit_usage() { print_usage; exit $1; }

function print_usage() {
cat <<_EOF
USAGE: 
Prepare the MOD dir for use with a new forge

  -s SERVER        x, y, z
  -v VERSION       p, d, q

X options are mandatory

Example:
  $CMD_NAME

_EOF
}

function parse_opts() {

    while getopts "s:v:" OPTION
    do
	case $OPTION in
	    s)  SERVER_TYPE=$OPTARG;;
	    v)  VERSION=$OPTARG;;
	esac
    done

    [ -z "$SERVER_TYPE" -o -z "$VERSION" ] && exit_usage 1

    echo ""
}


function prepare_out_dir() {
    # create output dir
    mkdir -p "$BUILD_DIR"
}

function backup_mcp_src() {
    if [ ! -f "$MCP_HOME/src.backup.tgz" ]; then
	pushd "$MCP_HOME"
	tar -zcf "src.backup.tgz" "src"
	popd
    fi
}
function revert_mcp_src() {
    if [ -f "$MCP_HOME/src.backup.tgz" ]; then
	pushd "$MCP_HOME"
	rm -rf "src"
	tar -zxf "src.backup.tgz"
	popd
    fi
}



# creates mod .class files in MCP_HOME/reobf (as of MCP-7.25)
function recompile_and_obfuscate() {
    pushd "$MCP_HOME" > /dev/null
    cmd /c "recompile.bat"
    cmd /c "reobfuscate.bat"
    popd > /dev/null
}



function overlay_mod_src() {
    cp -r "$CJB_SRC_DIR/." "$MCP_SRC_DIR"
    cp -r "$PAPI_SRC_DIR/." "$MCP_SRC_DIR"
}

function overlay_mod_resources() {
    cp -r "$CJB_TEXTURE_DIR/." "$MCP_REOBF_DIR"
}
function copy_mod_sounds() {
    local RSRC="$BUILD_DIR/resources"
    mkdir -p $RSRC
    cp -r "$CJB_RESOURCE_DIR/." "$RSRC"
}


function create_mod_zip() {
    local MODS="$BUILD_DIR/mods"
    local ZIP="$MODS/$MOD_ZIP"
    echo ">   Creating Mod: $ZIP"

    mkdir -p "$MODS"

    # cd to mods dir to get classes in root of archive
    pushd "$MCP_REOBF_DIR" > /dev/null
    # R: recursive from current
    zip -DRq $ZIP '*'
    popd > /dev/null
}

function create_artifact() {
    local ZIP="$BUILD_DIR/CJB-Deux-${MC_VERSION}-${MOD_VERSION}.zip"
    echo "> Creating Artifact: $ZIP"

    # cd to target dir to get mods/, resources/, etc in root
    pushd "$BUILD_DIR" > /dev/null
    # r: recursive from specified
    zip -Drq $ZIP "mods" "resources"
    popd > /dev/null
}



prepare_out_dir

backup_mcp_src

overlay_mod_src
recompile_and_obfuscate

# build mod-zips and temp-install dir
overlay_mod_resources
create_mod_zip 
copy_mod_sounds

# create artifact
create_artifact


revert_mcp_src
