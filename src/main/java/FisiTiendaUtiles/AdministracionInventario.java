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
    Esta clase recibe un mensaje de ProcesamientoOrdenes a través de la cola
    ordenes_inventario
    y tiene que enviar un mensaje a Reserva a través de la cola 
    inventario_reserva
*/
    
public class AdministracionInventario {

    private static final String NAME_QUEUE = "ordenes_inventario";
    
    private static final String NAME_QUEUE_SEND = "inventario_reserva";

    public static void main(String[] args) throws Exception {
        
        String messageReserva = "Administracion hacia Reserva";
        
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
        
        //Declarar la cola "inventario_reserva"
        channel.queueDeclare(NAME_QUEUE_SEND, false, false, false, null);
        
        // enviar mensaje a la cola "inventario_reserva" con el exchange por defecto
        channel.basicPublish("", NAME_QUEUE_SEND, null, messageReserva.getBytes());
    }
    
}
