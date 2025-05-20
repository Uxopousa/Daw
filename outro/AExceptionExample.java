/**
 *
 *Queremos implementar un programa en Java que calcule a división de dous números solicitados por teclado (dividendo e divisor). 
 O programa solicitará números indefinidamente ata que os dous números solicitados sexan -1. 
 Débese controlar mediante excepcións que o divisor non sexa 0. No caso de que o sexa, amosarase unha mensaxe por pantalla. 
 Tamén haberá que amosar por pantalla o número de divisións calculadas. Emprega números enteiros para as variables.
 *
 */

import java.util.Scanner;
public class AExceptionExample {
	public static void main(String[] args) {
		Scanner scn=new Scanner(System.in);
		int counter=0;
		int dividendo=0;
		int divisor=0;
		do {
			try {
				System.out.print("Dividendo: ");
				dividendo=Integer.parseInt(scn.nextLine());
				System.out.print("Divisor: ");
				divisor=Integer.parseInt(scn.nextLine());
				System.out.printf("A division enteira  %d/%d ten como cociente %d e como resto %s\n",dividendo,divisor,dividendo/divisor,dividendo%divisor);
				counter++;

			} catch(NumberFormatException e) {
				System.out.println("Debes introducir un número enteiro");
			} catch(ArithmeticException e) {
				System.out.println("Non se pode dividir entre 0");
			}
		} while(dividendo!=-1 || divisor!=-1);
		System.out.println("Se realizaron "+counter+" divisions");
	}

}

