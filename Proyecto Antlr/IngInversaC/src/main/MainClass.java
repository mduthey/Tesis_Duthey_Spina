package main;

import generic.Compiler;
import generic.PromiseController;
import generic.TypesController;

public class MainClass {

	public static void main(String[] args) {
		if(args.length < 3){
			String name = new java.io.File(MainClass.class.getProtectionDomain()
					  .getCodeSource()
					  .getLocation()
					  .getPath()).getName();
			System.out.println("Uso: java -jar "+name+".jar nombre carpeta salida");
			System.out.println("\tnombre: Nombre del proyecto");
			System.out.println("\tcarpeta: Carpeta donde se encuentra el proyecto");
			System.out.println("\tsalida: Nombre de archivo xmi");
			return ;
		}
		int MegaBytes = 10241024;
		//Compiler c = new Compiler("Proyecto Ejemplo", "./project/example");
		//Compiler c = new Compiler("Proyecto PingPong", "./project/PingPong");
		//Compiler c = new Compiler("Proyecto Tetris", "./project/Tetris");
		//Compiler c = new Compiler("Proyecto Tetris", "./project/pruebas");
		long freeMemory = Runtime.getRuntime().freeMemory()/MegaBytes;
        long totalMemory = Runtime.getRuntime().totalMemory()/MegaBytes;
        long maxMemory = Runtime.getRuntime().maxMemory()/MegaBytes;
        System.out.println("====================Antes de ejecutar=====================");
        System.out.println("Free Memory: "+freeMemory+" MB");
        System.out.println("Total Memory: "+totalMemory+" MB");
        System.out.println("Max Memory: "+maxMemory+" MB");
        long execDuration = System.currentTimeMillis();
        Compiler c = new Compiler(args[0], args[1]);
		c.execute();
		try {
			c.saveToFile(args[2]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		execDuration = System.currentTimeMillis() - execDuration;
		freeMemory = Runtime.getRuntime().freeMemory()/MegaBytes;
        totalMemory = Runtime.getRuntime().totalMemory()/MegaBytes;
        maxMemory = Runtime.getRuntime().maxMemory()/MegaBytes;
        System.out.println("===================Despues de ejecutar====================");
        System.out.println("Free Memory: "+freeMemory+" MB");
        System.out.println("Total Memory: "+totalMemory+" MB");
        System.out.println("Max Memory: "+maxMemory+" MB");
        System.out.println("==================Duracion de ejecucion===================");
        System.out.println("Tiempo en milisegundos: "+execDuration);
        System.out.println("==========================================================");
	}

}
