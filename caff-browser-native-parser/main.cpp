#include <fstream>

#include "caff_parser.h"

using namespace std;

int main(int argc, char* argv[]) {
    if (argc != 2) {
        throw runtime_error("No filename provided");
    }

    string filename = argv[1];

    ifstream is("../images/" + filename, ifstream::binary);
    if (is) {
        is.seekg(0, is.end);
        int file_size = is.tellg();
        is.seekg(0, is.beg);

        char *buffer = new char[file_size];

        is.read(buffer, file_size);
        is.close();

        parse_caff(buffer);

        delete[] buffer;
    } else {
        throw runtime_error("Invalid file, filename:" + filename);
    }

    return 0;
}