package modelo;
import java.util.Locale;

import util.DadosImovelInvalidosException;

public class Terreno extends Financiamento {
    private String tipoZona;

    public Terreno(double valorImovel, int prazoFinanciamento, double taxaJurosAnual, String tipoZona) throws DadosImovelInvalidosException {
        super(valorImovel, prazoFinanciamento, taxaJurosAnual);

        // Validação do tipo de zona (ignorando maiúsculas/minúsculas)
        if (!tipoZona.equalsIgnoreCase("Residencial") && !tipoZona.equalsIgnoreCase("Comercial")) {
            throw new DadosImovelInvalidosException("Tipo de zona inválido. Por favor, digite 'Residencial' ou 'Comercial'.");
        }

        this.tipoZona = tipoZona;
    }

    @Override
    public double calcularPagamentoMensal() {
        double parcelaBase = (this.getValorImovel() / (this.getPrazoFinanciamento() * 12)) * (1 + (this.getTaxaJurosAnual() / 12));
        return parcelaBase * 1.02;
    }

    @Override
    public String paraFormatoCSV() {
        // Para strings (%s), o Locale não afeta, mas mantemos o padrão.
        return super.paraFormatoCSV() + String.format(Locale.US, ",%s", this.tipoZona);
    }

    @Override
    public String paraRelatorioDescritivo() {
        return super.paraRelatorioDescritivo() +
                String.format(Locale.US, "  - Tipo de Zona: %s\n\n", this.tipoZona);
    }
    
    @Override
    public void exibirDadosFinanciamento() {
        super.exibirDadosFinanciamento();
        System.out.printf("Tipo de Zona: %s\n", this.tipoZona);
        System.out.println("===============================");
    }
}