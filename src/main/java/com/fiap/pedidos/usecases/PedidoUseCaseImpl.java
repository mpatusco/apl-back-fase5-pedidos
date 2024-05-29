package com.fiap.pedidos.usecases;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.exceptions.entities.PedidoOperacaoNaoSuportadaException;
import com.fiap.pedidos.interfaces.gateways.IFilaRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IPagamentoRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IPedidoProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.utils.enums.StatusPedido;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PedidoUseCaseImpl implements IPedidoUseCasePort {

    private final IPedidoProdutoRepositoryPort pedidoProdutoRepositoryPort;
    private final IPedidoRepositoryPort pedidoRepositoryPort;
    private final IPagamentoRepositoryPort pagamentoRepositoryPort;
    private final IFilaRepositoryPort filaRepositoryPort;

    @Override
    public Pedido iniciarPedido(Pedido pedido) {
        UUID idCliente = pedido.getCliente().getId();

        List<Pedido> pedidosAtivos = this.pedidoRepositoryPort
                .buscarPedidosPorClienteEStatus(idCliente, StatusPedido.A);

        if (pedidosAtivos.isEmpty()) {
            pedido.setValorPedido(new BigDecimal("0.0"));
            return pedidoRepositoryPort.cadastrar(pedido);
        }

        pedidosAtivos.sort(Comparator.comparing(Pedido::getDataInclusao).reversed());
        return pedidosAtivos.get(0);
    }

    @Override
    public Pedido atualizarPedido(
            UUID idPedido,
            TipoAtualizacao tipoAtualizacao,
            Pedido pedidoRequest,
            StatusPedido statusPedido) {

        Pedido pedidoExistente = buscarPorId(idPedido);

        switch (tipoAtualizacao){
            case F -> pedidoExistente.setStatusPedido(statusPedido);
            case C -> {
                if (pedidoExistente.getStatusPedido() != StatusPedido.A) {
                    throw new PedidoOperacaoNaoSuportadaException("Pedido não está aberto para edição.");
                }
            }
            case I -> {
                if (pedidoExistente.getStatusPedido() != StatusPedido.A) {
                    throw new PedidoOperacaoNaoSuportadaException("Pedido não está aberto para edição.");
                }
                pedidoExistente.setProdutos(pedidoRequest.getProdutos());
                pedidoExistente.setValorPedido(pedidoRequest.getValorPedido());
                pedidoExistente.setStatusPedido(pedidoRequest.getStatusPedido());
                pedidoExistente.setDataAtualizacao(new Date());
            }
            case P -> this.atualizarStatusPagamento(pedidoExistente);
        }

        Pedido pedido = this.atualizarPedido(pedidoExistente);

        pedido.setProdutos(this.pedidoProdutoRepositoryPort
                .obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(pedido.getIdPedido()));

        return pedido;
    }

    private void atualizarStatusPagamento(Pedido pedido) {
         var pagOk = this.pagamentoRepositoryPort.consultaPagamento(pedido.getIdPedido());

        if(pagOk){
            pedido.setStatusPedido(StatusPedido.R);
            filaRepositoryPort.inserePedidoNaFila(pedido.getIdPedido(), pedido.getCliente().getId());
        }
    }

    @Override
    public Pedido atualizarPedido(Pedido pedido) {
        return pedidoRepositoryPort.atualizarPedido(pedido);
    }

    @Override
    public Pedido buscarPorId(UUID id) {
        var pedidoOptional = pedidoRepositoryPort.buscarPorId(id);

        if (pedidoOptional.isEmpty()) {
            throw new PedidoNaoEncontradoException();
        }

        pedidoOptional
                .get()
                .setProdutos(this.pedidoProdutoRepositoryPort.obterTodosOsProdutosAssociadosAoPedidoPeloIdPedido(id));

        return pedidoOptional.get();
    }

    @Override
    public List<Pedido> buscarTodos(int pageNumber, int pageSize) {
        return pedidoRepositoryPort.buscarTodos(pageNumber, pageSize);
    }
}
