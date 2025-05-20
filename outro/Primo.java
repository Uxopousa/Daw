import java.util.Scanner;

public class Primo {
   public static void main(String[] args) {
      Scanner scn=new Scanner(System.in); 
      
      try {
         System.out.println("Numero enteiro > 0: ");
         int num=Integer.parseInt(scn.nextLine());
         if (num<=0) throw new NumberFormatException("Debe ser un número positivo");
         
         if (ePrimo(num)) System.out.println("E primo");
         else             System.out.println("Non é primo");
      
      } catch(NumberFormatException e) {
         System.out.println(e.getMessage());
      }
   }
   
   public static boolean ePrimo(int n) {
      boolean primo=true;
      int idx=2;
      while(idx<n && primo) {
         primo=(n%idx != 0);
         idx++;
      }
      /**
         while(idx<n && primo) {
            if (n%idx!=0) primo=false;
            idx++;
         }
      */
      return primo;
   }
}
