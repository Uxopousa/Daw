/*
 * Escribir un programa que solicite dous números enteiros e visualice o resultado de elevar o primeiro número ao segundo sen facer uso da clase Math.
 *
 * Se debe comprobar que os valores introducidos na entrada son correctos
 */

/* SOLUCION:
 * 	1.- Pedir base e expoñente
 * 	2.- Calcular Potencia
 * 	3.- Visualizar Resultado
 *
 *
 * 	1.- Pedir base e expoñente
 * 		- A base e un número enteiro.
 * 		- O expoñente é un número natural (>=0)
 * 		Si a entrada é errónea insistiremos a non ser que o usuario cancele a operación
 *
 * 	2.- Calcular Potencia
 * 		- Teño que multiplicar a base por si misma tantas veces como indique o expoñente
 * 		debo empezar cun resultado de 1
 * 		contando dende 1 ata chegar ao expoñente, debo multiplicar a base polo resultado  e actualizar o resultado
 *
 * 	3.- Usaremos printf para a visualización do resultado
 */

import java.util.Scanner;


public class Potencia {

	// Si o usuario pulsa 's' indicará que desexa abandoar a operación.1
        // Cambiamos a interrupción por outra máis axeitada, como InterruptedException (https://docs.oracle.com/javase/8/docs/api/java/lang/InterruptedException.html)
        public static int inputInteger(String title,int min,int max) throws InterruptedException {
                boolean ok=false;
                Scanner teclado=new Scanner(System.in);
                String line=null;
                int num=0;

                do {
                        try {
                                System.out.print(title+" (s para cancelar): ");
                                line=teclado.nextLine();        // Si definimos aquí line, so existe no bloque {} e non podemos usala no catch e similar con num
                                num=Integer.parseInt(line);     // Pode darse a "situación excepcional" de que o usuario non escriba un int... (NumberFormatException)
                                if (num>=min && num<=max) ok=true;      // Xa temos a info
                        } catch(NumberFormatException e) {
                                // Necesitamos saber si pulsou "s"....
                                if (line.equals("s")) throw new InterruptedException();
                                // ok segue a ser false, non precisamos facer nada
                        }
                } while(!ok);
                return num;

        }

	/** Imos a contemplar a posibilidade de que o expoñente poda ser negativo....
	 */
	public static void main(String[] args) {
		try {
			boolean negativo=false;

			// MIN_VALUE e MAX_VALUE son constantes que conteñen o mínimo valor integer posible e o maximo respectivamente
			// https://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html
			int base=inputInteger("Introduce a base",Integer.MIN_VALUE,Integer.MAX_VALUE);
			int exp=inputInteger("Introduce o expoñente",Integer.MIN_VALUE,Integer.MAX_VALUE);
			System.out.printf("El resultado de %d^%d = ",base,exp);
			if (exp<0) {
				negativo=true;
				exp=-exp;
			}
			double result=1;
			// for(int contando=1;contando<=exp;contando=contando+1) result=result*base;
			// Solución alternativa
			while(exp>0) {
				result=result*base;
				exp=exp-1;
			}
			if (negativo) result=1/result;
			System.out.printf("%.8f\n",result);
		} catch(InterruptedException e) {
			System.out.println("Operación Cancelada");
		}

	}
	
}


