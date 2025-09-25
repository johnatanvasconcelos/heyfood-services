package br.com.heyfood.pagamentos.dto;

import java.math.BigDecimal;

public record AtualizarPagamentoDto(
        BigDecimal valor,
        String nome,
        String numero,
        String expiracao,
        String codigo,
        String status,
        Long pedidoId,
        Long formaDePagamentoId
) {
}
