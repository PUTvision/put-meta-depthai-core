
SUMMARY = "libnop: C++ Native Object Protocols"
HOMEPAGE = "https://github.com/luxonis/libnop"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4e5640559f0849cf863b1dc47bac078b"

# See: https://github.com/luxonis/depthai-core/blob/v2.15.2/cmake/Hunter/config.cmake
SRC_URI = "git://github.com/luxonis/libnop.git;branch=develop"
SRCREV = "ec8f75aa4df3454f73b5d7a7fe0680f3701b1744"

S = "${WORKDIR}/git"

inherit cmake
