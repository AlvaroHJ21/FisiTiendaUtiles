/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FisiTiendaUtiles;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.Charset;

/**
 *
 * @author Alvaro
 */
public class ProcesamientoOrdenes {

    private static final String NAME_QUEUE = "ordenes_inventario";

    public static void main(String[] args) throws Exception {
        String message = "Nuevo Pedido";

        //Abrir un conexion AMQ
        ConnectionFactory conectionFactory = new ConnectionFactory();
        Connection connection = conectionFactory.newConnection();

        //establecer un canal
        Channel channel = connection.createChannel();

        //Declarar la cola "ordenes-inventario"
        channel.queueDeclare(NAME_QUEUE, false, false, false, null);

        //Enviar mensaje al exchange por defecto ""
        channel.basicPublish("", NAME_QUEUE, null, message.getBytes());
        
        channel.close();
        connection.close();
    }
}
