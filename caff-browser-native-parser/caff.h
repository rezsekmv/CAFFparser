#ifndef CAFF_BROWSER_NATIVE_PARSER_CAFF_H
#define CAFF_BROWSER_NATIVE_PARSER_CAFF_H

#include "util.h"
#include "ciff.h"

void parse_caff(const char *buffer) {
    //CAFF Header
    uint8_t header_id = parse_int_byte(buffer, 0, 1);
    if (header_id != 1) {
        throw runtime_error("Invalid header ID");
    }
    uint64_t header_length = parse_int_byte(buffer, 1, 8);
    if (header_length != 20) {
        throw runtime_error("Invalid CAFF header length");
    }
    string magic = parse_string_byte(buffer, 9, 4);
    if (magic != "CAFF") {
        throw runtime_error("Invalid CAFF magic");
    }
    uint64_t header_size = parse_int_byte(buffer, 13, 8);
    if (header_size != 20) {
        throw runtime_error("Invalid CAFF Header header size");
    }
    uint64_t num_anim = parse_int_byte(buffer, 21, 8);

    printf("CAFF Header:\n");
    printf("Id: %d\n", header_id);
    printf("Length: %llu\n", header_length);
    printf("Magic: %s\n", magic.c_str());
    printf("Header size: %llu\n", header_size);
    printf("Num_anim: %llu\n\n", num_anim);

    //CAFF Credits
    uint8_t credit_id = parse_int_byte(buffer, 29, 1);
    if (credit_id != 2) {
        throw runtime_error("Invalid credit ID");
    }
    uint64_t credit_length = parse_int_byte(buffer, 30, 8);
    uint16_t year = parse_int_byte(buffer, 38, 2);
    uint8_t month = parse_int_byte(buffer, 40, 1);
    uint8_t day = parse_int_byte(buffer, 41, 1);
    uint8_t hour = parse_int_byte(buffer, 42, 1);
    uint8_t minute = parse_int_byte(buffer, 43, 1);
    uint64_t creator_size = parse_int_byte(buffer, 44, 8);
    string creator = parse_string_byte(buffer, 52, (int) creator_size);
    int creator_end_index = 52 + (int) creator_size;

    if (credit_length != 14 + creator_size) {
        throw runtime_error("Invalid CAFF Credit header size");
    }

    printf("CAFF Credits:\n");
    printf("Id: %d\n", credit_id);
    printf("Length: %llu\n", credit_length);
    printf("Year: %hu\n", year);
    printf("Month: %d\n", month);
    printf("Day: %d\n", day);
    printf("Hour: %d\n", hour);
    printf("Minute: %d\n", minute);
    printf("Creator size: %llu\n", creator_size);
    printf("Creator: %s\n\n", creator.c_str());

    //CAFF Animation
    printf("CAFF Animation:\n");
    uint64_t start = creator_end_index + 17;
    for (uint64_t image_number = 0; image_number < num_anim; image_number++) {
        uint8_t animation_id = parse_int_byte(buffer, creator_end_index, 1);
        if (animation_id != 3) {
            throw runtime_error("Invalid animation ID");
        }
        uint64_t animation_length = parse_int_byte(buffer, creator_end_index + 1, 8);
        uint64_t duration = parse_int_byte(buffer, creator_end_index + 9, 8);

        printf("Id: %d\n", animation_id);
        printf("Length: %llu\n", animation_length);
        printf("Duration: %llu\n", duration);

        string image_index = uint64_to_string(image_number);

        parse_ciff(image_index, buffer, start);
        start += animation_length + 9;
    }
}

#endif //CAFF_BROWSER_NATIVE_PARSER_CAFF_H
