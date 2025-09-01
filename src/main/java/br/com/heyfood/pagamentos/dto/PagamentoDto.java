package br.com.heyfood.pagamentos.dto;

public record PagamentoDto(
        Long id,
        Long pedidoId,
        Long formaDePagamentoId,
        String nome,
        String numero,
        String expiracao,
        String codigo,
        String status,
        Double valor
) {
}
