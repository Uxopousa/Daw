/*
 * Escribir un programa que solicite un número > 0 e visualice o seu factorial. 
 * O factorial dun número natural n (enteiro  > 0) se define como o producto de todos os números dende 1 ata n.
 *
 * Se debe comprobar que os valores introducidos na entrada son correctos
 */

/* SOLUCION
 * 	1.- Pedir número valido
 * 	2.- Calcular factorial
 * 	3.- Visualizar resultado
 *
 *	1.- Pedir numero válido
 *		- E un número enteiro >= 0
 *		- Si o valor é erróneo, finalizaremos o programa
 *
 *	2.- Calcular factorial
 *		Temos que ir acumulando a multiplicacion dos números dende o 1 ata chegar a n
 *		r_factorial inicialmente debe valer 1
 *		dende n=1 mentras non cheguemos a n temos que gardar en r_factorial o que tiñamos en r_factorial*n
 *
 * 	3.- Visualizaremos o resultado con println
 *
 */

import java.util.Scanner;
import java.util.InputMismatchException;

public class Factorial {
	/* O cálculo do factorial é unha operación útil que non existe la libraría de java Math.
	 * de modo, que ainda que é simple, facemos un método
	 */

	/** 
	 * Calcula o factorial dun número
	 * @param int n  Numero do que desexamos o factorial
	 * @return O factorial do número
	 */
	public static long factorial(int numero) {
		long r_factorial=1;
		// Non ten moito sentido multiplicar por 1
		/*
		for(int n=2;n<=numero;n++) r_factorial=r_factorial*n;
		return r_factorial;
		*/
		// Outra solución
		/*while(numero>1) {
			r_factorial=r_factorial*numero;
			numero=numero-1;
		}
		return r_factorial;*/

		/* Otra solución.
		 * Esta solución emplea la definición recursiva de factorial que dice:
		 * 	a) El factorial de 0, es 1
		 * 	b) El factorial de n es n * factorial de n-1
		 *
		 * La recursión siempre consiste en un "caso básico", que nos da un resultado, y un "caso recursivo", que vuelve a usar la funcion para obtener la solución.
		 */
		if (numero==0) return 1;
		return numero*factorial(numero-1);

	}



	public static void main(String[] args) {
		try {
			// Pedir Datos
			Scanner teclado=new Scanner(System.in);
			System.out.print("Numero: ");
			int numero=teclado.nextInt();
			if (numero<0) throw new InputMismatchException();

			// Calculo
			long resultado=factorial(numero);

			// Resultado
			System.out.println(numero+"! = "+resultado);
		} catch(InputMismatchException e) {
			System.out.println("Debes introducir un enteiro >= 0. Os numeros grandes darán valores erróneos");
		}
	}
}
