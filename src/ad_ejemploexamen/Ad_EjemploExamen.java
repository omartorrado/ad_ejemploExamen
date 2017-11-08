/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad_ejemploexamen;

/**
 *
 * @author oracle
 */
public class Ad_EjemploExamen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SqlManager c = new SqlManager();

        c.getConexion();
        c.leerFichero("/home/oracle/Downloads/analisis.txt");
//            for(String[] s:c.analisis){
//                for(String st:s){
//                    System.out.println(st);
//                }
//            }
        for (int i = 0; i < c.analisis.length; i++) {
            c.insertarAnalisis(c.analisis[i]);
            //System.out.println(c.analisis[i][6]);
        }
        c.closeConexion();
        System.out.println(c.comprobarAcidez("c", 6));
    }

}
