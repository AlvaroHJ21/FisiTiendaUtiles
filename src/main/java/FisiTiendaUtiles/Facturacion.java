/*
 * G9 - Integrantes
 * -------------------------------------
 * Avila Velasquez Luis Enrique
 * C�spedes Francia Gianfranco Mois�s
 * Huaysara Jauregui Alvaro
 * Valenzuela Segovia Luis Alejandro Gabriel
 * Vera Cueva Antonella Jazmin

 */

package FisiTiendaUtiles;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.Charset;

/*
    Esta clase recibe un mensaje de Reserva a trav�s de la cola 
    reserva_facturacion
    y tiene que enviar un mensaje a CuentasCobrar a trav�s de la cola 
    facturacion_cuentas
*/

public class Facturacion {
    
    private static final String NAME_QUEUE_1 = "";
    private static final String NAME_QUEUE_2 = "";
    
    public static void main(String[] args) {
        
    }
}
