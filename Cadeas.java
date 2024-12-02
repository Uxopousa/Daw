import java.util.Scanner;

public class Cadeas {

   public static void main(String[] args) {
      Scanner scn=new Scanner(System.in);
      String str;
      String search;
      String subst;
      
      System.out.println("Escribe unha cadea: ");
      str=scn.nextLine();
      System.out.println(firstCapital(str));    // Cadea coa primeira letra en maiúscula
      
      System.out.println("Escribe unha cadea: ");
      str=scn.nextLine();
       System.out.println("Escribe a cadea a substituír: ");
      search=scn.nextLine();
      System.out.println("Escribe o valor a poñer: ");
      subst=scn.nextLine();
      
      System.out.println(substr(str,search,subst));  // Substitúe "search" por "subst" en "str"
    
   }
   
   
   public static String firstCapital(String str) {
   
      if (!str.isEmpty()) {
         char[] letras=str.toCharArray();
         letras[0]=Character.toUpperCase(letras[0]);
         str=new String(letras);
      }
      return str;
   }
   
   public static String substr(String str,String search,String sub) {
      /**   resultado=""
            idx=0
            Imos buscando search. dende idx
                --> o atopamos en fidx
                resultado=resultado+o de antes de atopar search
                resultado+=sub
                avanzamos idx a fidx+lonxitude de search
                
                
            esto e unha tonteria de todos modos 
            to
            ala
                
      */
      if (search.isEmpty()) return str;
      String r="";
      int start=0;
      int fidx=str.indexOf(search);
      while(fidx!=-1) {
         // substitución
         r=r+str.substring(start,fidx)+sub;
         start=fidx+search.length();
         fidx=str.indexOf(search,start);  // Buscar a seguinte aparición
      }
      r=r+str.substring(start);
      return r;      
   }
}
