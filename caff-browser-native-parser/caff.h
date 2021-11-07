#ifndef CAFF_BROWSER_NATIVE_PARSER_CAFF_H
#define CAFF_BROWSER_NATIVE_PARSER_CAFF_H

#include "util.h"
#include "ciff.h"
#include <fstream>

class caff {
public:
    // header
    uint8_t header_id;
    uint64_t header_length;
    string header_magic;
    uint64_t header_size;
    uint64_t num_anim;
    // credits
    uint8_t credit_id;
    uint64_t credit_length;
    uint16_t year;
    uint8_t month;
    uint8_t day;
    uint8_t hour;
    uint8_t minute;
    uint64_t creator_size;
    string creator;
    // animation
    vector<uint8_t> animation_ids;
    vector<uint64_t> animation_lengths;
    vector<uint64_t> animation_durations;
    vector<ciff> images;

    caff(uint8_t headerId, uint64_t headerLength, const string &headerMagic, uint64_t headerSize, uint64_t numAnim, uint8_t creditId,
         uint64_t creditLength, uint16_t p_year, uint8_t p_month, uint8_t p_day, uint8_t p_hour, uint8_t p_minute,
         uint64_t creatorSize, const string &p_creator, const vector<uint8_t> &animationIds,
         const vector<uint64_t> &animationLengths, const vector<uint64_t> &animationDurations, const vector<ciff> &ciffImgs) {
            header_id = headerId;
            header_length = headerLength;
            header_magic = headerMagic;
            header_size = headerSize;
            num_anim = numAnim;
            credit_id = creditId;
            credit_length = creditLength;
            year = p_year;
            month = p_month;
            day = p_day;
            hour = p_hour;
            minute = p_minute;
            creator_size = creatorSize;
            creator = p_creator;
            animation_ids = animationIds;
            animation_lengths = animationLengths;
            animation_durations = animationDurations;
            images = ciffImgs;
    }

    void writeToJSON(string fname) {
        ofstream of;
        of.open(fname);
        if(of.is_open()) {
            of << "{\n";
            of << "\t\"header\":{\n";
            of << "\t\t\"id\":" << header_id << "\n";
            of << "\t\t\"length\":" << header_length << "\n";
            of << "\t\t\"magic\":\"" << header_magic << "\"\n";
            of << "\t\t\"header_size\":" << header_size << "\n";
            of << "\t\t\"num_anim\":" << num_anim << "\n";
            of << "\t}\n";
            of << "\t\"credit\":{\n";
            of << "\t\t\"id\":" << credit_id << "\n";
            of << "\t\t\"length\":" << credit_length << "\n";
            of << "\t\t\"year\":" << year << "\n";
            of << "\t\t\"month\":" << month << "\n";
            of << "\t\t\"day\":" << day << "\n";
            of << "\t\t\"hour\":" << hour << "\n";
            of << "\t\t\"minute\":" << minute << "\n";
            of << "\t\t\"creator_length\":" << creator_size << "\n";
            of << "\t\t\"creator\":\"" << creator << "\"\n";
            of << "\t}\n";
            of << "\t\"animation\":{\n";
            of << "\t\t\"ids\":[";
            for (int i = 0; i < animation_ids.size() - 1; i++) {
                of << animation_ids[i] << ", ";
            }
            of << animation_ids[animation_ids.size() - 1] << "]\n";
            of << "\t\t\"lengths\":[";
            for (int i = 0; i < animation_lengths.size() - 1; i++) {
                of << animation_lengths[i] << ", ";
            }
            of << animation_lengths[animation_lengths.size() - 1] << "]\n";
            of << "\t\t\"ids\":[";
            for (int i = 0; i < animation_durations.size() - 1; i++) {
                of << animation_durations[i] << ", ";
            }
            of << animation_durations[animation_durations.size() - 1] << "]\n";
            of << "\t\t\"CIFFs\":[\n";
            for (int i = 0; i < images.size() - 1; i++) {
                of << "\t\t\t{\n";
                of << "\t\t\t\t\"magic\":\"" << images[i].magic << "\"\n";
                of << "\t\t\t\t\"header_size\":" << images[i].header_size << "\n";
                of << "\t\t\t\t\"content_size\":" << images[i].content_size << "\n";
                of << "\t\t\t\t\"width\":" << images[i].width << "\n";
                of << "\t\t\t\t\"height\":" << images[i].height << "\n";
                of << "\t\t\t\t\"caption\":\"" << images[i].caption << "\"\n";
                of << "\t\t\t\t\"tags\":[";
                for (int i = 0; i < images[i].tags.size() - 1; i++) {
                    of << "\"" << images[i].tags[i] << "\", ";
                }
                of << "\"" << images[i].tags[images[i].tags.size() - 1] << "\"]\n";
                of << "\t\t\t}";
            }
            of << "\t\t\t{\n";
            of << "\t\t\t\t\"magic\":\"" << images[images.size() - 1].magic << "\"\n";
            of << "\t\t\t\t\"header_size\":" << images[images.size() - 1].header_size << "\n";
            of << "\t\t\t\t\"content_size\":" << images[images.size() - 1].content_size << "\n";
            of << "\t\t\t\t\"width\":" << images[images.size() - 1].width << "\n";
            of << "\t\t\t\t\"height\":" << images[images.size() - 1].height << "\n";
            of << "\t\t\t\t\"caption\":\"" << images[images.size() - 1].caption << "\"\n";
            of << "\t\t\t\t\"tags\":[";
            for (int i = 0; i < images[images.size() - 1].tags.size() - 1; i++) {
                of << "\"" << images[images.size() - 1].tags[i] << "\", ";
            }
            of << "\"" << images[images.size() - 1].tags[images[images.size() - 1].tags.size() - 1] << "\"]\n";
            of << "\t\t\t}";
            of << "]\n";
            of << "\t}\n";
            of << "}";

            of.close();
        } else {
            throw runtime_error("File is not open!");
        }
    }
};

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
    vector<uint8_t> anIDs;
    vector<uint64_t> anLens;
    vector<uint64_t> anDurs;
    vector<ciff> ciffImgs;

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
        anIDs.push_back(animation_id);
        printf("Length: %llu\n", animation_length);
        anLens.push_back(animation_length);
        printf("Duration: %llu\n", duration);
        anDurs.push_back(duration);

        string image_index = uint64_to_string(image_number);

        ciff ciffObj = parse_ciff_with_return(image_index, buffer, start);
        ciffImgs.push_back(ciffObj);
        start += animation_length + 9;
    }

    caff caffObj(header_id, header_length, magic.c_str(), header_size, num_anim, credit_id, credit_length,
                 year, month, day, hour, minute, creator_size, creator.c_str(), anIDs, anLens, anDurs, ciffImgs);

    // kipróbálni!
    caffObj.writeToJSON("../caffObj.json");
}

#endif //CAFF_BROWSER_NATIVE_PARSER_CAFF_H
