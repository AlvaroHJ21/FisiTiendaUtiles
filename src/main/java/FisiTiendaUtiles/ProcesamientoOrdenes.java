/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FisiTiendaUtiles;

import FisiTiendaUtiles.Serializador.Serializer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alvaro
 */
public class ProcesamientoOrdenes {

    private static final String NAME_QUEUE = "ordenes_inventario";

    public static void main(String[] args) throws Exception {
        //String message = "Nuevo Pedido";
        Pedido pedido1 = new Pedido("nombre", 20);

        //Abrir un conexion AMQ
        ConnectionFactory conectionFactory = new ConnectionFactory();
        Connection connection = conectionFactory.newConnection();

        //establecer un canal
        Channel channel = connection.createChannel();

        //Declarar la cola "ordenes-inventario"
        channel.queueDeclare(NAME_QUEUE, false, false, false, null);

        //Enviar mensaje al exchange por defecto ""
        //channel.basicPublish("", NAME_QUEUE, null, message.getBytes());
        channel.basicPublish("", NAME_QUEUE, null, Serializer.serialize(pedido1));

        channel.close();
        connection.close();
    }

    public static void enviarMensaje(String nombre, int cantidad) {
        Pedido pedido = new Pedido(nombre, cantidad);

        ConnectionFactory conectionFactory = new ConnectionFactory();
        Connection connection = null;
        Channel channel = null;
        try {
            connection = conectionFactory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(NAME_QUEUE, false, false, false, null);
            channel.basicPublish("", NAME_QUEUE, null, Serializer.serialize(pedido));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
