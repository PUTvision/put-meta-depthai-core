
SUMMARY = "A cross-platform library for communicating with devices over various physical links."
HOMEPAGE = "https://github.com/luxonis/XLink"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://CMakeLists.txt;beginline=2;endline=2;md5=653b254f286b881bdce1d14afdede628"

# See: https://github.com/luxonis/depthai-core/blob/v2.15.2/cmake/Hunter/config.cmake
SRC_URI = "git://github.com/luxonis/XLink.git;branch=develop \
           file://0001-Use-pkg-config-to-find-libusb-1.0.patch \
           "
SRCREV = "e30ce8053f1795f5e63c1f4bd03e722153299c15"

S = "${WORKDIR}/git"

EXTRA_OECMAKE:append = " \
    -DCMAKE_BUILD_TYPE=Release \
    -DBUILD_SHARED_LIBS=ON \
    "

DEPENDS = " \
    libusb1 \
"

inherit cmake

# TODO: this should not happen, but xlink SO file is unversioned.
FILES_SOLIBSDEV = ""
FILES:${PN} += " \
    ${libdir}/lib*${SOLIBSDEV} \
"
