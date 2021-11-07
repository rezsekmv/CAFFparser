#ifndef CAFF_BROWSER_NATIVE_PARSER_CIFF_H
#define CAFF_BROWSER_NATIVE_PARSER_CIFF_H

#include <string>
#include <vector>
#include <iostream>

#include "util.h"

using namespace std;

class ciff {
public:
    string magic;
    uint64_t header_size;
    uint64_t content_size;
    uint64_t width;
    uint64_t height;
    string caption;
    vector<string> tags;

    ciff(const string &p_magic, uint64_t headerSize, uint64_t contentSize, uint64_t p_width, uint64_t p_height,
         const string &p_caption, const vector<string> &p_tags){
        magic = p_magic;
        header_size = headerSize;
        content_size = contentSize;
        width = p_width;
        height = p_height;
        caption = p_caption;
        tags = p_tags;
    }

    void writeToJSON(string fname) {
        ofstream of(fname);
        of << "{\n";
        of << "\t\"magic\":\""<< magic << "\"\n";
        of << "\t\"header_size\":" << header_size << "\n";
        of << "\t\"content_size\":" << content_size << "\n";
        of << "\t\"width\":" << width << "\n";
        of << "\t\"height\":" << height << "\n";
        of << "\t\"caption\":\"" << caption << "\"\n";
        of << "\t\"tags\":[";
        for(int i = 0; i<tags.size()-1;i++) {
            of << "\"" << tags[i] << "\", ";
        }
        of << "\"" << tags[tags.size()-1] << "\"]\n";
        of << "}";

        of.close();
    }
};

pair<string, int> parse_caption(const char *buffer, uint64_t start_index, uint64_t max_end_index) {
    uint64_t caption_end_index = -1;
    for (uint64_t i = start_index; i <= max_end_index; i++) {
        if (buffer[i] == '\n') {
            caption_end_index = i;
            break;
        }
    }
    return make_pair(parse_string_byte(buffer, start_index, caption_end_index - start_index + 1), caption_end_index);
}

vector<string> parse_tags(const char *buffer, int start_index, uint64_t size) {
    vector<int> close_sign_indices;
    for (int i = start_index; i <= size; i++) {
        if (buffer[i] == '\0') {
            close_sign_indices.push_back(i);
        }
    }
    vector<string> tags;
    for (int i = 0; i < close_sign_indices.size(); i++) {
        int start = i == 0 ? start_index : close_sign_indices[i - 1] + 1;
        int byte_number = close_sign_indices[i] - start + 1;
        tags.push_back(parse_string_byte(buffer, start, byte_number));
    }
    return tags;
}

void parse_rgb_pixels(const char *buffer, uint64_t start_index, uint64_t byte_number, unsigned char *pixels) {
    for (uint64_t i = start_index; i < start_index + byte_number; i++) {
        pixels[i - start_index] = parse_int_byte(buffer, i, 1);
    }
}

void convert_to_ppm(const string &filename, uint64_t width, uint64_t height, unsigned char *pixels) {
    FILE *imageFile;

    imageFile = fopen(("../output-images/" + filename + ".ppm").c_str(), "wb");
    if (imageFile == nullptr) {
        perror("ERROR: Cannot open output file");
        exit(EXIT_FAILURE);
    }

    fprintf(imageFile, "P6\n");
    fprintf(imageFile, "%llu %llu\n", width, height);
    fprintf(imageFile, "255\n");
    fwrite(pixels, 1, width * height * 3, imageFile);
    fclose(imageFile);
}

void parse_ciff(const string &file_name, const char *buffer, uint64_t start_index) {
    string magic = parse_string_byte(buffer, start_index, 4);
    if (magic != "CIFF") {
        throw runtime_error("Invalid CIFF magic");
    }
    uint64_t header_size = parse_int_byte(buffer, start_index + 4, 8);
    uint64_t content_size = parse_int_byte(buffer, start_index + 12, 8);
    uint64_t width = parse_int_byte(buffer, start_index + 20, 8);
    uint64_t height = parse_int_byte(buffer, start_index + 28, 8);
    if (content_size != width * height * 3) {
        throw runtime_error("Invalid CIFF content size");
    }
    pair<string, int> caption_pair = parse_caption(buffer, start_index + 36, start_index + header_size - 1);
    string caption = caption_pair.first;
    int caption_end_index = caption_pair.second;
    vector<string> tags = parse_tags(buffer, caption_end_index + 1, start_index + header_size - 1);

    printf("Header: %llu\n", header_size);
    printf("Content Size: %llu\n", content_size);
    printf("Width: %llu\n", width);
    printf("Height: %llu\n", height);
    printf("Caption: %s", caption.c_str());
    printf("Tags: ");
    for (auto &tag: tags) {
        cout << tag << ", ";
    }

    ciff ciffObj(magic, header_size, content_size, width, height, caption, tags);

    unsigned char pixels[content_size];
    parse_rgb_pixels(buffer, start_index + header_size, content_size, pixels);
    convert_to_ppm(file_name, width, height, pixels);

    cout << endl << endl;

}

ciff parse_ciff_with_return(const string &file_name, const char *buffer, uint64_t start_index) {
    string magic = parse_string_byte(buffer, start_index, 4);
    if (magic != "CIFF") {
        throw runtime_error("Invalid CIFF magic");
    }
    uint64_t header_size = parse_int_byte(buffer, start_index + 4, 8);
    uint64_t content_size = parse_int_byte(buffer, start_index + 12, 8);
    uint64_t width = parse_int_byte(buffer, start_index + 20, 8);
    uint64_t height = parse_int_byte(buffer, start_index + 28, 8);
    if (content_size != width * height * 3) {
        throw runtime_error("Invalid CIFF content size");
    }
    pair<string, int> caption_pair = parse_caption(buffer, start_index + 36, start_index + header_size - 1);
    string caption = caption_pair.first;
    int caption_end_index = caption_pair.second;
    vector<string> tags = parse_tags(buffer, caption_end_index + 1, start_index + header_size - 1);

    printf("Header: %llu\n", header_size);
    printf("Content Size: %llu\n", content_size);
    printf("Width: %llu\n", width);
    printf("Height: %llu\n", height);
    printf("Caption: %s", caption.c_str());
    printf("Tags: ");
    for (auto &tag: tags) {
        cout << tag << ", ";
    }

    ciff ciffObj(magic, header_size, content_size, width, height, caption, tags);

    unsigned char pixels[content_size];
    parse_rgb_pixels(buffer, start_index + header_size, content_size, pixels);
    convert_to_ppm(file_name, width, height, pixels);

    cout << endl << endl;

    return ciffObj;
}

#endif //CAFF_BROWSER_NATIVE_PARSER_CIFF_H
