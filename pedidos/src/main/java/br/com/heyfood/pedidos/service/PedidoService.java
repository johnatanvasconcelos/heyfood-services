package br.com.heyfood.pedidos.service;

import br.com.heyfood.pedidos.dto.AtualizaStatusDto;
import br.com.heyfood.pedidos.dto.PedidoDto;
import br.com.heyfood.pedidos.mapper.PedidoMapper;
import br.com.heyfood.pedidos.model.Pedido;
import br.com.heyfood.pedidos.model.Status;
import br.com.heyfood.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private PedidoMapper pedidoMapper;


    public List<PedidoDto> obterTodos() {
        return repository
                .findAll().stream()
                .map(p -> pedidoMapper.pedidoToDto(p))
                .collect(Collectors.toList());
    }

    public PedidoDto obterPorId(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return pedidoMapper.pedidoToDto(pedido);
    }

    public PedidoDto criarPedido(PedidoDto dto) {
        Pedido pedido = pedidoMapper.dtoToPedido(dto);

        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(Status.REALIZADO);
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        Pedido salvo = repository.save(pedido);

        return pedidoMapper.pedidoToDto(salvo);
    }

    public PedidoDto atualizaStatus(Long id, AtualizaStatusDto dto) {

        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(dto.status());
        repository.atualizaStatus(dto.status(), pedido);
        return pedidoMapper.pedidoToDto(pedido);
    }

    public void aprovaPagamentoPedido(Long id) {

        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(Status.PAGO);
        repository.atualizaStatus(Status.PAGO, pedido);
    }
}
