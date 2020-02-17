package Vista;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	private Scanner sc;

	public int GetChosseInt(String titulo, String[] MenuDatos) {
		int opcion = 0;
		boolean isValid = false;
		while (!isValid) {
			sc = new Scanner(System.in);
			System.out.println(titulo);
			for (int i = 0; i < MenuDatos.length; i++) {
				System.out.println((i + 1) + "." + MenuDatos[i]);
			}
			try {
				opcion = sc.nextInt();
				if (opcion < MenuDatos.length + 1 && opcion > 0) {
					isValid = true;

				} else {
					System.out.println(
							"El valor introducido esta fuera de rango porfavor elija una opcion del menu entre 1 -"
									+ MenuDatos.length + "\n");
				}

			} catch (InputMismatchException e) {
				System.out.println("Error al introducir la accion\n");
			} catch (NumberFormatException ex) {
				System.out.println("Error al introducir la accion\n");
			}
		}
		return opcion;
	}

}
