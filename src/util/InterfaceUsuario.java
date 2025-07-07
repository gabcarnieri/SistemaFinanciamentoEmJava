package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfaceUsuario {
    private final Scanner scanner;

    public InterfaceUsuario() {
        this.scanner = new Scanner(System.in);
    }

    public double pedirValorImovel() {
        return pedirDoubleValidado(
                "Digite o valor do Imovel: ",
                "Erro: O valor do imovel deve ser entre R$50.001 e R$499.999!",
                50000,
                500000
        );
    }

    public int pedirPrazoFinanciamento() {
        return pedirIntValidado(
                "Digite o prazo em anos: ",
                "Erro: O prazo deve ser positivo e menor que 80 anos.",
                0,
                80
        );
    }

    public double pedirTaxaJuros() {
        return pedirDoubleValidado(
                "Digite a taxa de juros anual (%): ",
                "Erro: A taxa de juros deve ser maior que 9% e menor que 27%!",
                9,
                27
        );
    }

    private double pedirDoubleValidado(String prompt, String erro, double min, double max) {
        double valor = 0;
        boolean valido = false;
        do {
            try {
                System.out.print(prompt);
                valor = scanner.nextDouble();
                if (valor > min && valor < max) {
                    valido = true;
                } else {
                    System.out.println(erro);
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida! Digite apenas números.");
                scanner.nextLine();
            }
        } while (!valido);
        scanner.nextLine();
        return valor;
    }

    private int pedirIntValidado(String prompt, String erro, int min, int max) {
        int valor = 0;
        boolean valido = false;
        do {
            try {
                System.out.print(prompt);
                valor = scanner.nextInt();
                if (valor > min && valor < max) {
                    valido = true;
                } else {
                    System.out.println(erro);
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida! Digite apenas números inteiros.");
                scanner.nextLine();
            }
        } while (!valido);
        scanner.nextLine();
        return valor;
    }
}