package modelo;

public class Financiamento {
    // Atributos privados
    private final double valorImovel;
    private final int prazoFinanciamento;
    private final double taxaJurosAnual;

    // Construtor
    public Financiamento(double valorImovel, int prazoFinanciamento, double taxaJurosAnual) {
        this.valorImovel = valorImovel;
        this.prazoFinanciamento = prazoFinanciamento;
        this.taxaJurosAnual = taxaJurosAnual;
    }

    // Getters
    public double getValorImovel() {
        return valorImovel;
    }

    public int getPrazoFinanciamento() {
        return prazoFinanciamento;
    }

    public double getTaxaJurosAnual() {
        return taxaJurosAnual;
    }

    // Cálculo da prestação mensal (fórmula correta)
    public double calcularPagamentoMensal() {
        double taxaMensal = taxaJurosAnual / 100 / 12;
        int numeroPagamentos = prazoFinanciamento * 12;
        return valorImovel * (taxaMensal * Math.pow(1 + taxaMensal, numeroPagamentos))
                / (Math.pow(1 + taxaMensal, numeroPagamentos) - 1);
    }

    // Cálculo do total a ser pago
    public double calcularTotalPagamento() {
        return calcularPagamentoMensal() * prazoFinanciamento * 12;
    }

    // Exibição dos dados do financiamento
    public void exibirDadosFinanciamento() {
        System.out.println("\n=== RESUMO DO FINANCIAMENTO ===");
        System.out.printf("Valor do imóvel: R$ %.2f%n", valorImovel);
        System.out.printf("Valor total do financiamento: R$ %.2f%n", calcularTotalPagamento());
        System.out.printf("Pagamento mensal: R$ %.2f%n", calcularPagamentoMensal());
        System.out.println("===============================");
    }
}