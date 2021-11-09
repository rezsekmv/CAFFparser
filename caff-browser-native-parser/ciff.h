#ifndef CAFF_BROWSER_NATIVE_PARSER_CIFF_H
#define CAFF_BROWSER_NATIVE_PARSER_CIFF_H

using namespace std;

class CIFF {
public:
    string magic;
    uint64_t header_size;
    uint64_t content_size;
    uint64_t width;
    uint64_t height;
    string caption;
    vector<string> tags;

    CIFF(const string &p_magic, uint64_t p_header_size, uint64_t p_content_size, uint64_t p_width, uint64_t p_height,
         const string &p_caption, const vector<string> &p_tags
    ) {
        magic = p_magic;
        header_size = p_header_size;
        content_size = p_content_size;
        width = p_width;
        height = p_height;
        caption = p_caption;
        tags = p_tags;
    }

    virtual ~CIFF() = default;
};

#endif //CAFF_BROWSER_NATIVE_PARSER_CIFF_H
