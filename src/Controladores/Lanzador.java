package Controladores;

import java.util.Scanner;

import Modales.Consultas;
import Vista.Menu;

public class Lanzador {
	static Scanner sc;
	static Menu menu;
	static Consultas c = new Consultas();
	public static String Menu[]= {"Mostrar departamentos","Mostrar empleados de un departamento" ,
			"Borrar departamento ","Modificar departamento ",
			"Insertar departamento","SALIR"};
	
	public static void main(String args[]) {
	
	LoadMenu();
		
		
	}
	public static void LoadMenu() {
		
		 menu=new Menu();
		int eleccion=menu.GetChosseInt("Elija que accion desea realizar",Menu);
		switch (eleccion) {
		case 1:
			c.LoadAll();
			break;
		case 2:
			String codigo_load=ObtenerValorScanner("Introduzca el codigo del departamento que desea visualizar");
			c.LoadOne(codigo_load);
			break;
		case 3:
			String codigo_delete=ObtenerValorScanner("Introduzca el codigo del departamento que desea eliminar");
			c.Delete(codigo_delete);
			break;
		case 4:
			String codigo_update=ObtenerValorScanner("Introduzca el codigo del departamento que desea eliminar");
			String name_new=ObtenerValorScanner("Introduzca el nuevo nombre de su departamentos");
			c.Update(codigo_update,name_new);
			break;
		case 5:
			String dept=ObtenerValorScanner("Introduzca el codigo de su departamentos");
			String telefono=ObtenerValorScanner("Introduzca el telefono de su departamentos");
			String tipo=ObtenerValorScanner("Introduzca el tipo de su departamentos");
			String name=ObtenerValorScanner("Introduzca el nombre de su departamentos");
			c.Insertar(dept,telefono,tipo,name);
			break;
		case 6:
			System.exit(0);
			break;
		}
		LoadMenu();
		
	}
	public static String ObtenerValorScanner(String mensaje) {
		System.out.println(mensaje);
		sc = new Scanner(System.in);
		String value=sc.nextLine();
		return value;
	}
}
