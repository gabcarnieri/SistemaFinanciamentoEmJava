package util;
import java.util.Scanner;

public class InterfaceUsuario{

    public double pedirValorImovel(){
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite o valor do Imovel: ");
        double valorImovel = entrada.nextDouble();
        return 0;
    }

    public int pedirPrazoFinanciamento(){
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite o valor do Prazo em anos: ");
        double valorPrazo = entrada.nextDouble();
        return 0;
    }

    public static double pedirTaxaJuros(){
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite a taxa de Juros Anual: ");
        double valorJuros = entrada.nextDouble();
        return 0;
    }
}
