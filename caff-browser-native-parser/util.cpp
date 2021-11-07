#include <string>
#include <sstream>

#include "util.h"

using namespace std;

string uint64_to_string(uint64_t value) {
    ostringstream os;
    os << value;
    return os.str();
}

uint64_t parse_int_byte(const char *buffer, uint64_t start_index, uint64_t byte_number) {
    uint64_t result = 0;
    for (uint64_t i = start_index; i < start_index + byte_number; i++) {
        result |= (uint8_t) buffer[i] << (i - start_index) * 8;
    }
    return result;
}

string parse_string_byte(const char *buffer, uint64_t start_index, uint64_t byte_number) {
    string result;
    for (uint64_t i = start_index; i < start_index + byte_number; i++) {
        result += buffer[i];
    }
    return result;
}