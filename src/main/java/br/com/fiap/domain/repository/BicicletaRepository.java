package br.com.fiap.domain.repository;

import br.com.fiap.domain.entity.Bicicleta;
import br.com.fiap.infra.ConnectionFactory;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class BicicletaRepository implements Repository<Bicicleta, Long> {

    private ClienteRepository clienteRepository = ClienteRepository.build();

    public ConnectionFactory factory;

    public static final AtomicReference<BicicletaRepository> instance = new AtomicReference<>();

    private BicicletaRepository(){
        this.factory = ConnectionFactory.build();
    }

    public static BicicletaRepository build() {
        instance.compareAndSet( null, new BicicletaRepository() );
        return instance.get();
    }



    @Override
    public List<Bicicleta> findAll() {
        List<Bicicleta> bicicletas = new ArrayList<>();

        var sql = "SELECT * FROM NTX_BICICLETAS";

        Connection conn = factory.getConnection();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.isBeforeFirst()){
                while (rs.next()){
                    var id = rs.getLong("ID_CLIENTE");
                    var numeroDeSerie = rs.getString("NUMERO_DE_SERIE");
                    var marca = rs.getString("MARCA");
                    var modelo = rs.getString("NASCIMENTO");
                    var tipo = rs.getString("EMAIL");
                    var cor = rs.getString("TELEFONR");
                    var valor = rs.getFloat("VALOR");
                    var idCliente = rs.getLong("ID_CLIENTES");
                    var cliente = clienteRepository.findById(idCliente);
                    bicicletas.add(new Bicicleta(id, numeroDeSerie, marca, modelo, tipo, cor, valor, cliente));
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        }finally {
            fecharObjetos(rs, st, conn);
        }
        return bicicletas;
    }

    @Override
    public Bicicleta findById(Long id) {

        Bicicleta bicicleta = null;

        var sql = "SELECT * FROM NTX_BICICLETA WHERE ID_BICICLETA = ?";

        Connection conn = factory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareCall( sql );
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()){
                while (rs.next()){
                    var idBicicleta = rs.getLong("ID_BICICLETA");
                    var numeroDeSerie = rs.getString("NUMERO_DE_SERIE");
                    var marca = rs.getString("MARCA");
                    var modelo = rs.getString("NASCIMENTO");
                    var tipo = rs.getString("EMAIL");
                    var cor = rs.getString("TELEFONR");
                    var valor = rs.getFloat("VALOR");
                    var idCliente = rs.getLong("ID_CLIENTES");
                    var cliente = clienteRepository.findById(idCliente);
                    bicicleta = new Bicicleta(idBicicleta, numeroDeSerie, marca, modelo, tipo, cor, valor, cliente);
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        }finally {
            fecharObjetos( rs, ps, conn );
        }
        return bicicleta;
    }

    @Override
    public Bicicleta persist(Bicicleta bicicleta) {

        var sql = "INSERT INTO NTX_BICICLETA (ID_BICICLETA, NUMERO_DE_SERIE, MARCA, MODELO, TIPO, COR, VALOR, ID_CLIENTE) VALUES (SQ_BICICLETA.nextval, ?,?,?,?,?,?,?,?)";

        Connection conn = factory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql, new String[]{"ID_BICICLETA"});
            ps.setString(1, bicicleta.getNunmeroDeSerie());
            ps.setString(2, bicicleta.getMarca());
            ps.setString(3, bicicleta.getModelo());
            ps.setString(4, bicicleta.getTipo());
            ps.setString(5, bicicleta.getCor());
            ps.setFloat(6, bicicleta.getValor());
            ps.setLong(7, bicicleta.getCliente().getId());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()){
                final Long id = rs.getLong(1);
                bicicleta.setId(id);
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possivel salvar o Movie no banco de dados:  " + e.getMessage() );
        }finally {
            fecharObjetos( rs, ps, conn );
        }
        return bicicleta;
    }

    @Override
    public void fecharObjetos(ResultSet rs, Statement st, Connection con) {
        Repository.super.fecharObjetos(rs, st, con);
    }

    @Override
    public Bicicleta findByName(String texto) {

        Bicicleta bicicleta = null;

        var sql = "SELECT * FROM NTX_BICICLETA WHERE trim(UPPER(NUMERO_DE_SERIE)) = ?";

        Connection conn = factory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareCall(sql);
            ps.setString(1, texto.toUpperCase().trim());
            rs = ps.executeQuery();

            if (rs.isBeforeFirst()){
                while (rs.next()){
                    var idBicicleta = rs.getLong("ID_BICICALETA");
                    var numeroDeSerie = rs.getString("NUMERO_DE_SERIE");
                    var marca = rs.getString("MARCA");
                    var modelo = rs.getString("MODELO");
                    var tipo = rs.getString("TIPO");
                    var cor = rs.getString("COR");
                    var valor = rs.getFloat("VALOR");
                    var idCliente = rs.getLong("ID_CLIENTE");
                    var cliente = clienteRepository.findById( idCliente );
                    bicicleta = new Bicicleta(idBicicleta, numeroDeSerie, marca, modelo, tipo, cor, valor, cliente);
                }
            }
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a consulta ao banco de dados: " + e.getMessage() );
        }finally {
            fecharObjetos( rs, ps, conn );
        }

        return bicicleta;
    }
}
