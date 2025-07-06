package main;

import modelo.Financiamento;
import modelo.Casa;
import modelo.Apartamento;
import modelo.Terreno;
import util.InterfaceUsuario;
import util.DadosImovelInvalidosException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Financiamento> financiamentos = new ArrayList<>();
        InterfaceUsuario interfaceUsuario = new InterfaceUsuario();
        Scanner scanner = new Scanner(System.in);

        int opcao = 0;
        boolean opcaoValida = false;

        do {
            System.out.println("Qual tipo de financiamento deseja cadastrar?");
            System.out.println("1. Casa");
            System.out.println("2. Apartamento");
            System.out.println("3. Terreno");
            System.out.print("Digite a opção: ");

            try {
                opcao = scanner.nextInt();

                if (opcao >= 1 && opcao <= 3) {
                    opcaoValida = true;
                } else {
                    System.out.println("\nErro: Opção inválida! Por favor, digite 1, 2 ou 3.\n");
                }

            } catch (InputMismatchException e) {
                System.out.println("\nErro: Entrada inválida! Por favor, digite apenas números (1, 2 ou 3).\n");
            } finally {
                scanner.nextLine();
            }

        } while (!opcaoValida);

        double valorImovel = interfaceUsuario.pedirValorImovel();
        int prazo = interfaceUsuario.pedirPrazoFinanciamento();
        double taxa = interfaceUsuario.pedirTaxaJuros();

        switch (opcao) {
            case 1:
                boolean dadosCasaValidos = false;
                do {
                    try {
                        System.out.print("Digite a área construída (m²): ");
                        double areaConstruida = scanner.nextDouble();
                        System.out.print("Digite a área do terreno (m²): ");
                        double areaTerreno = scanner.nextDouble();
                        scanner.nextLine();

                        // Agora o catch é para DadosImovelInvalidosException
                        financiamentos.add(new Casa(valorImovel, prazo, taxa, areaConstruida, areaTerreno));
                        dadosCasaValidos = true;

                    } catch (util.DadosImovelInvalidosException e) {
                        System.out.println("\nErro de validação: " + e.getMessage());
                        System.out.println("Por favor, insira os dados da casa novamente.\n");
                    } catch (InputMismatchException e) {
                        System.out.println("\nErro: Entrada inválida! Digite apenas números para as áreas.\n");
                        scanner.nextLine();
                    }
                } while (!dadosCasaValidos);
                break;

            case 2:
                boolean dadosAptoValidos = false;
                do {
                    try {
                        System.out.print("Digite o número de vagas na garagem: ");
                        int vagas = scanner.nextInt();
                        System.out.print("Digite o número do andar: ");
                        int andar = scanner.nextInt();
                        scanner.nextLine();

                        financiamentos.add(new Apartamento(valorImovel, prazo, taxa, vagas, andar));
                        dadosAptoValidos = true;

                    } catch (util.DadosImovelInvalidosException e) {
                        System.out.println("\nErro de validação: " + e.getMessage());
                        System.out.println("Por favor, insira os dados do apartamento novamente.\n");
                    } catch (InputMismatchException e) {
                        System.out.println("\nErro: Entrada inválida! Digite apenas números inteiros.\n");
                        scanner.nextLine(); // Limpa o buffer
                    }
                } while (!dadosAptoValidos);
                break;

            case 3:
                boolean dadosTerrenoValidos = false;
                do {
                    try {
                        System.out.print("Digite o tipo de zona (Residencial/Comercial): ");
                        String zona = scanner.nextLine();

                        financiamentos.add(new Terreno(valorImovel, prazo, taxa, zona));
                        dadosTerrenoValidos = true;

                    } catch (util.DadosImovelInvalidosException e) {
                        System.out.println("\nErro de validação: " + e.getMessage());
                        System.out.println("Por favor, insira os dados do terreno novamente.\n");
                    }
                } while (!dadosTerrenoValidos);
                break;
        }
        System.out.println("Financiamento do usuário adicionado à análise.\n");

        try {
            financiamentos.add(new Casa(320000, 25, 12, 150, 200));
        } catch (DadosImovelInvalidosException e) {
            System.out.println("Erro ao criar casa: " + e.getMessage());
        }
        try {
            financiamentos.add(new Apartamento(450000, 30, 11.2, 2, 8));
        } catch (DadosImovelInvalidosException e) {
            System.out.println("Erro ao criar apartamento: " + e.getMessage());
        }
        try {
            financiamentos.add(new Apartamento(550000, 20, 11.2, 3, 15));
        } catch (DadosImovelInvalidosException e) {
            System.out.println("Erro ao criar apartamento: " + e.getMessage());
        }
        try {
            financiamentos.add(new Terreno(120000, 10, 13, "Comercial"));
        } catch (DadosImovelInvalidosException e) {
            System.out.println("Erro ao criar terreno: " + e.getMessage());
        }

        double totalImoveis = 0;
        double totalFinanciamentos = 0;

        for (Financiamento f : financiamentos) {
            f.exibirDadosFinanciamento();
            totalImoveis += f.getValorImovel();
            try {
                totalFinanciamentos += f.calcularTotalPagamento();
            } catch (Exception e) {
                System.out.println("Erro ao calcular total do financiamento: " + e.getMessage());
            }
        }

        System.out.println("---------- TOTAIS GERAIS ----------");
        System.out.printf("Total de todos os imóveis: R$ %.2f\n", totalImoveis);
        System.out.printf("Total de todos os financiamentos: R$ %.2f\n", totalFinanciamentos);
        System.out.println("------------------------------------");

        String arquivoDeDados = "financiamentos_dados.csv";
        String arquivoDeRelatorio = "financiamentos_relatorio.txt";

        // 1. Grava o relatório descritivo para leitura humana
        util.ManipuladorDeArquivos.gravarRelatorioDescritivo(arquivoDeRelatorio, financiamentos);
        System.out.println("\nRelatório descritivo salvo com sucesso em '" + arquivoDeRelatorio + "'.");

        // 2. Grava o arquivo de dados para leitura do programa
        util.ManipuladorDeArquivos.gravarFinanciamentosCSV(arquivoDeDados, financiamentos);
        System.out.println("Arquivo de dados salvo com sucesso em '" + arquivoDeDados + "'.");

        // 3. Lê o arquivo de DADOS para comprovar
        List<Financiamento> financiamentosLidos = util.ManipuladorDeArquivos.lerFinanciamentosCSV(arquivoDeDados);

        System.out.println("\n--- Comprovação da Leitura do Arquivo de Dados ---");
        if (financiamentosLidos.isEmpty()) {
            System.out.println("Nenhum dado foi lido do arquivo de dados ou ocorreu um erro.");
        } else {
            System.out.println(financiamentosLidos.size() + " financiamentos lidos com sucesso.");
        }

        scanner.close();
    }
}
