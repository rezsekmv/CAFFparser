#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

class Color {
public:
    uint8_t red;
    uint8_t green;
    uint8_t blue;
};

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

vector<Color> parse_rgb_pixels(const char *buffer, uint64_t start_index, uint64_t byte_number) {
    vector<Color> pixels;
    for (uint64_t i = start_index; i < start_index + byte_number - 1; i += 3) {
        Color color{};
        color.red = parse_int_byte(buffer, i, 1);
        color.green = parse_int_byte(buffer, i + 1, 1);
        color.blue = parse_int_byte(buffer, i + 2, 1);
        pixels.push_back(color);
    }
    return pixels;
}

void parse_ciff(const char *buffer, uint64_t start_index) {
    string magic = parse_string_byte(buffer, start_index, 4);
    if (magic != "CIFF") {
        throw std::runtime_error("Invalid CIFF magic");
    }
    uint64_t header_size = parse_int_byte(buffer, start_index + 4, 8);
    uint64_t content_size = parse_int_byte(buffer, start_index + 12, 8);
    uint64_t width = parse_int_byte(buffer, start_index + 20, 8);
    uint64_t height = parse_int_byte(buffer, start_index + 28, 8);
    pair<string, int> caption_pair = parse_caption(buffer, start_index + 36, start_index + header_size - 1);
    string caption = caption_pair.first;
    int caption_end_index = caption_pair.second;
    vector<string> tags = parse_tags(buffer, caption_end_index + 1, start_index + header_size - 1);

    printf("Header: %llu\n", header_size);
    printf("Content Size: %llu\n", content_size);
    printf("Width: %llu\n", width);
    printf("Height: %llu\n", height);
    printf("Caption: %s\n", caption.c_str());
    for (auto &tag: tags) {
        cout << tag << endl;
    }

    vector<Color> pixels = parse_rgb_pixels(buffer, start_index + header_size, content_size);
    printf("%zu db pixel:\n", pixels.size());
    for (int i = 0; i < 10/*pixels.size()*/; i++) {
        printf("%d - %d - %d\n", pixels[i].red, pixels[i].green, pixels[i].blue);
    }
}

void parse_caff(const char *buffer) {
    uint8_t header_id = parse_int_byte(buffer, 0, 1);
    if (header_id != 1) {
        throw std::runtime_error("Invalid header ID");
    }
    uint64_t header_length = parse_int_byte(buffer, 1, 8);
    string magic = parse_string_byte(buffer, 9, 4);
    if (magic != "CAFF") {
        throw std::runtime_error("Invalid CAFF magic");
    }
    uint64_t header_size = parse_int_byte(buffer, 13, 8);
    uint64_t num_anim = parse_int_byte(buffer, 21, 8);
    uint8_t credit_id = parse_int_byte(buffer, 29, 1);
    if (credit_id != 2) {
        throw std::runtime_error("Invalid credit ID");
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

    printf("Id: %d\n", header_id);
    printf("Length: %llu\n", header_length);
    printf("Magic: %s\n", magic.c_str());
    printf("Header size: %llu\n", header_size);
    printf("Num_anim: %llu\n", num_anim);
    printf("Id: %d\n", credit_id);
    printf("Length: %llu\n", credit_length);
    printf("Year: %hu\n", year);
    printf("Month: %d\n", month);
    printf("Day: %d\n", day);
    printf("Hour: %d\n", hour);
    printf("Minute: %d\n", minute);
    printf("Creator size: %llu\n", creator_size);
    printf("Creator: %s\n", creator.c_str());

    uint64_t start = creator_end_index + 17;
    for (uint64_t image_number = 0; image_number < num_anim; image_number++) {
        uint8_t animation_id = parse_int_byte(buffer, creator_end_index, 1);
        if (animation_id != 3) {
            throw std::runtime_error("Invalid animation ID");
        }
        uint64_t animation_length = parse_int_byte(buffer, creator_end_index + 1, 8);
        uint64_t duration = parse_int_byte(buffer, creator_end_index + 9, 8);

        printf("Id: %d\n", animation_id);
        printf("Length: %llu\n", animation_length);
        printf("Duration: %llu\n", duration);

        parse_ciff(buffer, start);
        start += animation_length + 9;
    }

}

int main() {
    ifstream is("../images/2.caff", ifstream::binary);
    if (is) {
        is.seekg(0, is.end);
        int file_size = is.tellg();
        is.seekg(0, is.beg);

        char *buffer = new char[file_size];

        printf("File size: %d\n", file_size);

        is.read(buffer, file_size);
        is.close();

        parse_caff(buffer);

        delete[] buffer;
    } else {
        throw std::runtime_error("Invalid file");
    }

    return 0;
}