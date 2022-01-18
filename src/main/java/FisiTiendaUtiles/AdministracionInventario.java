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

    
public class AdministracionInventario {

    private static final String NAME_QUEUE = "ordenes_inventario";

    public static void main(String[] args) throws Exception {
        //abrir una coneccion
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();

        //establecer un canal
        Channel channel = connection.createChannel();

        //Declarar la cola "ordenes-inventario"
        channel.queueDeclare(NAME_QUEUE, false, false, false, null);

        //crear subscripcion a la cola "ordenes-inventario" usando el comando Basic.consume
        channel.basicConsume(NAME_QUEUE, true, (consumerTag, message) -> {
            String messageBody = new String(message.getBody(), Charset.defaultCharset());

            System.out.println("Mensaje: " + messageBody);
            System.out.println("Exchange: " + message.getEnvelope().getExchange());
            System.out.println("Routing key: " + message.getEnvelope().getRoutingKey());
            System.out.println("Delivery tag: " + message.getEnvelope().getDeliveryTag());
        }, consumerTag -> {
            System.out.println("Consumidor: " + consumerTag + " cancelado");
        });
    }
}
