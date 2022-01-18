/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FisiTiendaUtiles;

import com.rabbitmq.client.Delivery;
import java.nio.charset.Charset;

/**
 *
 * @author Alvaro
 */
public class Queues {
    public static final String NAME_QUEUE_ORDENES_INVENTARIO = "ordenes_inventario";
    public static final String NAME_QUEUE_INVENTARIO_ORDENES = "inventario_ordenes";
    public static final String NAME_QUEUE_INVENTARIO_RESERVA = "inventario_reserva";
    public static final String NAME_QUEUE_RESERVA_FACTURACION = "reserva_facturacion";
    public static final String NAME_QUEUE_FACTURACION_CUENTAS = "facturacion_cuentas";
    public static final String NAME_QUEUE_FACTURACION_ORDENES = "facturacion_ordenes";
    public static final String NAME_QUEUE_CUENTAS_ORDENES = "cuentas_ordenes";
}
