package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
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

    public void propoe(Lance lance){
        double valorLance = lance.getValor();

        if (valorLance > maiorLance)
            maiorLance = valorLance;
        if (valorLance < menorLance)
            menorLance = valorLance;
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
}
