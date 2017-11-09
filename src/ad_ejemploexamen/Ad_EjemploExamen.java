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
//
        for (String[] analisi : c.analisis) {
            c.insertarAnalisis(analisi);
        }
        c.closeConexion();
       
    }

}
