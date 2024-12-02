import java.util.Scanner;

class E {
   public static void main(String[] args) {
      Scanner scn=new Scanner(System.in);
      int prec;
      try {
         System.out.println("Precision (1-20): : ");
         prec=Integer.parseInt(scn.nextLine());
         if (prec<1 || prec>20) throw new NumberFormatException();
         
         double e=calcula_e(prec);
         
         System.out.printf("O numero e cunha precision de %d é %.8f\n",prec,e);
      
      } catch(NumberFormatException e) {
         System.out.println("Debes introducir un número enteiro entre 1 e 10");
      }
   }
   
   static double calcula_e(int prec) {
      double v=1;
      
      for(int idx=1; idx<=prec; idx++) {
         v=v+1/factorial(idx);
      }
      return v;
   }
   
   static double factorial(int n) {
      if (n==0) return 1;
      else return n*factorial(n-1);
   }
   
}
