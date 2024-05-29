package com.fiap.pedidos.controllers;

import com.fiap.pedidos.adapters.PedidoDTO;
import com.fiap.pedidos.controllers.requestValidations.PedidoProdutoRequest;
import com.fiap.pedidos.controllers.requestValidations.PedidoRequest;
import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoProdutoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@EnableAsync
public class PedidoController {

    private final IPedidoUseCasePort pedidoUseCasePort;
    private final IPedidoProdutoUseCasePort pedidoProdutoUseCasePort;
    private final IClienteUseCasePort clienteUseCasePort;

    @PostMapping(value = {"/", ""},
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDTO> iniciarPedido(@RequestBody @NotNull PedidoRequest request) {
        Optional<Cliente> cliente = clienteUseCasePort.buscarPorId(request.getIdCliente());

        if (cliente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Pedido pedido = this.pedidoUseCasePort.iniciarPedido(request.from(request, cliente.get()));

        return Objects.nonNull(pedido) ?
                new ResponseEntity<>(PedidoDTO.from(pedido), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/{idPedido}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDTO> adicionarItem(
            @PathVariable("idPedido") UUID idPedido, @RequestBody @NotNull PedidoProdutoRequest request) {
        PedidoProduto pedidoProduto = request.from(request, idPedido);
        Pedido pedido = pedidoProdutoUseCasePort.adicionarItemNoPedido(pedidoProduto);
        return new ResponseEntity<>(PedidoDTO.from(pedido), HttpStatus.OK);
    }

    //remover item do pedido
    @DeleteMapping(value = "/{idPedido}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDTO> removerItem(
            @PathVariable("idPedido") UUID idPedido,
            @RequestBody @NotNull PedidoProdutoRequest request) {
        PedidoProduto pedidoProduto = request.from(request, idPedido);
        pedidoProduto.setPedidoId(idPedido);
        Pedido pedido = pedidoProdutoUseCasePort.removerItemDoPedido(pedidoProduto);
        return new ResponseEntity<>(PedidoDTO.from(pedido), HttpStatus.OK);
    }

    //FinalizaPedido
    @PostMapping(value = "/checkout/{idPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDTO> checkout(@PathVariable UUID idPedido) {
        return new ResponseEntity<>(PedidoDTO.from(pedidoUseCasePort
                .atualizarPedido(idPedido, TipoAtualizacao.C, null, null)), HttpStatus.OK);
    }

    @GetMapping(value = {"/", ""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PedidoDTO>> buscarTodosNaoFinalizados(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "100") int pageSize) {
        List<Pedido> pedidos = pedidoUseCasePort.buscarTodos(pageNumber, pageSize);
        List<PedidoDTO> pedidoDTOs = pedidos.stream()
                .map(PedidoDTO::from)
                .collect(Collectors.toList());
        return new ResponseEntity<>(pedidoDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{idPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDTO> buscarPedido(
            @PathVariable("idPedido") UUID idPedido) {
        var pedidoDto = PedidoDTO.from(pedidoUseCasePort.buscarPorId(idPedido));
        return new ResponseEntity<>(pedidoDto, HttpStatus.OK);
    }
}
