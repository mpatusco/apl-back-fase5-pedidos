package com.fiap.pedidos.interfaces.repositories;

import com.fiap.pedidos.gateways.entities.PedidoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, UUID> {

    @Query(value = "SELECT * FROM pedidos WHERE id_cliente = ?1 AND id_status LIKE ?2",
    nativeQuery = true)
    List<PedidoEntity> findByIdClienteAndStatusPedido(UUID idCliente, String statusPedido);

    @Query(value = "SELECT * FROM pedidos WHERE id_status NOT LIKE 'P' ORDER BY CASE id_status WHEN 'C' THEN 1 WHEN 'E' THEN 2 WHEN 'R' THEN 3 WHEN 'A' THEN 4 END ASC, dt_h_inclusao ASC",
            nativeQuery = true)
    List<PedidoEntity> listagemOrdenadaPorStatusExcluindoFinalizados(Pageable pageable);
}
