#include <fstream>

#include "caff.h"

using namespace std;

int main() {
    ifstream is("../images/2.caff", ifstream::binary);
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
        throw runtime_error("Invalid file");
    }

    return 0;
}