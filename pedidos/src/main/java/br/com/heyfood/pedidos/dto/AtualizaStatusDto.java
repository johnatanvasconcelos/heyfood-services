package br.com.heyfood.pedidos.dto;

import br.com.heyfood.pedidos.model.Status;
import jakarta.validation.constraints.NotNull;

public record AtualizaStatusDto(
        @NotNull
        Status status
) {
}

