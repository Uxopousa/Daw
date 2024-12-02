/*
 *
 * Facer un programa que lea por teclado o radio dun círculo (un valor real) e:
 * - Imprima a súa área
 * - Imprima lonxitude da circunferencia que o delimita
 * - Imprima o volume (4 pi * r^3 / 3) 
 * - Imprima a superficie (4 * pi r^2) da esfera dese radio. 
 *
 * Os resultados deben amosarse con 4 decimais utilizando os modificadores de System.out.printf
 * Se debe comprobar que os valores introducidos na entrada son correctos
 */

/*
 * SOLUCION:
 *      Debemos:        1.- Pedir un radio válido
 *                      2.- Calcular os datos
 *                      3.- Visualizar os resultados
 *
 *      1.- Pedir un radio válido
 *              - A entrada debe ser numérica e Real (un número con decimais)
 *              - O radio non pode ser negativo
 *              Decidimos que si non se cumplen estas condicións, o programa finaliza
 *              (Poderíamos decidir que si non se cumplen, se insiste na entrada. Nese caso deberíamos darlle ao usuario a opción de cancelar)
 *
 *      2.- Calcular os datos
 *              area=2*PI*radio
 *              lonxitude=PI*radio*radio
 *              volume=4*PI*radio*radio*radio/3
 *              superficie=4*PI*radio*radio
 *
 *      3.- Visualizar os resultados
 *              Usaremos printf co modificador %.4f para amosar 4 dixitos decimais
 *
 *      Como a opción 1 ten certa complexidade, e podería ser util en posteriores exercicios poderíamos plantexar un
 *      método para a entrada. O deixamos para posteriores exercicios.
 */

import java.util.Scanner;
import java.util.InputMismatchException;
import static java.lang.Math.PI;	// Importamos a constante, debemos poñer static

public class Esfera {
	// Esta constante xa está definida na clase Math (https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html)
	// public static final double PI=3.14;

	public static void main(String[] args) {
		// Creamos o obxecto Scanner para ler datos de teclado
                // Scanner non é unha clase de java.lang, de modo que temos que importala
		Scanner teclado=new Scanner(System.in);

		// Variables para gardar os resultados
                double radio;
                double area;
                double lonxitude;
                double volume;
                double superficie;

		try {
			System.out.print("Radio: ");
	                // Si miramos o API de Scanner (https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html#nextDouble--) si non intruducimos un número válido
        	        // Se produce unha situación "Excepcional" que é notificada mediante unha InputMismatchException....
			// Capturamos ese sinal "Excepcional" e o tratamos de xeito axeitado...
	                radio=teclado.nextDouble();
			// Consideramos tamén a introdución dun radio non válido como un caso "Excepcional" do mesmo tipo
			if (radio<=0) throw new InputMismatchException();
		
			// Facemos os cálculos. Xa non precisamos especificar Math, porque está importado PI
	                area=2*PI*radio;
                        lonxitude=PI*radio*radio;
              	        volume=4*PI*radio*radio*radio/3;
                       	superficie=4*PI*radio*radio;

	                // Visualizamos os resultados
                        System.out.printf("O Area do círculo é %.4f\n",area);
               	        System.out.printf("A Lonxitude da circunferencia é %.4f\n",lonxitude);
                       	System.out.printf("O Volume da esfera é %.4f\n",volume);
	                System.out.printf("A Superficie da esfera é %.4f\n",superficie);
		} catch(InputMismatchException e) {  // InputMismatchException non é de java.lang, debemos importala...
			 System.out.println("Debes introducir un valor numérico > 0 usando a coma decimal");
		}
	}
}

