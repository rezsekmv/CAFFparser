#ifndef CAFF_BROWSER_NATIVE_PARSER_CAFF_H
#define CAFF_BROWSER_NATIVE_PARSER_CAFF_H

class CAFF {
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
    vector<CIFF> images;

    CAFF(uint8_t headerId, uint64_t headerLength, const string &headerMagic, uint64_t headerSize, uint64_t numAnim, uint8_t creditId,
         uint64_t creditLength, uint16_t p_year, uint8_t p_month, uint8_t p_day, uint8_t p_hour, uint8_t p_minute,
         uint64_t creatorSize, const string &p_creator, const vector<uint8_t> &animationIds,
         const vector<uint64_t> &animationLengths, const vector<uint64_t> &animationDurations, const vector<CIFF> &ciffImgs
    ) {
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

    void write_to_json(const string &path, const string &filename) {
        FILE *file;
        file = fopen((path + "\\caff-browser-native-parser\\output-json\\" + filename +"-json.json").c_str(), "wb");
        if (file == nullptr) {
            perror("ERROR: Cannot open output file");
            exit(EXIT_FAILURE);
        }
        fprintf(file, "{\n");
        fprintf(file, "\t\"header\": {\n");
        fprintf(file, "\t\t\"id\": %d,\n", header_id);
        fprintf(file, "\t\t\"length\": %llu,\n", header_length);
        fprintf(file, "\t\t\"magic\": \"%s\",\n", header_magic.c_str());
        fprintf(file, "\t\t\"header_size\": %llu,\n", header_size);
        fprintf(file, "\t\t\"num_anim\": %llu\n", num_anim);
        fprintf(file, "\t},\n");
        fprintf(file, "\t\"credit\": {\n");
        fprintf(file, "\t\t\"id\": %d,\n", credit_id);
        fprintf(file, "\t\t\"length\": %llu,\n", credit_length);
        fprintf(file, "\t\t\"year\": %hu,\n", year);
        fprintf(file, "\t\t\"month\": %d,\n", month);
        fprintf(file, "\t\t\"day\": %d,\n", day);
        fprintf(file, "\t\t\"hour\": %d,\n", hour);
        fprintf(file, "\t\t\"minute\": %d,\n", minute);
        fprintf(file, "\t\t\"creator_length\": %llu,\n", creator_size);
        fprintf(file, "\t\t\"creator\": \"%s\"\n", creator.c_str());
        fprintf(file, "\t},\n");
        fprintf(file, "\t\"animation\": {\n");
        fprintf(file, "\t\t\"ids\": [");
        for (int i = 0; i < animation_ids.size(); i++) {
            if (i == animation_ids.size() - 1) {
                fprintf(file, "%d],\n", animation_ids[i]);
            } else {
                fprintf(file, "%d, ", animation_ids[i]);
            }
        }
        fprintf(file, "\t\t\"lengths\": [");
        for (int i = 0; i < animation_lengths.size(); i++) {
            if (i == animation_lengths.size() - 1) {
                fprintf(file, "%llu],\n", animation_lengths[i]);
            } else {
                fprintf(file, "%llu, ", animation_lengths[i]);
            }
        }
        fprintf(file, "\t\t\"durations\": [");
        for (int i = 0; i < animation_durations.size(); i++) {
            if (i == animation_durations.size() - 1) {
                fprintf(file, "%llu],\n", animation_durations[i]);
            } else {
                fprintf(file, "%llu, ", animation_durations[i]);
            }
        }
        fprintf(file, "\t\t\"CIFFs\": [\n");
        for (int i = 0; i < images.size(); i++) {
            fprintf(file, "\t\t\t{\n");
            fprintf(file, "\t\t\t\t\"magic\": \"%s\",\n", images[i].magic.c_str());
            fprintf(file, "\t\t\t\t\"header_size\": %llu,\n", images[i].header_size);
            fprintf(file, "\t\t\t\t\"content_size\": %llu,\n", images[i].content_size);
            fprintf(file, "\t\t\t\t\"width\": %llu,\n", images[i].width);
            fprintf(file, "\t\t\t\t\"height\": %llu,\n", images[i].height);
            fprintf(file, "\t\t\t\t\"caption\": \"%s\",\n", images[i].caption.c_str());
            fprintf(file, "\t\t\t\t\"tags\":[");
            for (int j = 0; j < images[i].tags.size(); j++) {
                if (j == images[i].tags.size() - 1) {
                    fprintf(file, "\"%s\"]\n", images[i].tags[j].c_str());
                } else {
                    fprintf(file, "\"%s\", ", images[i].tags[j].c_str());
                }
            }
            if (i == images.size() - 1) {
                fprintf(file, "\t\t\t}\n");
            } else {
                fprintf(file, "\t\t\t},\n");
            }
        }
        fprintf(file, "\t\t]\n");
        fprintf(file, "\t}\n");
        fprintf(file, "}");

        fclose(file);
    }

    virtual ~CAFF() = default;
};

#endif //CAFF_BROWSER_NATIVE_PARSER_CAFF_H
