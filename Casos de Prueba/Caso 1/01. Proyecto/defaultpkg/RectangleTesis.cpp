#include "RectangleTesis.h"

void Rectangle::set_values(int x, int y) {
	width = x;
	height = y;
}

Rectangle::Rectangle(int x, int y) {
	set_values(x, y);
}

void display(int n) {
	printf("Area: %d", n);
}

int main() {
	Rectangle rect;
	rect.set_values(3, 4);
	display(rect.area());
	return 0;
}