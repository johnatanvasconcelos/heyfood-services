package br.com.heyfood.pedidos.mapper;

import br.com.heyfood.pedidos.dto.ItemDoPedidoDto;
import br.com.heyfood.pedidos.dto.PedidoDto;
import br.com.heyfood.pedidos.model.ItemDoPedido;
import br.com.heyfood.pedidos.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public PedidoDto pedidoToDto(Pedido pedido) {

        if (pedido == null) {
            return null;
        }
        return new PedidoDto(
                pedido.getId(),
                pedido.getDataHora(),
                pedido.getStatus(),
                pedido.getItens() != null ? pedido.getItens().stream()
                        .map(this::itemToDto)
                        .collect(Collectors.toList()) : null
        );
    }

    public Pedido dtoToPedido(PedidoDto pedidoDto) {

        if (pedidoDto == null) {
            return null;
        }
        Pedido pedido = new Pedido();
        pedido.setId(pedidoDto.id());
        pedido.setDataHora(pedidoDto.dataHora());
        pedido.setStatus(pedidoDto.status());
        if (pedidoDto.itens() != null) {
            pedido.setItens(pedidoDto.itens().stream()
                    .map(this::dtoToItem)
                    .collect(Collectors.toList()));
        }
        return pedido;
    }

    public ItemDoPedidoDto itemToDto(ItemDoPedido item) {
        if (item == null) {
            return null;
        }
        return new ItemDoPedidoDto(
                item.getId(),
                item.getQuantidade(),
                item.getDescricao()
        );
    }

    public ItemDoPedido dtoToItem(ItemDoPedidoDto itemDto) {
        if (itemDto == null) {
            return null;
        }
        ItemDoPedido item = new ItemDoPedido();
        item.setId(itemDto.id());
        item.setQuantidade(itemDto.quantidade());
        item.setDescricao(itemDto.descricao());
        return item;
    }
}

