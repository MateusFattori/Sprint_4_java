package br.com.fiap.domain.resource;

import br.com.fiap.domain.dto.ClienteDTO;
import br.com.fiap.domain.entity.Cliente;
import br.com.fiap.domain.service.ClienteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource implements Resource<ClienteDTO, Long>{

    @Context
    UriInfo uriInfo;

    private final ClienteService service = new ClienteService();

    @GET
    @Override
    public Response findAll() {
        List<Cliente> all = service.findAll();
        return Response.ok(all.stream().map(ClienteDTO::of).toList()).build();
    }

    @GET
    @Path("/{id}")
    @Override
    public Response findById(@PathParam("id") Long id) {
        Cliente cliente = service.findById(id);
        if (Objects.isNull(cliente)) return Response.status(404).build();
        return Response.ok(ClienteDTO.of(cliente)).build();
    }

    @POST
    @Override
    public Response persist(ClienteDTO cliente) {

        Cliente persisted = service.persist(ClienteDTO.of(cliente));

        if (Objects.isNull(persisted)) return Response.status(400).build();

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(String.valueOf(persisted)).build();

        return Response.created(uri).entity(ClienteDTO.of(persisted)).build();
    }
}
