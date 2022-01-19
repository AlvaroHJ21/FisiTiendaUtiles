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
    Esta clase envía un mensaje a AdministracionInventario a través de la cola
    ordenes_inventario
    y recibe un mensaje de CuentasCobrar a través de la cola
    cuentas_ordenes
 */
public class ProcesamientoOrdenes {

    private static final String NAME_QUEUE_SEND = Queues.NAME_QUEUE_ORDENES_INVENTARIO;
    private static final String NAME_QUEUE_RECEIVE_1 = Queues.NAME_QUEUE_CUENTAS_ORDENES;
    private static final String NAME_QUEUE_RECEIVE_2 = Queues.NAME_QUEUE_FACTURACION_ORDENES;

    public static void main(String[] args) throws Exception {

        String mensaje = "Nuevo pedido";

        enviarMensaje(mensaje);

        recibirMensaje(NAME_QUEUE_RECEIVE_1);
        recibirMensaje(NAME_QUEUE_RECEIVE_2);

    }

    public static void enviarMensaje(String mensaje) {
        try {
            //Abrir un conexion AMQ
            ConnectionFactory conectionFactory = new ConnectionFactory();
            Connection connection = conectionFactory.newConnection();

            //establecer un canal
            Channel channel = connection.createChannel();

            //Declarar la cola "ordenes_inventario"
            channel.queueDeclare(NAME_QUEUE_SEND, false, false, false, null);

            //Enviar mensaje al exchange por defecto ""
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

    public static void recibirMensaje(String nameQueue) throws Exception {
        //abrir una conexion
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();

        //establecer un canal
        Channel channel = connection.createChannel();

        //Declarar la cola "ordenes-inventario"
        channel.queueDeclare(nameQueue, false, false, false, null);

        //crear subscripcion a la cola "ordenes-inventario" usando el comando Basic.consume
        channel.basicConsume(nameQueue, true, (consumerTag, message) -> {
            String messageBody = new String(message.getBody(), Charset.defaultCharset());

            System.out.println("MENSAJE RECIBIDO!");
            System.out.println("------------------------------------------------------");
            System.out.println("Mensaje: " + messageBody);
            System.out.println("Exchange: " + message.getEnvelope().getExchange());
            System.out.println("Routing key: " + message.getEnvelope().getRoutingKey());
            System.out.println("Delivery tag: " + message.getEnvelope().getDeliveryTag());
            System.out.println("------------------------------------------------------");

        }, consumerTag -> {
            System.out.println("Consumidor: " + consumerTag + " cancelado");
        });

    }

}
