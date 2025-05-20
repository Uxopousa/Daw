import java.util.Scanner;

class MCM {

	/**
	 * Como vemos, si aplicamos deseño descendente, este sería o programa completo.
	 * Agora so necesitamos un método mcm que reciba dous números enteiros e retorne
	 * o seu mcm
	 */
	public static void main(String[] args) {
		int num1;
		int num2;
		Scanner scn=new Scanner(System.in);

		// Si o usuario non introduce un número enteiro, rematamos o programa indicando 
		// que debe poñer un número enteiro.
		try {
			System.out.println("Introduce o primeiro numero: ");
			num1=Integer.parseInt(scn.nextLine());
			System.out.println("Introduce o segundo numero: ");
			num2=Integer.parseInt(scn.nextLine());
			// Aplicamos deseño descendente. O que quero facer e visualizar num1, num2 e o mcm de num1 e num2
			// O fago. Non teño por qué resolver aquí o problema.
			System.out.printf("O mcm de %d e %d é %d\n",num1,num2,mcm(num1,num2));
		} catch(NumberFormatException e) {
			// En lugar de usar nextInt, vou a utilizar nextLine e convertir con Integer.parseInt.
			// Nese caso, cando non e un número enteiro en lugar de unha InputMismatchException, recibirei unha NumberFormatException
			System.out.println("Debes escribir numeros enteiros");
		} catch(IllegalStateException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Retorna o mínimo común múltiplo de dous numeros enteiros 
	 * @param a  Primeiro número
	 * @param b  Segundo número
	 * @return O mínimo común múltiplo de a e b
	 */
	public static int mcm(int a,int b) throws IllegalStateException {
		int mcm,min,max;
		// As posibilidades en canto os posibles valores de a e b son:
		// Un dos dous, ou os dous son negativos (a<0 || b<0).  Nese caso, o mcm se calcula cos números positivos xa que -a = -1 * a e -b = -1 *b
		// Un dos dous, ou os dous son 0 - Nese caso non estsá definido o mcm. Polo tanto sería unha condición errónea.
		// Temos dúas posibilidades: 
		// 	a) Nos aseguramos no main que sempre a e b > 0 : Funcionaría, pero o método mcm non sería correcto... xa que si en
		// 	   outro programa se lle pasan valores 0 funcinaría mal
		// 	b) Comprobamos aquí que a e b sexan > 0, e si non é así notificamos un erro.
		// 		
		// A mellor solución é b). PERO, ¿ Como indicamos ao "main" a condición de erro?. Temos dúas posibilidades razoables:
		// 	a) Cun valor de retorno: Non e posible de xeito direto, xa que calquera valor "int" que retornemos pode ser un mcm. Non temos posibilidade de distinguir
		// 	   ao 100% cando é un erro ou un resultado. Un modo de facelo sería pasándolle un obxecto onde se almacene un código de resultado que poderíamos
		// 	   cambiar.... pero e demasiado lío.
		// 	b) Entendendo o erro como unha "situación anómala" que é necesario notificar: Unha Excepción. Por exemplo, IllegalStateException
		//
		// Eleximos a opción b)
	
		if (a==0 || b==0) throw new IllegalStateException("Non se pode calcular o mcm si un dos valores é 0");

		// Nos aseguramos que os valores son positivos
		a=Math.abs(a);
		b=Math.abs(b);

		// calculamos o mcm
		// Usamos un algoritmo sinxelo (aínda que pouco eficiente), para algoritmos máis eficientes son necesarios coñecementos matemáticos.
		// Por exemplo, se podería utilizar o algoritmo de euclides.
		// O mcm é o número positivo máis pequeno que é múltiplo dos dous números. Si comezamos polo número maior e o imos incrementando ao seu
		// seguinte múltiplo mentres que non sexa multiplo do outro, teremos o mcm
		// Necesitamos o máximo e o mínimo
		max=a;
		min=b;
		if (b>a) {
			max=b;
			min=a;
		}
		mcm=max;
		while(mcm%min!=0) mcm=mcm+max;
		return mcm;
	}

}
