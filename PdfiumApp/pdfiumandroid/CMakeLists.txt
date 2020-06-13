cmake_minimum_required(VERSION 3.4.1)

include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp/include)
aux_source_directory(${CMAKE_SOURCE_DIR}/src/main/cpp PDF_WRAP_SRC)

SET(APP_LIBS_DIR ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI})

#add_library(
#        cpp_shared
 #       SHARED
#        IMPORTED
#)
add_library(
        modft2
        SHARED
        IMPORTED
)
add_library(
        modpdfium
        SHARED
        IMPORTED
)
add_library(
        modpng
        SHARED
        IMPORTED
)
#set_target_properties(cpp_shared PROPERTIES IMPORTED_LOCATION ${APP_LIBS_DIR}/libc++_shared.so
#)
set_target_properties(modft2 PROPERTIES IMPORTED_LOCATION ${APP_LIBS_DIR}/libmodft2.so
)
set_target_properties(modpdfium PROPERTIES IMPORTED_LOCATION ${APP_LIBS_DIR}/libmodpdfium.so
)
set_target_properties(modpng PROPERTIES IMPORTED_LOCATION ${APP_LIBS_DIR}/libmodpng.so
)
message($modpng)
#
add_library(
        jniPdfium
        SHARED
        ${PDF_WRAP_SRC}
)

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

target_link_libraries(
      jniPdfium
      #cpp_shared
      modpng
      modft2
      modpdfium
      ${log-lib}
      -landroid
      -ljnigraphics
)