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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alvaro
 */
public class Inventario {

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
        /*
        channel.basicConsume(NAME_QUEUE, true, (consumerTag, message) -> {
            String messageBody = new String(message.getBody(), Charset.defaultCharset());

            System.out.println("Mensaje: " + messageBody);
            System.out.println("Exchange: " + message.getEnvelope().getExchange());
            System.out.println("Routing key: " + message.getEnvelope().getRoutingKey());
            System.out.println("Delivery tag: " + message.getEnvelope().getDeliveryTag());
            
        }, consumerTag -> {
            System.out.println("Consumidor: " + consumerTag + " cancelado");
        });*/
        channel.basicConsume(NAME_QUEUE, true, (consumerTag, message) -> {
            try {
                Pedido pedido = (Pedido) Serializer.deserialize(message.getBody());

                System.out.println("Mensaje: " + pedido.getNombre() + " " + pedido.getCantidad());
                System.out.println("Exchange: " + message.getEnvelope().getExchange());
                System.out.println("Routing key: " + message.getEnvelope().getRoutingKey());
                System.out.println("Delivery tag: " + message.getEnvelope().getDeliveryTag());
            } catch (IOException ex) {
                Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }, consumerTag -> {
            System.out.println("Consumidor: " + consumerTag + " cancelado");
        });
    }
}
