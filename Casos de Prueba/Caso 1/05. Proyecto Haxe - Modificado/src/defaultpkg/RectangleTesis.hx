package defaultpkg; 
class Rectangle 
{
	var width:Int; 
	var height:Int; 
	public function set_values (x : Int, y : Int)  : Void {
		width = x;
		height = y;
	} 
	public function area ()  : Int {
		return width * height;
	} 
	public function new (?x : Int, ?y : Int)
	{
		x = (x == null) ? 0 : x;
		y = (y == null) ? 0 : y;
		set_values(x, y);
	} 
}  
class RectangleTesis 
{
	public static function display (n : Int)  : Void {
		Sys.println("Area: "+n);
	} 
	public static function main ()  : Int {
		var rect : Rectangle  = new Rectangle();
		rect.set_values(3, 4);
		display(rect.area());
		return 0;
	} 
}  
