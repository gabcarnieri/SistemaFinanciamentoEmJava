package modelo;
import util.DadosImovelInvalidosException;
import java.util.Locale;

public class Apartamento extends Financiamento {
    private int vagasGaragem;
    private int numeroAndar;

    public Apartamento(double valorImovel, int prazoFinanciamento, double taxaJurosAnual, int vagasGaragem, int numeroAndar) throws DadosImovelInvalidosException {
        super(valorImovel, prazoFinanciamento, taxaJurosAnual);

        // Validação dos dados do apartamento
        if (vagasGaragem < 0) {
            throw new DadosImovelInvalidosException("O número de vagas na garagem não pode ser negativo.");
        }
        if (numeroAndar <= 0 || numeroAndar > 200) { // Ex: Definimos um limite realista de 200 andares
            throw new DadosImovelInvalidosException("O número do andar deve ser um valor positivo e realista (ex: entre 1 e 200).");
        }

        this.vagasGaragem = vagasGaragem;
        this.numeroAndar = numeroAndar;
    }

    @Override
    public double calcularPagamentoMensal() {
        double taxaMensal = (this.getTaxaJurosAnual() / 100) / 12;
        int meses = this.getPrazoFinanciamento() * 12;
        return this.getValorImovel() * (taxaMensal * Math.pow(1 + taxaMensal, meses)) / (Math.pow(1 + taxaMensal, meses) - 1);
    }

    @Override
    public String paraFormatoCSV() {
        // No caso de inteiros (%d), o Locale não é estritamente necessário, mas é uma boa prática manter a consistência.
        return super.paraFormatoCSV() + String.format(Locale.US, ",%d,%d", this.vagasGaragem, this.numeroAndar);
    }

    @Override
    public String paraRelatorioDescritivo() {
        return super.paraRelatorioDescritivo() +
                String.format(Locale.US, "  - Vagas na Garagem: %d\n", this.vagasGaragem) +
                String.format(Locale.US, "  - Andar: %d\n\n", this.numeroAndar);
    }

    @Override
    public void exibirDadosFinanciamento() {
        super.exibirDadosFinanciamento(); // Exibe dados comuns
        System.out.printf("Vagas na Garagem: %d\n", this.vagasGaragem);
        System.out.printf("Número do Andar: %d\n", this.numeroAndar);
        System.out.println("===============================");
    }
}