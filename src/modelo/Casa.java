package modelo;
import util.AumentoMaiorDoQueJurosException;
import util.DadosImovelInvalidosException;
import java.util.Locale;

public class Casa extends Financiamento {
    private double areaConstruida;
    private double areaTerreno;

    public Casa(double valorImovel, int prazoFinanciamento, double taxaJurosAnual, double areaConstruida, double areaTerreno) throws DadosImovelInvalidosException {
        super(valorImovel, prazoFinanciamento, taxaJurosAnual);

        if (areaConstruida > areaTerreno) {
            throw new DadosImovelInvalidosException("Erro: A área construída não pode ser maior que a área do terreno.");
        }
        if (areaTerreno <= 0 || areaConstruida <= 0) {
            throw new DadosImovelInvalidosException("As áreas devem ser valores positivos.");
        }
        if (areaTerreno > 5000) {
            throw new DadosImovelInvalidosException("Área do terreno muito grande para este tipo de financiamento (limite: 5000m²).");
        }

        this.areaConstruida = areaConstruida;
        this.areaTerreno = areaTerreno;
    }

    public void verificarAumentoFixo() throws AumentoMaiorDoQueJurosException {
        double parcelaBase = (this.getValorImovel() / (this.getPrazoFinanciamento() * 12)) * (1 + (this.getTaxaJurosAnual() / 12));
        double percentualAumento = (80 / parcelaBase) * 100;

        if (percentualAumento > this.getTaxaJurosAnual()) {
            throw new AumentoMaiorDoQueJurosException(
                    String.format("O aumento fixo de R$ %.2f representa %.2f%% da parcela, " +
                                    "que é maior que a taxa de juros de %.2f%%",
                            80.0, percentualAumento, this.getTaxaJurosAnual())
            );
        }
    }

    @Override
    public double calcularPagamentoMensal() {
        double taxaMensal = (this.getTaxaJurosAnual() / 100) / 12;
        int meses = this.getPrazoFinanciamento() * 12;
        double parcela = this.getValorImovel() * (taxaMensal * Math.pow(1 + taxaMensal, meses)) / (Math.pow(1 + taxaMensal, meses) - 1);
        return parcela + 80;
    }

    @Override
    public String paraFormatoCSV() {
        return super.paraFormatoCSV() + String.format(Locale.US, ",%.2f,%.2f", this.areaConstruida, this.areaTerreno);
    }

    @Override
    public String paraRelatorioDescritivo() {
        return super.paraRelatorioDescritivo() +
                String.format(Locale.US, "  - Área Construída: %.2f m²\n", this.areaConstruida) +
                String.format(Locale.US, "  - Área do Terreno: %.2f m²\n\n", this.areaTerreno);
    }
}