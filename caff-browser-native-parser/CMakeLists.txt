cmake_minimum_required(VERSION 3.20)
project(caff-browser-native-parser)

set(CMAKE_CXX_STANDARD 14)

add_executable(caff-browser-native-parser main.cpp caff.h ciff.h util.h)
