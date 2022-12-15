
SUMMARY = "Conversion to/from half-precision floating point formats"
HOMEPAGE = "https://github.com/luxonis/FP16"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=998fb0b16ad8a4fb8bd41bf3faf2d21c"

# See: https://github.com/luxonis/depthai-core/blob/v2.15.2/cmake/Hunter/config.cmake
SRC_URI = "git://github.com/luxonis/FP16.git;branch=master"
SRCREV = "c911175d2717e562976e606c6e5f799bf40cf94e"

S = "${WORKDIR}/git"

# TODO: this repo is downloaded during configure stage:
# https://github.com/Maratyszcza/psimd

EXTRA_OECMAKE:append = " \
    -DFP16_BUILD_TESTS=OFF \
    -DFP16_BUILD_BENCHMARKS=OFF \
    "

inherit cmake
