package com.fiap.pedidos.gateways;

import com.fiap.pedidos.interfaces.gateways.IPagamentoRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.PagamentoRepository;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PagamentoRepositoryAdapterTest {

    private IPagamentoRepositoryPort pagamentoRepositoryPortAdapter;

    @Mock
    private PagamentoRepository pagamentoRepository;

    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        pagamentoRepositoryPortAdapter = new PagamentoRepositoryAdapter(pagamentoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested class ConsultaPagamento {

        @Test
        @Severity(SeverityLevel.BLOCKER)
        @Description("Consulta pagamento")
        void deveConsultarPagamento() {
            var header = new HashMap<String, Collection<String>>();
            header.put("header", Collections.EMPTY_LIST);

            var request = Request
                    .create(
                            Request.HttpMethod.GET,
                            "" ,
                            header,
                            null,
                            null,
                            null);
            var response = Response.builder()
                            .request(request)
                            .status(200)
                            .build();

            when(pagamentoRepository.consultarPagamento(any(UUID.class))).thenReturn(response);

            pagamentoRepositoryPortAdapter.consultaPagamento(UUID.randomUUID());

            verify(pagamentoRepository, times(1)).consultarPagamento(any(UUID.class));
        }
    }
}
