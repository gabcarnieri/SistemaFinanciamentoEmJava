package util;

import java.util.Scanner;

public class InterfaceUsuario {
    private final Scanner scanner;

    public InterfaceUsuario() {
        this.scanner = new Scanner(System.in);
    }

    public double pedirValorImovel() {
        double valor;
        do {
            System.out.print("Digite o valor do Imovel: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Erro: Digite apenas números!");
                scanner.next(); // Limpa entrada inválida
                System.out.print("Digite o valor do Imovel: ");
            }
            valor = scanner.nextDouble();
            scanner.nextLine(); // Limpa o buffer

            if (valor <= 0) {
                System.out.println("Erro: O valor deve ser positivo!");
            }
        } while (valor <= 0);

        return valor;
    }

    public int pedirPrazoFinanciamento() {
        int prazo;
        do {
            System.out.print("Digite o prazo em anos: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Erro: Digite apenas números inteiros!");
                scanner.next(); // Limpa entrada inválida
                System.out.print("Digite o prazo em anos: ");
            }
            prazo = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            if (prazo <= 0) {
                System.out.println("Erro: O prazo deve ser positivo!");
            }
        } while (prazo <= 0);

        return prazo;
    }

    public double pedirTaxaJuros() {
        double taxa;
        do {
            System.out.print("Digite a taxa de juros anual (%): ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Erro: Digite apenas números!");
                scanner.next(); // Limpa entrada inválida
                System.out.print("Digite a taxa de juros anual (%): ");
            }
            taxa = scanner.nextDouble();
            scanner.nextLine(); // Limpa o buffer

            if (taxa <= 0) {
                System.out.println("Erro: A taxa deve ser positiva!");
            }
        } while (taxa <= 0);

        return taxa;
    }
}