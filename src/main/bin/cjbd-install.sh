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
Simple description

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

function install_forge() {
    if [ ! -d "$FORGE_HOME" ]; then
	echo "> forge directory not found: $FORGE_HOME"
	exit_usage 1;
    fi

    pushd "$FORGE_HOME"
    cmd /c "install.cmd"
    popd
}

install_forge
