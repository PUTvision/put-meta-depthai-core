
SUMMARY = "DepthAI C++ Library"
HOMEPAGE = "https://github.com/luxonis/depthai-core"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=39a6484daa66eaf84c7ee9c45e34db02"

# These resources are required as resources, note that these are very coupled to the version of depthai-core:
# See: cmake/Depthai/DepthaiBootloaderConfig.cmake
#   depthai bootloader: release-0.0.17
#   https://artifacts.luxonis.com/artifactory/luxonis-myriad-release-local/depthai-bootloader/0.0.17/depthai-bootloader-fwp-0.0.17.tar.xz
# See: cmake/Depthai/DepthaiDownloader.cmake
#   depthai device side
#   https://artifacts.luxonis.com/artifactory/luxonis-myriad-snapshot-local/depthai-device-side/afe8d00c868344c73ee897e26278f80a444b9763/depthai-device-fwp-afe8d00c868344c73ee897e26278f80a444b9763.tar.xz

SRC_URI = "https://github.com/luxonis/${BPN}/releases/download/v${PV}/${BPN}-v${PV}.tar.gz;name=src \
           https://artifacts.luxonis.com/artifactory/luxonis-myriad-release-local/depthai-bootloader/0.0.17/depthai-bootloader-fwp-0.0.17.tar.xz;unpack=0;name=bootloader-fwp \
           https://artifacts.luxonis.com/artifactory/luxonis-myriad-snapshot-local/depthai-device-side/afe8d00c868344c73ee897e26278f80a444b9763/depthai-device-fwp-afe8d00c868344c73ee897e26278f80a444b9763.tar.xz;unpack=0;name=device-fwp \
           file://80-movidius.rules \
           file://0001-Adapt-cmake-configuration-to-be-compatible-with-open.patch \
           file://0002-Remove-downloading-from-CMakeLists.txt.patch \
           "

# These three downloads are a coupled set, when updating the src, update the other blobs as well!
SRC_URI[src.sha256sum] = "dea755d06ed19a1ea57cf6474a80c47302d7edd27060234447c17a93b9602877"
SRC_URI[bootloader-fwp.sha256sum] = "27ea0d797498f7fff1d82e9524ea6a244a5998424b523eab9b353944548bcbef"
SRC_URI[device-fwp.sha256sum] = "5cc6e63d5c10430c3cc73f74278c928a39d36b860160403962e898e63ef96046"

S = "${WORKDIR}/${BPN}-v${PV}"

inherit cmake

# DEPTHAI_BINARIES_RESOURCE_COMPILE=OFF should disable the inclusion of bootloader and application as a resource.
# HUNTER_ENABLED=OFF disables the hunter C++ package manager (since we use bitbake)
# DEPTHAI_ENABLE_BACKWARD=OFF disables the use of a fancy C++ backtrace printing library called 'backward'
# BUILD_SHARED_LIBS=ON ensures we build shared libraries
# using DEPTHAI_BOOTLOADER_SHARED_LOCAL prevents GIT submodule initialization
EXTRA_OECMAKE:append = " \
    -DHUNTER_ENABLED=OFF \
    -DCMAKE_BUILD_TYPE=Release \
    -DDEPTHAI_CLANG_FORMAT=OFF \
    -DDEPTHAI_CLANG_TIDY=OFF \
    -DDEPTHAI_ENABLE_BACKWARD=OFF \
    -DDEPTHAI_BINARIES_RESOURCE_COMPILE=ON \
    -DBUILD_SHARED_LIBS=ON \
    -DDEPTHAI_BOOTLOADER_SHARED_LOCAL=${S}/shared/depthai-bootloader-shared \
    -DDEPTHAI_SHARED_LOCAL=${S}/shared/depthai-shared \
    -DDEPTHAI_BUILD_EXAMPLES=OFF \
    -DDEPTHAI_BUILD_TESTS=OFF \
    -DDEPTHAI_BUILD_DOCS=OFF \
    -DDEPTHAI_BOOTLOADER_FWP=${WORKDIR}/depthai-bootloader-fwp-0.0.17.tar.xz \
    -DDEPTHAI_DEVICE_FWP=${WORKDIR}/depthai-device-fwp-afe8d00c868344c73ee897e26278f80a444b9763.tar.xz \
    "

DEPENDS = " \
    bzip2 \
    spdlog \
    libarchive \
    zlib \
    xz \
    nlohmann-json \
    luxonis-xlink \
    luxonis-libnop \
    luxonis-fp16 \
"

do_install:append() {
    # These permissions are required, otherwise the OAK-D device will report as X_LINK_UNBOOTED
    install -Dm 0644 ${WORKDIR}/80-movidius.rules ${D}${sysconfdir}/udev/rules.d/80-movidius.rules
}

# -dev package depthai-core-dev contains non-symlink .so '/usr/lib/libdepthai-core.so' [dev-elf]
FILES_SOLIBSDEV = ""
FILES:${PN} += " \
    ${libdir}/lib*${SOLIBSDEV} \
    ${sysconfdir}/udev/rules.d/80-movidius.rules \
"
