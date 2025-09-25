package br.com.heyfood.pagamentos.mapper;

import br.com.heyfood.pagamentos.dto.PagamentoDto;
import br.com.heyfood.pagamentos.model.Pagamento;
import br.com.heyfood.pagamentos.model.Status;

public class PagamentoMapper {
    public static Pagamento toEntity(PagamentoDto dto) {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(dto.id());
        pagamento.setValor(dto.valor());
        pagamento.setNome(dto.nome());
        pagamento.setNumero(dto.numero());
        pagamento.setExpiracao(dto.expiracao());
        pagamento.setCodigo(dto.codigo());
        if (dto.status() != null) {
            pagamento.setStatus(Status.valueOf(dto.status()));
        }
        pagamento.setPedidoId(dto.pedidoId());
        pagamento.setFormaDePagamentoId(dto.formaDePagamentoId());
        return pagamento;
    }

    public static PagamentoDto toDto(Pagamento pagamento) {
        return new PagamentoDto(
                pagamento.getId(),
                pagamento.getValor(),
                pagamento.getNome(),
                pagamento.getNumero(),
                pagamento.getExpiracao(),
                pagamento.getCodigo(),
                pagamento.getStatus() != null ? pagamento.getStatus().name() : null,
                pagamento.getPedidoId(),
                pagamento.getFormaDePagamentoId()
        );
    }
}
