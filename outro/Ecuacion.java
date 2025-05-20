/**
      ax²+bx+c=0
      --> Atopar os valores de x que fan que esa igualdade se cumpla.
      
      En xeral duas solucións:  x2=(-b+sqrt(b²-4ac))/2a, x2=(-b-sqrt(b²-4ac))/2a  --> Si o discriminante é negativo non ten solucións reais. Si o discriminante é 0, so ten unha solución.
      Si a=0 --> bx+c=0
                     x=-c/b
                     Si b==0 (e a==0) --> c=0  : Si c é 0, (ou a==0,b==0,c==0) ten infinitas solucións (calquera valor de x cumple a igualdade), si c!=0 non ten solución.
      
*/
import java.util.Scanner;

public class Ecuacion {
   public static void main(String[] args) {
      Scanner scn=new Scanner(System.in);
      double a;
      double b;
      double c;
   
      try {
         System.out.print("Valor de A: ");
         a=Double.parseDouble(scn.nextLine());
      
         System.out.print("Valor de B: ");
         b=Double.parseDouble(scn.nextLine());

         System.out.print("Valor de C: ");
         c=Double.parseDouble(scn.nextLine());

         // Si lanza unha Exception, infinitas solucións (calquera valor de x e solución).
         // Si retorna null, non ten solucións.
         // Si o array devolto ten unha lonxitude de 1, unha solución, si ten unha lonxitude de 2 duas solucións     
         double[] sol=resolve2g(a,b,c);
         if (sol==null) System.out.println("Sen solucións");
         else {
            for(int idx=0;idx<sol.length;idx++) {
               System.out.printf("Solucion %d: %.4f\n",(idx+1),sol[idx]);
            }
         }
      
      } catch(NumberFormatException e) {
         System.out.println("Debes introducir valores numéricos");
      } catch(Exception e) {
         System.out.println("Calquera valor de x e solución");
      }
   }
   
   public static double[] resolve2g(double a,double b, double c) throws Exception {
      double[] solucion=null;  // Sin solucions
      
      if (a==0) {
         if (b==0) {
            if (c==0) throw new Exception("Infinitas Solucions");
         } else {
            /**
               solucion=new int[1];
               solucion[0]=-b/c;
            */
            solucion=new double[] { -c/b };
         }
      } else {
         double disc=b*b-4*a*c;
         if (disc >= 0) {
            if (disc==0) solucion=new double[] {-b/2*a };
            else {
               disc=Math.sqrt(disc);
               solucion=new double[] { (-b+disc)/2.0*a, (-b-disc)/2*a };
            }
         }
      }
      return solucion;
   }
}
