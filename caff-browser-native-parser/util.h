#ifndef CAFF_BROWSER_NATIVE_PARSER_UTIL_H
#define CAFF_BROWSER_NATIVE_PARSER_UTIL_H

using namespace std;

string uint64_to_string(uint64_t value);

uint64_t parse_int_byte(const char *buffer, uint64_t start_index, uint64_t byte_number);

string parse_string_byte(const char *buffer, uint64_t start_index, uint64_t byte_number);

#endif //CAFF_BROWSER_NATIVE_PARSER_UTIL_H
