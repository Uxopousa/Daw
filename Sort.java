import java.util.Scanner;

public class Sort {

   /**
      1.- Pedir números
      2.- Ordear os numeros
      3.- Visualizar os números
   */
   public static void main(String[] args) {
      final int TOTALNUMS=7;
      int[] nums=new int[TOTALNUMS];
      Scanner scn=new Scanner(System.in);
      
      try {
         // Pedir números
         for(int idx=0;idx<nums.length; idx++) {
            System.out.printf("Numero %d ? :",(idx+1));
            nums[idx]=Integer.parseInt(scn.nextLine());
         }
      
         // Ordear
         sort(nums);
      
         // Visualizar
         System.out.println("Secuencia Ordeada:");
         for(int idx=0;idx<nums.length; idx++) {
            System.out.printf("%d ",nums[idx]);
         }
         
      } catch(NumberFormatException e) {
         System.out.println("Debes introducir números enteiros");
      }
      
   }
   
   
   /**
   *     
         IDX-0  
           C0          3 -->-3, 5, 0, 3, 99, 18, 1
           C1          -3,3 --> 5,0,3,99,18,1
           C2          -3,3,5-->0,3,99,18,1
           C3          -3,3,0,5-->3,99,18,1
           c4          -3,3,0,3,5-->99,18,1
           c5          -3,3,0,3,5,99-->18,1
           C6          -3,3,0,3,5,18,99-->1
           
        IDX-1
           c0          -3->3,0,3,5,18,1,[99]
           c1          -3,3->0,3,5,18,1,[99]
           c2          -3,0,3->3,5,18,1 [99]
           c3          -3,0,3,3->5,18,1 [99]
           c4          -3,0,3,3,5,18->1 [99]
           c5          -3,0,3,3,5,1,[18] [99]
           
        IDX-2           
           c0          -3->0,3,3,5,1,[18] [99]
           c1          -3,0->3,3,5,1,[18] [99]
           c2          -3,0,3->3,5,1,[18] [99] 
           c3          -3,0,3,3->5,1,[18] [99]
           c4          -3,0,3,3,5->1,[18] [99]
          
      IDX-3          
           c0          -3->0,3,3,1,[5][18] [99]
           c1            -3,0->3,3,1,[5][18] [99]
           c2            -3,0,3->3,1,[5][18] [99]
           c3            -3->0,3,3->1,[5][18] [99]
           
      IDX-4
           c0            -3->0,3,1,[3],[5][18] [99]
           c1            -3,0->3,1,[3],[5][18] [99]
           c2            -3,0,3->1,[3],[5][18] [99]

      IDX-5       
           C0            -3->0,1,[3],[3],[5][18] [99]
           C1            -3,0->1,[3],[3],[5][18] [99]
           
      IDX-6
           c0            -3->0,[1],[3],[3],[5][18] [99]
           Dende idx=0 mentres <= n-2
              Dende c=0 ata mentres <= n-2-idx
                 Si el[c] > el[c+1] intercambiamos
           
           
           
                     
   */
   static void sort(int[] n) {
      boolean ordeado=false;
      int fin_idx=n.length-2;
      for(int idx=0;(idx<=fin_idx) && (!ordeado);idx++) {
         int fin_c=n.length-2-idx;
         ordeado=true;
         for(int c=0;c<=fin_c;c++) {
            if (n[c] > n[c+1]) {
               int sw=n[c];
               n[c]=n[c+1];
               n[c+1]=sw;
               ordeado=false;
            }
         }
      }
   }
}
