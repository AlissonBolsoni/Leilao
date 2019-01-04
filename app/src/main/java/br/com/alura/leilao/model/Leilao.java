package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao implements Serializable {

    public static final String LANCE_EH_MENOR_QUE_O_MAIOR_LANCE = "Lance eh menor que o maior lance";
    public static final String USUARIO_JA_DEU_CINCO_LANCES = "Usuário já deu cinco lances";
    public static final String USUARIO_EH_O_MESMO_DO_ULTIMO_LANCE = "Usuario eh o mesmo do ultimo lance";
    private final String descricao;
    private final List<Lance> lances;
    private double maiorLance;
    private double menorLance;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<>();
        maiorLance = 0.0;
        menorLance = 0.0;
    }

    public void propoe(Lance lance) {
        if (lanceNaoValido(lance)) return;

        lances.add(lance);

        double valorLance = lance.getValor();
        if (defineMaiorEMenorLancePrimeiroLance(valorLance)) return;

        Collections.sort(lances);
        calculaMaiorLance(valorLance);
        CalculaMenorLance(valorLance);

    }

    private boolean defineMaiorEMenorLancePrimeiroLance(double valorLance) {
        if (lances.size() == 1) {
            maiorLance = valorLance;
            menorLance = valorLance;
            return true;
        }
        return false;
    }

    private boolean lanceNaoValido(Lance lance) {
        double valorLance = lance.getValor();
        if (lanceMenorQueUltimoLance(valorLance)) throw new RuntimeException(LANCE_EH_MENOR_QUE_O_MAIOR_LANCE);

        if (temLances()) {
            Usuario usuarioNovo = lance.getUsuario();

            if (usuarioEhOMesmoDoUltimoLance(usuarioNovo)) throw new RuntimeException(USUARIO_EH_O_MESMO_DO_ULTIMO_LANCE);

            if (usuarioDeuCincoLances(usuarioNovo)) throw new RuntimeException(USUARIO_JA_DEU_CINCO_LANCES);

        }
        return false;
    }

    private boolean temLances() {
        return !lances.isEmpty();
    }

    private boolean usuarioDeuCincoLances(Usuario usuarioNovo) {
        int lancesDoUsuario = 0;
        for (Lance l : lances){
            Usuario usuarioExistente = l.getUsuario();
            if (usuarioExistente.equals(usuarioNovo)){
                lancesDoUsuario++;
                if (lancesDoUsuario == 5) return true;
            }
        }
        return false;
    }

    private boolean usuarioEhOMesmoDoUltimoLance(Usuario usuarioNovo) {
        Usuario ultimoUsuario = lances.get(0).getUsuario();
        if (usuarioNovo.equals(ultimoUsuario)) return true;
        return false;
    }

    private boolean lanceMenorQueUltimoLance(double valorLance) {
        if (valorLance < maiorLance) return true;
        return false;
    }

    private void CalculaMenorLance(double valorLance) {
        if (valorLance < menorLance)
            menorLance = valorLance;
    }

    private void calculaMaiorLance(double valorLance) {
        if (valorLance > maiorLance)
            maiorLance = valorLance;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getMaiorLance() {
        return maiorLance;
    }

    public double getMenorLance() {
        return menorLance;
    }

    public List<Lance> tresMaioresLances() {
        int size = lances.size();
        if (size > 3)
            size = 3;

        return lances.subList(0, size);
    }

    public int quantidadesDeLances() {
        return lances.size();
    }
}
