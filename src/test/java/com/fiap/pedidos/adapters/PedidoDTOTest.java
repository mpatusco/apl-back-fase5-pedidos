package com.fiap.pedidos.adapters;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.utils.enums.StatusPedido;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoDTOTest {

    @Nested
    class ConverterParaPedidoDto {

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @Description("Converter Pedido para DTO")
        void converterPedidoParaDto() {
            Pedido pedido = Helper.gerarPedidoComClienteEProdutos();
            var uuid = UUID.randomUUID();
            PedidoDTO pedidoDTO = PedidoDTO.from(pedido);


            pedidoDTO.setIdPedido(uuid);
            pedidoDTO.setStatusPedido(StatusPedido.P);
            pedidoDTO.setValorPedido(BigDecimal.TEN);
            pedidoDTO.setProdutos(null);
            pedidoDTO.setIdCliente(uuid);
            pedidoDTO.setDataAtualizacao(new Date());
            pedidoDTO.setDataInclusao(new Date());

            PedidoDTO pedidoDTO1 = PedidoDTO.from(pedido);

            pedidoDTO1.setIdPedido(uuid);
            pedidoDTO1.setStatusPedido(StatusPedido.P);
            pedidoDTO1.setValorPedido(BigDecimal.TEN);
            pedidoDTO1.setProdutos(null);
            pedidoDTO1.setIdCliente(uuid);
            pedidoDTO1.setDataAtualizacao(new Date());
            pedidoDTO1.setDataInclusao(new Date());


            String dto = PedidoDTO.builder()
                    .idPedido(uuid)
                    .valorPedido(BigDecimal.TEN).toString();

            assertThat(dto).contains(uuid.toString(), BigDecimal.TEN.toString());


            assertThat(pedidoDTO).isNotNull();
            assertThat(pedidoDTO.getProdutos()).isNull();
            assertThat(pedidoDTO.getStatusPedido()).isEqualTo(StatusPedido.P);
            assertThat(pedidoDTO.getValorPedido()).isEqualTo(BigDecimal.TEN);


            assertThat(pedidoDTO).isNotNull();
            assertThat(pedidoDTO.getIdPedido()).isEqualTo(pedidoDTO1.getIdPedido());
            assertThat(pedidoDTO.getValorPedido()).isEqualTo(pedidoDTO1.getValorPedido());

            assertThat(pedidoDTO.equals(pedidoDTO1)).isTrue();
            assertThat(pedidoDTO.hashCode()).isEqualTo(pedidoDTO1.hashCode());
            assertThat(pedidoDTO.canEqual(pedidoDTO1)).isTrue();
            assertThat(pedidoDTO.toString()).contains(pedidoDTO1.getValorPedido().toString(), pedidoDTO1.getIdPedido().toString());
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        void testarConstrutoVazio() {
            PedidoDTO pedido = new PedidoDTO();
            PedidoDTO pedido1 = new PedidoDTO();

            assertThat(pedido).isNotNull();
            assertThat(pedido.getProdutos()).isNull();
            assertThat(pedido.getValorPedido()).isNull();
            assertThat(pedido.getIdPedido()).isNull();
            assertThat(pedido.getStatusPedido()).isNull();
            assertThat(pedido.getDataAtualizacao()).isNull();
            assertThat(pedido.getDataInclusao()).isNull();

            assertThat( pedido.equals(pedido1)).isTrue();
        }
    }
}
