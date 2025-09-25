package br.com.heyfood.pagamentos.service;

import br.com.heyfood.pagamentos.dto.AtualizarPagamentoDto;
import br.com.heyfood.pagamentos.dto.PagamentoDto;
import br.com.heyfood.pagamentos.http.PedidoClient;
import br.com.heyfood.pagamentos.model.Pagamento;
import br.com.heyfood.pagamentos.model.Status;
import br.com.heyfood.pagamentos.repository.PagamentoRepository;
import br.com.heyfood.pagamentos.mapper.PagamentoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private PedidoClient pedido;


    public Page<PagamentoDto> obterTodos(Pageable paginacao) {
        return repository
                .findAll(paginacao)
                .map(PagamentoMapper::toDto);
    }

    public PagamentoDto obterPorId(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return PagamentoMapper.toDto(pagamento);
    }

    public PagamentoDto criarPagamento(PagamentoDto dto) {
        Pagamento pagamento = PagamentoMapper.toEntity(dto);
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);
        return PagamentoMapper.toDto(pagamento);
    }

    public PagamentoDto atualizarPagamento(Long id, AtualizarPagamentoDto dto) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        if (dto.valor() != null) {
            pagamento.setValor(dto.valor());
        }
        if (dto.nome() != null) {
            pagamento.setNome(dto.nome());
        }
        if (dto.numero() != null) {
            pagamento.setNumero(dto.numero());
        }
        if (dto.expiracao() != null) {
            pagamento.setExpiracao(dto.expiracao());
        }
        if (dto.codigo() != null) {
            pagamento.setCodigo(dto.codigo());
        }
        if (dto.status() != null) {
            pagamento.setStatus(Status.valueOf(dto.status()));
        }
        if (dto.pedidoId() != null) {
            pagamento.setPedidoId(dto.pedidoId());
        }
        if (dto.formaDePagamentoId() != null) {
            pagamento.setFormaDePagamentoId(dto.formaDePagamentoId());
        }
        repository.save(pagamento);
        return PagamentoMapper.toDto(pagamento);
    }

    public void excluirPagamento(Long id) {
        repository.deleteById(id);
    }
    public void confirmarPagamento(Long id){
        Optional<Pagamento> pagamento = repository.findById(id);

        if (!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO);
        repository.save(pagamento.get());
        pedido.atualizaPagamento(pagamento.get().getPedidoId());
    }


    public void marcarPagamentoComoAutorizadoComIntegracaoPendente(Long id) {
        Optional<Pagamento> pagamento = repository.findById(id);

        if (!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        repository.save(pagamento.get());
    }
}
