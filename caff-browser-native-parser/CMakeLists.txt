cmake_minimum_required(VERSION 3.20)
project(caff-browser-native-parser)

set(CMAKE_CXX_STANDARD 14)

add_executable(caff-browser-native-parser main.cpp caff.h caff_parser.cpp caff_parser.h ciff.h ciff_parser.cpp ciff_parser.h util.cpp util.h)
