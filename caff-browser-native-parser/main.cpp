#include <fstream>

#include "caff_parser.h"

using namespace std;

int main(int argc, char* argv[]) {
    if (argc != 3) {
        throw runtime_error("No path or filename provided");
    }

    string path = argv[1];
    string filename = argv[2];

    ifstream is(path + "\\caff-browser-backend\\src\\main\\resources\\static\\"+ filename + ".caff", ifstream::binary);
    if (is) {
        is.seekg(0, is.end);
        int file_size = is.tellg();
        is.seekg(0, is.beg);

        char *buffer = new char[file_size];

        is.read(buffer, file_size);
        is.close();

        parse_caff(buffer, path, filename);

        delete[] buffer;
    } else {
        throw runtime_error("Invalid file:" + path + "\\caff-browser-backend\\src\\main\\resources\\static\\"+ filename + ".caff");
    }

    return 0;
}