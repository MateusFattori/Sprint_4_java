package br.com.fiap.domain.resource;

import br.com.fiap.domain.dto.BicicletaDTO;
import br.com.fiap.domain.entity.Bicicleta;
import br.com.fiap.domain.service.BicicletaService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Path("/bicicleta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BicicletaResource implements Resource<BicicletaDTO, Long> {

    @Context
    UriInfo uriInfo;

    private final BicicletaService service = new BicicletaService();


    @GET
    @Override
    public Response findAll() {
        List<Bicicleta> all = service.findAll();
        return Response.ok(all.stream().map(BicicletaDTO::of).toList()).build();
    }

    @GET
    @Path("/{id}")
    @Override
    public Response findById(@PathParam("id") Long id) {
        Bicicleta bicicleta = service.findById(id);
        if (Objects.isNull(bicicleta)) return Response.status(404).build();
        return Response.ok(BicicletaDTO.of(bicicleta)).build();
    }

    @POST
    @Override
    public Response persist(BicicletaDTO bicicleta) {

        Bicicleta persisted = service.persist(BicicletaDTO.of(bicicleta));

        if (Objects.isNull(persisted)) return Response.status(400).build();

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(String.valueOf(persisted.getId())).build();

        return Response.created(uri).entity(BicicletaDTO.of(persisted)).build();
    }
}
