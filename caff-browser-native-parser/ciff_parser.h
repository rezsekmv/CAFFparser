#ifndef CAFF_BROWSER_NATIVE_PARSER_CIFF_PARSER_H
#define CAFF_BROWSER_NATIVE_PARSER_CIFF_PARSER_H

#include <fstream>
#include <vector>
#include "ciff.h"

using namespace std;

pair<string, int> parse_caption(const char *buffer, uint64_t start_index, uint64_t max_end_index);

vector<string> parse_tags(const char *buffer, int start_index, uint64_t size);

void parse_rgb_pixels(const char *buffer, uint64_t start_index, uint64_t byte_number, unsigned char *pixels);

void convert_to_ppm(const string &path, const string &filename, uint64_t width, uint64_t height, unsigned char *pixels);

CIFF parse_ciff(const string &path, const string &filename, const char *buffer, uint64_t start_index);

#endif //CAFF_BROWSER_NATIVE_PARSER_CIFF_PARSER_H
