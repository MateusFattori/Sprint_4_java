package br.com.fiap.domain.service;

import br.com.fiap.domain.entity.Bicicleta;
import br.com.fiap.domain.repository.BicicletaRepository;

import java.util.List;
import java.util.Objects;

public class BicicletaService implements Service<Bicicleta, Long> {

    BicicletaRepository repo = BicicletaRepository.build();

    @Override
    public List<Bicicleta> findAll() {
        return repo.findAll();
    }

    @Override
    public Bicicleta findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Bicicleta persist(Bicicleta bicicleta) {
        var b = repo.findByName(bicicleta.getNunmeroDeSerie());
        if (Objects.nonNull(b)){
            System.err.println("Já existe genero cadastrado com o mesmo nome: " + b.getNunmeroDeSerie());
            return b;
        }
        return repo.persist(bicicleta);
    }
}
