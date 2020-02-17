package Modales;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

public class Consultas {
	   public static final String nombrefichero = "AleatorioEmple.dat";
	    static Scanner teclado = new Scanner(System.in);
	    static String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist
	    //xmldb:exist://localhost:8083/exist/xmlrpc
	    static String URI = "xmldb:exist://localhost:8083/exist/xmlrpc/db/ColeccionPruebas"; //URI colecci�n   
	    static String usu = "admin"; //Usuario
	    static String usuPwd = "1234"; //Clave
	    static Collection col = null;

	public static  Collection Conectar() {
		try {
            Class<?> cl = Class.forName(driver); //Cargar del driver 
            Database database = (Database) cl.newInstance(); //Instancia de la BD
            DatabaseManager.registerDatabase(database); //Registro del driver
            col = (Collection) DatabaseManager.getCollection(URI, usu, usuPwd);
            return col;
        } catch (XMLDBException e) {
            System.out.println("Error al inicializar la BD eXist.");
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error en el driver.");
            //e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Error al instanciar la BD.");
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Error al instanciar la BD.");
            //e.printStackTrace();
        }
        return null;
	}
	

	public void Insertar(String dep,String telefono,String tipo ,String dnombre) {
		if(!comprobardep(dep)) {
			 String nuevodep = "<departamento telefono='"+telefono+"' tipo='"+tipo+"'><codigo>" + dep + "</codigo>"
		                + "<nombre>" + dnombre +"</nombre>"+"</departamento>";
		        if (Conectar() != null) {
		            try {
		                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
		                System.out.printf("Inserto: %s \n", nuevodep);
		                ResourceSet result = servicio.query("update insert " + nuevodep + " into /universidad");
		                col.close(); //borramos 
		                System.out.println("Dep insertado.");
		            } catch (Exception e) {
		                System.out.println("Error al insertar empleado.");
		                e.printStackTrace();
		            }
		        } else {
		            System.out.println("Error en la conexi�n. Comprueba datos.");
		        }
		}else {
			System.out.println("Ya existe un departamento con este Codigo de departamento");
		}
		  
	}
	public void Delete(String dep) {
		if (comprobardep(dep)) {
            if (Conectar() != null) {
                try {
                    System.out.printf("Borro el departamento: %s\n", dep);
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    ResourceSet result = servicio.query(
                            "update delete /universidad/departamento[codigo='" + dep + "']");
                    col.close();
                    System.out.println("Dep borrado.");
                } catch (Exception e) {
                    System.out.println("Error al borrar.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error en la conexi�n. Comprueba datos.");
            }
        } else {
            System.out.println("El departamento NO EXISTE.");
        }
	}
	public void Update(String dep,String nombre) {
        if (comprobardep(dep)) {
            if (Conectar() != null) {
                try {
                    System.out.printf("Actualizo el departamento: %s\n", dep);
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    ResourceSet result = servicio.query(
                            "update value /universidad/departamento[codigo='"+dep+"']/nombre with data('"+nombre+"')");

                    col.close();
                    System.out.println("Dep actualizado.");
                } catch (Exception e) {
                    System.out.println("Error al actualizar.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error en la conexi�n. Comprueba datos.");
            }
        } else {
            System.out.println("El departamento NO EXISTE.");
        }
	}
	public void LoadAll() {
		   if (Conectar() != null) {
	            try {
	                XPathQueryService servicio;
	                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
	                ResourceSet result = servicio.query("for $dep in /universidad/departamento return $dep");
	                // recorrer los datos del recurso.
	           
	                ResourceIterator i;
	                i = result.getIterator();
	                if (!i.hasMoreResources()) {
	                    System.out.println(" LA CONSULTA NO DEVUELVE NADA O EST� MAL ESCRITA");
	                }
	                while (i.hasMoreResources()) {
	                	 Resource r = i.nextResource();
	                     System.out.println("--------------------------------------------");
	                   
	                     System.out.println(r.getContent());
	                col.close();
	                }
	            } catch (XMLDBException e) {
	                System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("Error en la conexi�n. Comprueba datos.");
	        }
	}
	 private static boolean comprobardep(String dep) {
	        //Devuelve true si el dep existe
	        if (Conectar() != null) {
	            try {
	                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
	                ResourceSet result = servicio.query("/universidad/departamento[codigo='"+dep+"']");
	                ResourceIterator i;
	                i = result.getIterator();
	                col.close();
	                if (!i.hasMoreResources()) {
	                    return false;
	                } else {
	                    return true;
	                }
	            } catch (Exception e) {
	                System.out.println("Error al consultar.");
	                // e.printStackTrace();
	            }
	        } else {
	            System.out.println("Error en la conexi�n. Comprueba datos.");
	        }

	        return false;

	    }// comprobardep
	public void LoadOne(String dep) {
		 if (comprobardep(dep)) {
	            if (Conectar() != null) {
	                try {
	                    XPathQueryService servicio;
	                    servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
	                    ResourceSet result = servicio.query("/universidad/departamento[codigo='"+dep+"']");
	                    // recorrer los datos del recurso.  
	                    ResourceIterator i;
	                    i = result.getIterator();
	                    if (!i.hasMoreResources()) {
	                        System.out.println(" LA CONSULTA NO DEVUELVE NADA O EST� MAL ESCRITA");
	                    } else {
	                        Resource r = i.nextResource();
	                        System.out.println("--------------------------------------------");
	                        System.out.println((String) r.getContent());
	                    }
	                    col.close();
	                } catch (XMLDBException e) {
	                    System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
	                    e.printStackTrace();
	                }
	            } else {
	                System.out.println("Error en la conexi�n. Comprueba datos.");
	            }
	        } else {
	            System.out.println("El departamento NO EXISTE.");
	        }

	}
}
