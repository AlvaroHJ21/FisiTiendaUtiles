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
    Esta clase envía un mensaje a AdministracionInventario
 */
public class ProcesamientoOrdenes {

    private static final String NAME_QUEUE_SEND = Queues.NAME_QUEUE_ORDENES_INVENTARIO;

    public static void main(String[] args) throws Exception {

        String mensaje = "Otro pedido 3";

        enviarMensaje(mensaje);

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
}
