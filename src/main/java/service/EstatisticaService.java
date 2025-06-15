package service;

import repository.EstatisticaRepository;

import java.sql.SQLException;
import java.util.List;

public class EstatisticaService {

    private EstatisticaRepository repo = new EstatisticaRepository();

    public EstatisticaRepository.PecaVenda getPecaMaisVendida() throws SQLException {
        return repo.getPecaComMaisIngressos();
    }

    public EstatisticaRepository.PecaVenda getPecaMenosVendida() throws SQLException {
        return repo.getPecaComMenosIngressos();
    }

    public EstatisticaRepository.SessaoOcupacao getSessaoMaisOcupada() throws SQLException {
        return repo.getSessaoComMaiorOcupacao();
    }

    public EstatisticaRepository.SessaoOcupacao getSessaoMenosOcupada() throws SQLException {
        return repo.getSessaoComMenorOcupacao();
    }

    public EstatisticaRepository.LucroSessao getSessaoMaisLucrativa() throws SQLException {
        return repo.getSessaoMaisLucrativa();
    }

    public EstatisticaRepository.LucroSessao getSessaoMenosLucrativa() throws SQLException {
        return repo.getSessaoMenosLucrativa();
    }

    public List<EstatisticaRepository.FaturamentoMedio> getFaturamentoMedioPorPeca() throws SQLException {
        return repo.getFaturamentoMedioPorPeca();
    }
}
