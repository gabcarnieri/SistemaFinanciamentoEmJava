package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfaceUsuario {
    private final Scanner scanner;

    public InterfaceUsuario() {
        this.scanner = new Scanner(System.in);
    }

    public double pedirValorImovel() {
        double valor = 0;
        do {
            try {
                System.out.print("Digite o valor do Imovel: ");
                valor = scanner.nextDouble();
                scanner.nextLine();

                if (valor <= 50000 || valor >= 500000) {
                    System.out.println("Erro: O valor do imovel deve ser entre R$50.001 e R$499.999!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida! Digite apenas números.");
                scanner.nextLine();
                valor = 0;
            }
        } while (valor <= 50000 || valor >= 500000);

        return valor;
    }

    public int pedirPrazoFinanciamento() {
        int prazo = 0;
        do {
            try {
                System.out.print("Digite o prazo em anos: ");
                prazo = scanner.nextInt();
                scanner.nextLine();

                if (prazo <= 0 || prazo >= 80) {
                    System.out.println("Erro: O prazo deve ser positivo e menor que 80 anos.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida! Digite apenas números inteiros.");
                scanner.nextLine();
                prazo = 0;
            }
        } while (prazo <= 0 || prazo >= 80);

        return prazo;
    }

    public double pedirTaxaJuros() {
        double taxa = 0;
        do {
            try {
                System.out.print("Digite a taxa de juros anual (%): ");
                taxa = scanner.nextDouble();
                scanner.nextLine();

                if (taxa <= 9 || taxa >= 27) {
                    System.out.println("Erro: A taxa de juros deve ser maior que 9% e menor que 27%!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida! Digite apenas números.");
                scanner.nextLine();
                taxa = 0;
            }
        } while (taxa <= 9 || taxa >= 27);

        return taxa;
    }
}