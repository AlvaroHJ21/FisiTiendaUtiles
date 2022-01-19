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

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.Charset;

/*
    Esta clase recibe un mensaje de Facturacion a través de la cola
    facturacion_cuentas
    y tiene que enviar un mensaje a ProcesamientoOrdenes a través de la cola
    cuentas_ordenes
 */
public class CuentasCobrar {

    private static final String NAME_QUEUE_RECEIVE = Queues.NAME_QUEUE_FACTURACION_CUENTAS;
    private static final String NAME_QUEUE_SEND = Queues.NAME_QUEUE_CUENTAS_ORDENES;

    public static void main(String[] args) throws Exception {

        //abrir una conexion
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();

        //establecer un canal
        Channel channel = connection.createChannel();

        //Declarar la cola "facturacion_cuentas"
        channel.queueDeclare(NAME_QUEUE_RECEIVE, false, false, false, null);

        //crear subscripcion a la cola "inventario_reserva" usando el comando Basic.consume
        channel.basicConsume(NAME_QUEUE_RECEIVE, true, (consumerTag, message) -> {
            String messageBody = new String(message.getBody(), Charset.defaultCharset());

            System.out.println("MENSAJE RECIBIDO!");
            System.out.println("------------------------------------------------------");
            System.out.println("Mensaje: " + messageBody);
            System.out.println("Exchange: " + message.getEnvelope().getExchange());
            System.out.println("Routing key: " + message.getEnvelope().getRoutingKey());
            System.out.println("Delivery tag: " + message.getEnvelope().getDeliveryTag());
            System.out.println("------------------------------------------------------");

            //procesa el mensaje
            messageBody = procesarMensaje(messageBody);

            //envia el mensaje
            enviarMensaje(messageBody);

        }, consumerTag -> {
            System.out.println("Consumidor: " + consumerTag + " cancelado");
        });
    }

    public static void enviarMensaje(String mensaje) {
        try {
            //abrir una coneccion
            ConnectionFactory connectionFactory = new ConnectionFactory();
            Connection connection = connectionFactory.newConnection();

            //establecer un canal
            Channel channel = connection.createChannel();

            //Declarar la cola "cuentas_ordenes"
            channel.queueDeclare(NAME_QUEUE_SEND, false, false, false, null);

            // enviar mensaje a la cola "inventario_reserva" con el exchange por defecto
            channel.basicPublish("", NAME_QUEUE_SEND, null, mensaje.getBytes());

            System.out.println("MENSAJE ENVIADO!");
            System.out.println("------------------------------------------------------");
            System.out.println("mensaje: " + mensaje);
            System.out.println("queue: " + NAME_QUEUE_SEND);
            System.out.println("------------------------------------------------------");

            channel.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String procesarMensaje(String mensaje) {
        return mensaje += " /cuentas";
    }
}
