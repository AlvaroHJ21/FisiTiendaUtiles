/*
 * G9 - Integrantes
 * -------------------------------------
 * Avila Velasquez Luis Enrique
 * Céspedes Francia Gianfranco Moisés
 * Huaysara Jauregui Alvaro
 * Valenzuela Segovia Luis Alejandro Gabriel
 * Vera Cueva Antonella Jazmin

 */
package FisiTiendaUtiles;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.Charset;

/*
    Esta clase recibe un mensaje de Reserva a través de la cola 
    reserva_facturacion
    y tiene que enviar un mensaje a CuentasCobrar a través de la cola 
    facturacion_cuentas
 */
public class Facturacion {

    private static final String NAME_QUEUE_RECEIVE = "";
    private static final String NAME_QUEUE_SEND_1 = Queues.NAME_QUEUE_FACTURACION_CUENTAS;
    private static final String NAME_QUEUE_SEND_2 = Queues.NAME_QUEUE_FACTURACION_ORDENES;
    private static final String NAME_EXCHANGE = "facturacion";

    public static void main(String[] args) throws Exception {

        //envia el mensaje
        enviarMensaje("mensaje enviado por un exchange fanout");

    }

    private static void enviarMensaje(String mensaje) {
        try {
            
            //Abrir un conexion AMQ
            ConnectionFactory conectionFactory = new ConnectionFactory();
            Connection connection = conectionFactory.newConnection();
            
            //establecer un canal
            Channel channel = connection.createChannel();

            //Declarar un exchange FANOUT
            channel.exchangeDeclare(NAME_EXCHANGE, BuiltinExchangeType.FANOUT);

            //Declarar la colas
            channel.queueDeclare(NAME_QUEUE_SEND_1, false, false, false, null);
            channel.queueDeclare(NAME_QUEUE_SEND_2, false, false, false, null);

            //Vincula las colas al exchange
            channel.queueBind(NAME_QUEUE_SEND_1, NAME_EXCHANGE, "");
            channel.queueBind(NAME_QUEUE_SEND_2, NAME_EXCHANGE, "");

            //Enviar mensaje al exchange nuevo
            channel.basicPublish(NAME_EXCHANGE, "", null, mensaje.getBytes());
            System.out.println("Mensaje enviado: " + mensaje);

            channel.close();
            connection.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
