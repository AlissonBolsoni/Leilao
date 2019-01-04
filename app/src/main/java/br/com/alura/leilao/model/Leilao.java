package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao implements Serializable {

    private final String descricao;
    private final List<Lance> lances;
    private double maiorLance;
    private double menorLance;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<>();
        maiorLance = Double.NEGATIVE_INFINITY;
        menorLance = Double.POSITIVE_INFINITY;
    }

    public void propoe(Lance lance) {
        adicionaLance(lance);
        double valorLance = lance.getValor();

        calculaMaiorLance(valorLance);
        CalculaMenorLance(valorLance);
    }

    private void adicionaLance(Lance lance) {
        lances.add(lance);
        Collections.sort(lances);
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
}
