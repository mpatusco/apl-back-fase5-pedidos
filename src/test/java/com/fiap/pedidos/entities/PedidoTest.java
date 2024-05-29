package com.fiap.pedidos.entities;
import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.helpers.Helper;
import com.fiap.pedidos.utils.enums.StatusPedido;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class PedidoTest {

    @Test
    void testEqualsAndHashCode() {
        UUID idPedido = UUID.randomUUID();
        Cliente cliente = new Cliente();
        List<Produto> produtos = Arrays.asList(new Produto(), new Produto());
        StatusPedido statusPedido = StatusPedido.A;
        BigDecimal valorPedido = new BigDecimal("50.00");
        Date dataInclusao = new Date();
        Date dataAtualizacao = new Date();

        Pedido pedido1 = new Pedido(idPedido, cliente, produtos, statusPedido, valorPedido, dataInclusao, dataAtualizacao);
        Pedido pedido2 = new Pedido(idPedido, cliente, produtos, statusPedido, valorPedido, dataInclusao, dataAtualizacao);

        assertThat(pedido1).isEqualTo(pedido2);
        assertThat(pedido1.hashCode()).isEqualTo(pedido2.hashCode());
    }

    @Test
    void testInicializarVazio() {
        Pedido pedido = new Pedido();
        assertThat(pedido.getIdPedido()).isNull();
        assertThat(pedido.getValorPedido()).isNull();
        assertThat(pedido.getCliente()).isNull();
        assertThat(pedido.getProdutos()).isNull();
    }

    @Test
    void testToString() {
        UUID idPedido = UUID.randomUUID();
        Cliente cliente = Helper.gerarClienteComTodosDadosEID();
        List<Produto> produtos = Arrays.asList(Helper.gerarProdutoBebida(), Helper.gerarProdutoBebida());
        StatusPedido statusPedido = StatusPedido.A;
        BigDecimal valorPedido = new BigDecimal("50.00");

        Pedido pedido = new Pedido(idPedido, cliente, produtos, statusPedido, valorPedido, null, null);

        assertThat(pedido.toString()).contains(idPedido.toString(), cliente.toString(), produtos.toString(), statusPedido.toString(), valorPedido.toString());
    }
}
