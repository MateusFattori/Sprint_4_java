package br.com.fiap.domain.dto;

import br.com.fiap.domain.entity.Bicicleta;
import br.com.fiap.domain.service.BicicletaService;
import br.com.fiap.domain.service.ClienteService;

import java.util.Objects;

public record BicicletaDTO(
        Long id,

        String numeroDeSerie,

        String marca,

        String modelo,

        String tipo,

        String cor,

        Float valor,

        ClienteDTO cliente
) {

    static BicicletaService service = new BicicletaService();

    static ClienteService clienteService = new ClienteService();


    public static Bicicleta of(BicicletaDTO dto){
        if (Objects.isNull(dto)) return null;

        if (Objects.nonNull(dto.id)) return service.findById(dto.id);

        Bicicleta b = new Bicicleta();
        b.setId(null);

        var cliente = Objects.nonNull(dto.cliente) && (Objects.nonNull(dto.cliente.id())) ?
                clienteService.findById(dto.cliente.id())
                : !dto.cliente.nome().equalsIgnoreCase("")?clienteService.persist(ClienteDTO.of(dto.cliente)): null;

        b.setNunmeroDeSerie(dto.numeroDeSerie);
        b.setMarca(dto.marca);
        b.setModelo(dto.modelo);
        b.setCor(dto.cor);
        b.setValor(dto.valor);

        return b;
    }

    public static BicicletaDTO of(Bicicleta b){
        BicicletaDTO dto = new BicicletaDTO(b.getId(), b.getNunmeroDeSerie(), b.getMarca(), b.getModelo(), b.getTipo(), b.getCor(), b.getValor(), ClienteDTO.of(b.getCliente()));
        return dto;
    }

}