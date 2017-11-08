package ad_ejemploexamen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlManager {

    public static Connection conexion = null;
    public PreparedStatement pst;
    //para recorrer todas las filas hacer un for de i<analisis.lenght
    public String[][] analisis = new String[4][];

    public void getConexion() {
        String usuario = "hr";
        String password = "hr";
        String host = "localhost.localdomain";
        String puerto = "1521";
        String sid = "orcl";
        String ulrjdbc = "jdbc:oracle:thin:" + usuario + "/" + password + "@" + host + ":" + puerto + ":" + sid;
        try {
            conexion = DriverManager.getConnection(ulrjdbc);
        } catch (SQLException ex) {
            Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void closeConexion() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void leerFichero(String ruta){
        try {
            BufferedReader bf=new BufferedReader(new FileReader(ruta));
            int i=0;
            while(bf.ready()){
                analisis[i]=bf.readLine().split(",");
                i++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void insertarAnalisis(String[] analisis) {
        try {
            pst = conexion.prepareStatement("insert into xerado(num,nomeuva,tratacidez,total) values(?,?,?,?)");
            //leemos el codigo del analisis
            pst.setString(1, analisis[0]);
            //leemos en la tabla uvas el nombre de la uva segun el codigo de uva del analisis
            pst.setString(2, nombreUva(analisis[4]));
            //leer de la tabla los valores de acidez de la uva en base a tratacidez para meter luego en xerado subir/bajar/correcta segun corresponda
            System.out.println(comprobarAcidez(analisis[4], Integer.parseInt(analisis[1])));
            pst.setString(3, comprobarAcidez(analisis[4], Integer.parseInt(analisis[1])));
            //leemos la cantidad del analisis y la multipicamos por 15
            System.out.println("Tu madre");
            pst.setInt(4, (Integer.parseInt(analisis[5]) * 15));
            //ejecutamos el statement
            System.out.println("hijo de puta");
            pst.executeUpdate();
            System.out.println("JODEEEEEEEEER");
            //Falta aumentar el numero de analisis en base al dni, hay que hacer un nuevo statement
            pst=conexion.prepareStatement("update clientes set numerodeanalisis+=? where dni=?");

            pst.setInt(1, 1);

            pst.setString(2,analisis[6]);

            pst.executeUpdate();
            conexion.commit();
        } catch (SQLException ex) {
            System.out.println("Falla insertar");
            Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String nombreUva(String codUva) {
        String nomeUva = null;
        try {
            pst = conexion.prepareStatement("select nomeu from uvas where tipo='" + codUva + "'");
            ResultSet rs = pst.executeQuery();
            rs.next();
            nomeUva=rs.getString("nomeu");
        } catch (SQLException ex) {
            Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nomeUva;

    }

    public String comprobarAcidez(String codUva, int acidez) {
        String trataAcidez = null;
        int acidezMin;
        int acidezMax;
        try {
            pst = conexion.prepareStatement("select acidezmin,acidezmax from uvas where tipo='" + codUva + "'");
            ResultSet rs = pst.executeQuery();
            rs.next();
            acidezMin = Integer.parseInt(rs.getString("acidezmin"));
            acidezMax = Integer.parseInt(rs.getString("acidezmax"));
            if (acidez < acidezMin) {
                trataAcidez = "subir acidez";
            } else if (acidez > acidezMax) {
                trataAcidez = "bajar acidez";
            } else {
                trataAcidez = "equilibrada";
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return trataAcidez;

    }
}
