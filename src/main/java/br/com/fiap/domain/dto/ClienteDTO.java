package br.com.fiap.domain.dto;
import br.com.fiap.domain.entity.Cliente;
import br.com.fiap.domain.repository.ClienteRepository;
import br.com.fiap.domain.service.ClienteService;


import java.util.Date;
import java.util.Objects;

public record ClienteDTO(
        Long id,

        String nome,

        String CPF,

        Date nascimento,

        String email,

        String telefone
        ) {

    private static ClienteService service = new ClienteService();

    public  static Cliente of(ClienteDTO dto) {
        if (Objects.isNull(dto)) return null;

        if (Objects.nonNull(dto.id)) return service.findById(dto.id);

        Cliente c = new Cliente();
        c.setId(null);
        c.setNome(dto.nome);
        c.setCPF(dto.CPF);
        c.setNacimento(dto.nascimento);
        c.setEmail(dto.email);
        c.setTelefone(dto.telefone);

        return c;
    }

    public static ClienteDTO of(Cliente c){
        return new ClienteDTO(c.getId(), c.getNome(), c.getCPF(), c.getNacimento(), c.getEmail(), c.getTelefone());
    }
}
