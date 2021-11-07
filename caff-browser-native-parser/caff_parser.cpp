#include <iostream>
#include <string>

#include "ciff_parser.h"
#include "util.h"
#include "caff_parser.h"
#include "caff.h"

using namespace std;

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

    //CAFF Animation
    vector<uint8_t> animation_ids;
    vector<uint64_t> animation_lengths;
    vector<uint64_t> animation_durations;
    vector<ciff> ciff_images;

    uint64_t start = creator_end_index + 17;
    for (uint64_t image_number = 0; image_number < num_anim; image_number++) {
        uint8_t animation_id = parse_int_byte(buffer, creator_end_index, 1);
        if (animation_id != 3) {
            throw runtime_error("Invalid animation ID");
        }
        uint64_t animation_length = parse_int_byte(buffer, creator_end_index + 1, 8);
        uint64_t duration = parse_int_byte(buffer, creator_end_index + 9, 8);

        animation_ids.push_back(animation_id);
        animation_lengths.push_back(animation_length);
        animation_durations.push_back(duration);

        string image_index = uint64_to_string(image_number);

        ciff ciff_image = parse_ciff(image_index, buffer, start);
        ciff_images.push_back(ciff_image);
        start += animation_length + 9;
    }

    caff caff_image(header_id, header_length, magic, header_size, num_anim, credit_id, credit_length,
                    year, month, day, hour, minute, creator_size, creator, animation_ids, animation_lengths, animation_durations, ciff_images);

    caff_image.write_to_json("output-json");
}