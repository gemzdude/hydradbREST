# Regression Server

## Building a new regression server distribution

Regression Server uses gradle to build. To compile, run './gradlew build'

To assemble a distribution for deployment, run './gradlew assembleDist'.

This will create a zip file in regression-assembly/build/distributions/

##Running regression server locally

Unarchive the distribution and run the following commands:

```bash
cd snapshot-<sha>/antbuild
export REGR_SERVER=`pwd`
```
The regression server can be started with `launchpad.sh [<local directory> <portNum>]` which is located in regression-server-1.0/antbuild/bin.

The local directory is a directory that can contain overwrites and also will be used to output log files to.

The port number typically is set to 8123 but can be set to any available port.
