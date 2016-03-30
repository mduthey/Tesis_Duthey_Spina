#include <iostream>

using namespace std;

class Rectangle {
	int width, height;
public:
	void set_values(int x, int y);
	virtual int area() { return width*height; }
	Rectangle(int x, int y);
	Rectangle() {};
	~Rectangle() {};
};