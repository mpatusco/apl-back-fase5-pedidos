package com.fiap.pedidos.gateways;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.gateways.entities.PedidoEntity;
import com.fiap.pedidos.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.PedidoProdutoRepository;
import com.fiap.pedidos.interfaces.repositories.PedidoRepository;
import com.fiap.pedidos.utils.enums.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoRepositoryAdapter implements IPedidoRepositoryPort {

    private final PedidoRepository pedidoRepository;
    private final PedidoProdutoRepository pedidoProdutoRepository;

    @Override
    @Transactional
    public Pedido cadastrar(Pedido pedido) {
        PedidoEntity pedidoEntity = new PedidoEntity().from(pedido, true);
        return this.pedidoRepository.save(pedidoEntity).to();
    }

    @Override
    @Transactional
    public Pedido atualizarPedido(Pedido pedido) {
        PedidoEntity existingPedidoEntity = new PedidoEntity().from(pedido, false);
        return this.pedidoRepository.save(existingPedidoEntity).to();
    }

    @Override
    @Transactional
    public void remover(UUID idPedido) {
        PedidoEntity pedidoEntity = this.pedidoRepository.findById(idPedido)
                .orElseThrow(PedidoNaoEncontradoException::new);

        if(Objects.nonNull(pedidoEntity.getProdutos()) && !pedidoEntity.getProdutos().isEmpty())
            pedidoEntity.getProdutos().forEach(pedidoProdutoEntity -> {
                this.pedidoProdutoRepository.deleteById(pedidoProdutoEntity.getId());
            });
        this.pedidoRepository.delete(pedidoEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> buscarTodos(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return this.pedidoRepository.listagemOrdenadaPorStatusExcluindoFinalizados(pageable).stream()
                .map(PedidoEntity::to)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pedido> buscarPorId(UUID idPedido) {
        return this.pedidoRepository.findById(idPedido)
                .map(PedidoEntity::to);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> buscarPedidosPorClienteEStatus(UUID idCliente, StatusPedido statusPedido) {
        return this.pedidoRepository.findByIdClienteAndStatusPedido(idCliente, statusPedido.toString())
                .stream()
                .map(PedidoEntity::to)
                .collect(Collectors.toList());
    }
}
