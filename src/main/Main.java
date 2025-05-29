package main;
import modelo.Financiamento;
import util.InterfaceUsuario;

public class Main {
    public static void main(String[] args) {

        InterfaceUsuario interfaceUsuario = new InterfaceUsuario();
        double taxaJuros = InterfaceUsuario.pedirTaxaJuros();
        int prazoFinanciamentoAnos = interfaceUsuario.pedirPrazoFinanciamento();
        double valorImovel = interfaceUsuario.pedirValorImovel();

        Financiamento calculoFinanciamento = new Financiamento(valorImovel, prazoFinanciamentoAnos, taxaJuros);
    }
}