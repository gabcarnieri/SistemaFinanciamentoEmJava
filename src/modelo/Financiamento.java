package modelo;
import java.util.Locale;

public abstract class Financiamento {
    protected double valorImovel;
    protected int prazoFinanciamento;
    protected double taxaJurosAnual;

    public Financiamento(double valorImovel, int prazoFinanciamento, double taxaJurosAnual) {
        this.valorImovel = valorImovel;
        this.prazoFinanciamento = prazoFinanciamento;
        this.taxaJurosAnual = taxaJurosAnual;
    }

    public double getValorImovel() {
        return valorImovel;
    }

    public int getPrazoFinanciamento() {
        return prazoFinanciamento;
    }

    public double getTaxaJurosAnual() {
        return taxaJurosAnual;
    }

    public abstract double calcularPagamentoMensal();

    public double calcularTotalPagamento() {
        return this.calcularPagamentoMensal() * this.getPrazoFinanciamento() * 12;
    }

    public String paraFormatoCSV() {
        // Adicionamos Locale.US como o primeiro argumento do String.format
        return String.format(Locale.US, "%s,%.2f,%.2f,%.2f,%d",
                this.getClass().getSimpleName(),
                this.getValorImovel(),
                this.calcularTotalPagamento(),
                this.getTaxaJurosAnual(),
                this.getPrazoFinanciamento()
        );
    }

    public String paraRelatorioDescritivo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tipo de Financiamento: ").append(this.getClass().getSimpleName()).append("\n");
        sb.append(String.format(Locale.US, "  - Valor do Imóvel: R$ %.2f\n", this.getValorImovel()));
        sb.append(String.format(Locale.US, "  - Valor do Financiamento: R$ %.2f\n", this.calcularTotalPagamento()));
        sb.append(String.format(Locale.US, "  - Taxa de Juros Anual: %.2f%%\n", this.getTaxaJurosAnual()));
        sb.append(String.format(Locale.US, "  - Prazo do Financiamento (anos): %d\n", this.getPrazoFinanciamento()));
        return sb.toString();
    }

    public void exibirDadosFinanciamento() {
        System.out.println("\n=== RESUMO DO FINANCIAMENTO ===");
        System.out.printf("Tipo: %s\n", this.getClass().getSimpleName());
        System.out.printf("Valor do imóvel: R$ %.2f\n", this.getValorImovel());
        System.out.printf("Valor total do financiamento: R$ %.2f\n", this.calcularTotalPagamento());
        System.out.printf("Pagamento mensal: R$ %.2f\n", this.calcularPagamentoMensal());
    }
}