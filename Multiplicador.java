/*
 * 	Escribir un programa que solicite un número do 1 ao 9 e visualice a súa táboa de multiplicar.

	Se debe comprobar que os valores introducidos na entrada son correctos
*/

/* SOLUCION
 *	1.- Pedir un número entero entre 1 e 9
 *	2.- Visualizar a taboa de multiplicar do número
 *
 *
 *	1,- Pedir un numero entero entre 1 e 0
 *		Pediremos o número mentres non sexa correcto dando a opción de cancelar a operación
 *
 *	2.- Empezamos no valor 1, mentras non cheguemos ao valor 10, iremos visualizando o numero*valor e sumándolle 1 ao valor
 */
import java.util.Scanner;

public class Multiplicador {
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


	public static void main(String[] args) {
		try {
			int numero=inputInteger("Introduce un numero entre 1 e 9",1,9);
			System.out.printf("Tabla do %d\n-----------\n",numero);
			for(int valor=1;valor<=10;valor=valor+1) System.out.printf("%d * %d = %d\n",numero,valor,numero*valor);
		} catch(InterruptedException e) {
			System.out.println("Operación cancelada");
		}
	}
}
