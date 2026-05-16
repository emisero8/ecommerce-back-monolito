package com.tienda.ecommerce.pedido.servicio;

import com.tienda.ecommerce.pedido.dominio.ItemPedido;
import com.tienda.ecommerce.pedido.dominio.Pedido;
import com.tienda.ecommerce.pedido.dto.ItemPedidoDTO;
import com.tienda.ecommerce.pedido.dto.PedidoRequestDTO;
import com.tienda.ecommerce.pedido.repositorio.PedidoRepositorio;
import com.tienda.ecommerce.producto.dto.ProductoDTO;
import com.tienda.ecommerce.producto.servicio.ProductoServicio;
import com.tienda.ecommerce.pago.servicio.DescuentoServicio;
import com.tienda.ecommerce.pago.servicio.PagoServicio;
import com.tienda.ecommerce.pago.dto.PagoResultado;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class PedidoServicioImpl implements PedidoServicio {

    private final PedidoRepositorio pedidoRepositorio;
    private final ProductoServicio productoServicio; // Twelve-Factor: Inyectamos el servicio, NO el repositorio.
    private final DescuentoServicio descuentoServicio;
    private final PagoServicio pagoServicio;

    public PedidoServicioImpl(PedidoRepositorio pedidoRepositorio, ProductoServicio productoServicio,
                              DescuentoServicio descuentoServicio, PagoServicio pagoServicio) {
        this.pedidoRepositorio = pedidoRepositorio;
        this.productoServicio = productoServicio;
        this.descuentoServicio = descuentoServicio;
        this.pagoServicio = pagoServicio;
    }

    @Override
    public String crearPedido(PedidoRequestDTO request) {
        Pedido pedido = new Pedido();
        pedido.setNombreCliente(request.getNombre());
        pedido.setTelefono(request.getTelefono());
        pedido.setDireccion(request.getDireccion());
        pedido.setColonia(request.getColonia());
        pedido.setReferencias(request.getReferencias());
        pedido.setMetodoPago(request.getMetodoPago());
        pedido.setFechaCreacion(LocalDateTime.now());
        
        // Generar código de seguimiento
        String codigo = "ORD-2026-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        pedido.setCodigoSeguimiento(codigo);

        double subtotal = 0.0;

        for (ItemPedidoDTO itemDto : request.getItems()) {
            // Consultamos el precio real en la base de datos de productos
            ProductoDTO producto = productoServicio.buscarPorId(itemDto.getProductoId());
            
            ItemPedido item = new ItemPedido();
            item.setProductoId(producto.getId());
            item.setCantidad(itemDto.getCantidad());
            item.setPrecioUnitario(producto.getPrecio());
            
            subtotal += (producto.getPrecio() * itemDto.getCantidad());
            
            pedido.agregarItem(item);
        }

        // Validar descuento con el servicio
        double porcentajeDescuento = descuentoServicio.obtenerPorcentajeDescuento(request.getCodigoDescuento());
        double descuento = subtotal * porcentajeDescuento;

        double envio = 45.0; // Costo fijo de envío como en el frontend
        double total = subtotal - descuento + envio;

        // Procesar pago simulado
        PagoResultado resultadoPago = pagoServicio.procesarPago(request.getMetodoPago(), total);
        pedido.setEstadoPago(resultadoPago.getEstadoPago());
        pedido.setIdTransaccion(resultadoPago.getIdTransaccion());

        pedido.setSubtotal(subtotal);
        pedido.setDescuento(descuento);
        pedido.setEnvio(envio);
        pedido.setTotal(total);

        pedidoRepositorio.save(pedido);

        return codigo;
    }
}
