package main;

import modelo.Financiamento;
import util.InterfaceUsuario;

public class Main {
    public static void main(String[] args) {
        InterfaceUsuario interfaceUsuario = new InterfaceUsuario();

        double valorImovel = interfaceUsuario.pedirValorImovel();
        int prazoFinanciamentoAnos = interfaceUsuario.pedirPrazoFinanciamento();
        double taxaJuros = interfaceUsuario.pedirTaxaJuros();

        Financiamento financiamento = new Financiamento(valorImovel, prazoFinanciamentoAnos, taxaJuros);
        financiamento.exibirDadosFinanciamento();
    }
}