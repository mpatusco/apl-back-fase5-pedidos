package com.fiap.pedidos.gateways.entities;

import com.fiap.pedidos.entities.DescricaoProduto;
import com.fiap.pedidos.entities.NomeProduto;
import com.fiap.pedidos.entities.Produto;
import com.fiap.pedidos.entities.ValorProduto;
import com.fiap.pedidos.utils.enums.TipoProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "produtos")
public class ProdutoEntity {
    @Id
    @GeneratedValue
    private UUID idProduto;

    @Column(name = "txt_nome_produto")
    @Embedded
    private NomeProduto nomeProduto;

    @Column(name = "txt_descricao_produto")
    @Embedded
    private DescricaoProduto descricaoProduto;

    private String tipoProduto;

    @Column(name = "v_produto")
    @Embedded
    private ValorProduto valorProduto;


    @Column(name = "dt_h_atualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_h_inclusao")
    private Date dataCriacao;

    private Boolean ativo;

    public Produto to() {
        return Produto.builder()
                .idProduto(this.getIdProduto())
                .nomeProduto(this.getNomeProduto())
                .descricaoProduto(this.getDescricaoProduto())
                .tipoProduto(TipoProduto.fromCodigo(this.getTipoProduto()))
                .valorProduto(this.getValorProduto())
                .ativo(this.getAtivo())
                .build();
    }

    public ProdutoEntity from(Produto produto, boolean isCreated) {
        ProdutoEntityBuilder produtoEntityBuilder = ProdutoEntity.builder()
                .idProduto(produto.getIdProduto())
                .nomeProduto(produto.getNomeProduto())
                .descricaoProduto(produto.getDescricaoProduto())
                .tipoProduto(produto.getTipoProduto().getCodigo())
                .valorProduto(produto.getValorProduto())
                .ativo(produto.getAtivo());

        if(isCreated) {
            produtoEntityBuilder.dataCriacao(this.obterDataHoraAtual());
            produtoEntityBuilder.ativo(true);
        }

            produtoEntityBuilder.dataAtualizacao(this.obterDataHoraAtual());

        return produtoEntityBuilder.build();
    }

    private Date obterDataHoraAtual(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE),
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
        return new Date(calendar.getTime().getTime());
    }
}
