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
    Esta clase envía un mensaje a AdministracionInventario a través de la cola
    ordenes_inventario
    y recibe un mensaje de CuentasCobrar a través de la cola
    cuentas_ordenes
 */
public class ProcesamientoOrdenes {

    private static final String NAME_QUEUE_SEND = Queues.NAME_QUEUE_ORDENES_INVENTARIO;
    private static final String NAME_QUEUE_RECEIVE = Queues.NAME_QUEUE_CUENTAS_ORDENES;

    public static void main(String[] args) throws Exception {

        String mensaje = "Segundo pedido";

        enviarMensaje(mensaje);
        
        //recibirMensaje();

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
            System.out.println("Mensaje enviado: " + mensaje);

            channel.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void recibirMensaje() throws Exception {
        //abrir una conexion
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();

        //establecer un canal
        Channel channel = connection.createChannel();

        //Declarar la cola "ordenes-inventario"
        channel.queueDeclare(NAME_QUEUE_RECEIVE, false, false, false, null);

        //crear subscripcion a la cola "ordenes-inventario" usando el comando Basic.consume
        channel.basicConsume(NAME_QUEUE_RECEIVE, true, (consumerTag, message) -> {
            String messageBody = new String(message.getBody(), Charset.defaultCharset());

            System.out.println("Mensaje recibido");
            System.out.println("Mensaje: " + messageBody);
            System.out.println("Exchange: " + message.getEnvelope().getExchange());
            System.out.println("Routing key: " + message.getEnvelope().getRoutingKey());
            System.out.println("Delivery tag: " + message.getEnvelope().getDeliveryTag());

            //procesa el mensaje
            messageBody += "recibido por Procesamiento de Ordenes";

            //envia el mensaje
            enviarMensaje(messageBody);

        }, consumerTag -> {
            System.out.println("Consumidor: " + consumerTag + " cancelado");
        });
        
    }
    
    
}
