package br.com.alura.leilao.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class LeilaoTest {

    private static final double DELTA = 0.0001;
    private final Leilao CONSOLE = new Leilao("Console");
    private final Usuario ALEX = new Usuario("Alex");

    @Test
    public void deve_DevolverDescricao_QuandoRecebeDescricao() {
        String descricaoDevolvida = CONSOLE.getDescricao();

        assertEquals("Console", descricaoDevolvida);
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));

        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeDoisLancesEmOrdemCrescente() {
        final Usuario FRAN = new Usuario("Fran");

        CONSOLE.propoe(new Lance(ALEX, 100.0));
        CONSOLE.propoe(new Lance(FRAN, 200.0));

        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeDoisLancesEmOrdemDecrescente() {
        final Usuario FRAN = new Usuario("Fran");

        CONSOLE.propoe(new Lance(FRAN, 200.0));
        CONSOLE.propoe(new Lance(ALEX, 100.0));

        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));

        double menorLanceDevolvidodo = CONSOLE.getMenorLance();

        assertEquals(200.0, menorLanceDevolvidodo, DELTA);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeDoisLancesEmOrdemCrescente() {
        final Usuario FRAN = new Usuario("Fran");

        CONSOLE.propoe(new Lance(ALEX, 100.0));
        CONSOLE.propoe(new Lance(FRAN, 200.0));

        double menorLanceDevolvidodo = CONSOLE.getMenorLance();

        assertEquals(100.0, menorLanceDevolvidodo, DELTA);
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeDoisLancesEmOrdemDecrescente() {
        final Usuario FRAN = new Usuario("Fran");

        CONSOLE.propoe(new Lance(FRAN, 200.0));
        CONSOLE.propoe(new Lance(ALEX, 100.0));

        double menorLanceDevolvidodo = CONSOLE.getMenorLance();

        assertEquals(100.0, menorLanceDevolvidodo, DELTA);
    }

    @Test
    public void deve_DeveDevolverTresMaioresLances_QuandoRecebeTresLances(){
        final Usuario FRAN = new Usuario("Fran");
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(FRAN, 300.0));
        CONSOLE.propoe(new Lance(ALEX, 400.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();
        assertEquals(3, tresMaioresLancesDevolvidos.size());
        assertEquals(400.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(300.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_DeveDevolverTresMaioresLances_QuandoNaoRecebeLances(){
        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();
        assertEquals(0, tresMaioresLancesDevolvidos.size());
    }

    @Test
    public void deve_DeveDevolverTresMaioresLances_QuandoRecebeUmLances(){
        CONSOLE.propoe(new Lance(ALEX, 200.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();
        assertEquals(1, tresMaioresLancesDevolvidos.size());
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
    }

    @Test
    public void deve_DeveDevolverTresMaioresLances_QuandoRecebeDoisLances(){
        final Usuario FRAN = new Usuario("Fran");
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(FRAN, 300.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();
        assertEquals(2, tresMaioresLancesDevolvidos.size());
        assertEquals(300.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
    }

    @Test
    public void deve_DeveDevolverTresMaioresLances_QuandoRecebeMaisDeTresLances(){
        final Usuario FRAN = new Usuario("Fran");
        CONSOLE.propoe(new Lance(FRAN, 100.0));
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(FRAN, 300.0));
        CONSOLE.propoe(new Lance(ALEX, 400.0));

        final List<Lance> tresMaioresLancesDevolvidosParaQuatroLances = CONSOLE.tresMaioresLances();
        assertEquals(3, tresMaioresLancesDevolvidosParaQuatroLances.size());
        assertEquals(400.0, tresMaioresLancesDevolvidosParaQuatroLances.get(0).getValor(), DELTA);
        assertEquals(300.0, tresMaioresLancesDevolvidosParaQuatroLances.get(1).getValor(), DELTA);
        assertEquals(200.0, tresMaioresLancesDevolvidosParaQuatroLances.get(2).getValor(), DELTA);

        CONSOLE.propoe(new Lance(FRAN, 500.0));

        final List<Lance> tresMaioresLancesDevolvidosParaCincoLances = CONSOLE.tresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosParaCincoLances.size());
        assertEquals(500.0, tresMaioresLancesDevolvidosParaCincoLances.get(0).getValor(), DELTA);
        assertEquals(400.0, tresMaioresLancesDevolvidosParaCincoLances.get(1).getValor(), DELTA);
        assertEquals(300.0, tresMaioresLancesDevolvidosParaCincoLances.get(2).getValor(), DELTA);
    }
}