/*
 * Escribir un programa que solicite dous números enteiros e visualice o cociente de dividir o primeiro entre o segundo e o seu resto 
 * sin facer uso dos operadores / ou %
 *
 * Se debe comprobar que os valores introducidos na entrada son correctos
 *
 */

/* SOLUCION:
 * 	1.- Pedir dividendo e divisor
 * 	2.- Calcular división enteira
 * 	3.- Visualizar resultados
 *
 *
 * 	1.- Pedir dividendo e divisor
 * 		O dividendo é un número enteiro
 * 		O divisor é un número enteiro
 * 		No aso de que unha entrada sexa errónea se finaliza o programa
 *
 * 	2.- Calcular a división enteira
 * 		Unha división enteira consiste en saber cantas veces se pode quitar a cantidade do divisor ao dividendo
 * 		polo tanto iremos restando mentres o dividendo sexa maior ou igual que o divisor e contando as restas
 *
 * 		cociente=0
 * 		mentres o dividendo sexa >= divisor, sumar 1 ao cociente, restarlle o divisor ao dividendo.
 *
 * 		Temos un pequeno "problema" cos números negativos. Si temos algún número negativo, debemos facer a operación cos
 * 		numeros positivos e logo axustar o signo. Si so un dos dous é negativo, o signo será *-1, mentras que si os dous son negativos
 * 		ou positivos o signo será 1.
 * 		Polo tanto, inicialmente o signo será 1, e cada vez que un dato sexa negativo o multiplicamos por -1. Ao final
 * 		multiplicamos o resultado obtido polo signo.
 *
 * 	3.- Visualizar Resultado
 * 		Visualizaremos o resultado con println
 */

import java.util.Scanner;
import java.util.InputMismatchException;

public class Divide {
	public static void main(String[] args) {
		try {
			Scanner teclado=new Scanner(System.in);
			int signo=1;

			// Pedimos datos
			//
			System.out.print("Dividendo: ");
			int dividendo=teclado.nextInt();
			System.out.print("Divisor: ");
			int divisor=teclado.nextInt();
			if (divisor==0) throw new InputMismatchException("Non se pode dividir por 0");

			System.out.print(dividendo+"/"+divisor+"=");


			// Axustamos signo
			//
			// Si o dividendo é negativo, o pasamos a positivo e o signo e negativo
			if (dividendo<0) {
				signo=-1;
				dividendo=-dividendo;
			}
			
			if (divisor<0) {
				signo=signo*-1;
				divisor=-divisor;
			}

			// Calculamos división
			//
			int cociente=0;
			while(dividendo>=divisor) {
				dividendo-=divisor;
				cociente=cociente+1;
			}
			// Poñemos signo ao resultado
			cociente=cociente*signo;

			// Visualizamos
			System.out.println(cociente);

		} catch(InputMismatchException e) {
			System.out.println("Entrada de datos errónea");
			// As chamadas a funcións/métodos consumen tempo. Si se pode evitar chamar, mellor:
			// Agora so a chamo unha vez
			String message=e.getMessage();
			if (message!=null) System.out.println(message);
		}
	}
}

