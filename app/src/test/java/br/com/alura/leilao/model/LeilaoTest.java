package br.com.alura.leilao.model;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import br.com.alura.leilao.execption.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.execption.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.execption.UsuarioJaDeuCincoLancesException;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.*;

public class LeilaoTest {

    private static final double DELTA = 0.0001;
    private static final String ERA_ESPERADO_UMA_RUNTIME_EXCEPTION = "Era esperado uma RuntimeException";
    private final Leilao CONSOLE = new Leilao("Console");
    private final Usuario ALEX = new Usuario("Alex");

    @Test
    public void deve_DevolverDescricao_QuandoRecebeDescricao() {
        String descricaoDevolvida = CONSOLE.getDescricao();

        //assertEquals("Console", descricaoDevolvida);
        assertThat(descricaoDevolvida, is("Console"));
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        assertThat(maiorLanceDevolvido, closeTo(200.0, DELTA));
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeDoisLancesEmOrdemCrescente() {
        final Usuario FRAN = new Usuario("Fran");

        CONSOLE.propoe(new Lance(ALEX, 100.0));
        CONSOLE.propoe(new Lance(FRAN, 200.0));

        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        assertThat(maiorLanceDevolvido, closeTo(200.0, DELTA));
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));

        double menorLanceDevolvidodo = CONSOLE.getMenorLance();

        assertThat(menorLanceDevolvidodo, closeTo(200.0, DELTA));
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeDoisLancesEmOrdemCrescente() {
        final Usuario FRAN = new Usuario("Fran");

        CONSOLE.propoe(new Lance(ALEX, 100.0));
        CONSOLE.propoe(new Lance(FRAN, 200.0));

        double menorLanceDevolvidodo = CONSOLE.getMenorLance();

        assertThat(menorLanceDevolvidodo, closeTo(100.0, DELTA));
    }

    @Test
    public void deve_DeveDevolverTresMaioresLances_QuandoRecebeTresLances() {
        final Usuario FRAN = new Usuario("Fran");
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(FRAN, 300.0));
        CONSOLE.propoe(new Lance(ALEX, 400.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();

//        assertThat(tresMaioresLancesDevolvidos, hasSize(3));
        //assertThat(tresMaioresLancesDevolvidos, hasItem(new Lance(ALEX, 400.0)));

//        assertEquals(400.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
//        assertEquals(300.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
//        assertEquals(200.0, tresMaioresLancesDevolvidos.get(2).getValor(), DELTA);

        assertThat(tresMaioresLancesDevolvidos, both(Matchers.<Lance>hasSize(3)).and(contains(
                new Lance(ALEX, 400.0),
                new Lance(FRAN, 300.0),
                new Lance(ALEX, 200.0)
        )));
    }

    @Test
    public void deve_DeveDevolverTresMaioresLances_QuandoNaoRecebeLances() {
        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();
        assertEquals(0, tresMaioresLancesDevolvidos.size());
    }

    @Test
    public void deve_DeveDevolverTresMaioresLances_QuandoRecebeUmLances() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();
        assertEquals(1, tresMaioresLancesDevolvidos.size());
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
    }

    @Test
    public void deve_DeveDevolverTresMaioresLances_QuandoRecebeDoisLances() {
        final Usuario FRAN = new Usuario("Fran");
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(FRAN, 300.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();
        assertEquals(2, tresMaioresLancesDevolvidos.size());
        assertEquals(300.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
    }

    @Test
    public void deve_DeveDevolverTresMaioresLances_QuandoRecebeMaisDeTresLances() {
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

    @Test
    public void deve_DevolverValorZeroParaMaiorLance_QuandoNaoTiverLance() {
        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        assertEquals(0.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverValorZeroParaMenorLance_QuandoNaoTiverLance() {
        double menorLanceDevolvido = CONSOLE.getMenorLance();

        assertEquals(0.0, menorLanceDevolvido, DELTA);
    }

    @Test(expected = LanceMenorQueUltimoLanceException.class)
    public void naoDeve_AdicionarLance_QuandoForMenorQueOMaiorLance() {
        final Usuario FRAN = new Usuario("Fran");
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(FRAN, 100.0));
    }

    @Test(expected = LanceSeguidoDoMesmoUsuarioException.class)
    public void naoDeve_AdicionarLance_QuandoForOMesmoUsuarioDoUltimoLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Alex"), 400.0));
    }

    @Test(expected = UsuarioJaDeuCincoLancesException.class)
    public void naoDeve_AdicionarLance_QuandoUsuarioDerMaisQueCincoLances() {
        final Usuario FRAN = new Usuario("Fran");
        CONSOLE.propoe(new Lance(FRAN, 100.0));
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(FRAN, 300.0));
        CONSOLE.propoe(new Lance(ALEX, 400.0));
        CONSOLE.propoe(new Lance(FRAN, 500.0));
        CONSOLE.propoe(new Lance(ALEX, 600.0));
        CONSOLE.propoe(new Lance(FRAN, 700.0));
        CONSOLE.propoe(new Lance(ALEX, 800.0));
        CONSOLE.propoe(new Lance(FRAN, 900.0));
        CONSOLE.propoe(new Lance(ALEX, 1000.0));
        CONSOLE.propoe(new Lance(FRAN, 1100.0));
    }
}