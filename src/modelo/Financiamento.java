package modelo;

public class Financiamento{
    //atributos
    private double valorImovel;
    private int prazoFinanciamento;
    private double taxaJurosAnual;

    public double getValorImovel() {
        return valorImovel;
    }
    public int getPrazoFinanciamento() {
        return prazoFinanciamento;
    }
    public double getTaxaJurosAnual() {
        return taxaJurosAnual;
    }

    public Financiamento(double valorDesejadoImovel, int prazoFinanciamentoAnos, double taxaJurosAnual){
        this.valorImovel = valorDesejadoImovel;
        this.prazoFinanciamento = prazoFinanciamentoAnos;
        this.taxaJurosAnual = taxaJurosAnual;
}

    double calcularPagamentoMensal(){
        return (this.valorImovel / (this.prazoFinanciamento * 12)) * (1 + (this.taxaJurosAnual / 12));
    }

    double calcularTotalPagamento(){
        return calcularPagamentoMensal() * this.prazoFinanciamento * 12;
    }

}
