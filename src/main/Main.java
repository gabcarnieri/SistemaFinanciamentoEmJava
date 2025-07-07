package main;

import modelo.Financiamento;
import util.InterfaceUsuario;
import util.DadosImovelInvalidosException;
import util.AumentoMaiorDoQueJurosException; // Exceção que já tínhamos adicionado
import util.ManipuladorDeArquivos;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // A lista é inicializada vazia e será preenchida via menu
        List<Financiamento> financiamentos = new ArrayList<>();
        InterfaceUsuario interfaceUsuario = new InterfaceUsuario();
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;

        // Loop principal do menu, continua até o usuário escolher sair
        while (!sair) {
            exibirMenu();
            int escolha = 0;

            try {
                escolha = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("\nErro: Por favor, digite um número válido para a opção.");
                scanner.nextLine();
                continue;
            }

            switch (escolha) {
                case 1:
                    cadastrarNovoFinanciamento(scanner, interfaceUsuario, financiamentos);
                    break;

                case 2:
                    verFinanciamentosSalvos(financiamentos);
                    break;

                case 3:
                    System.out.println("\nSalvando dados...");
                    if (!financiamentos.isEmpty()) {
                        salvarDados(financiamentos);
                        System.out.println("Dados salvos com sucesso!");
                    } else {
                        System.out.println("Nenhum financiamento para salvar.");
                    }
                    System.out.println("Encerrando o programa. Até logo!");
                    sair = true;
                    break;

                default:
                    System.out.println("\nOpção inválida! Por favor, escolha uma opção de 1 a 3.");
                    break;
            }
        }

        scanner.close();
    }
    //Exibe o menu principal de opções para o usuário.
    private static void exibirMenu() {
        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println("1. Cadastrar Novo Financiamento");
        System.out.println("2. Ver Financiamentos Salvos");
        System.out.println("3. Sair e Salvar Dados");
        System.out.print("Escolha uma opção: ");
    }

    //Cuida do fluxo de cadastro de um novo financiamento.

    private static void cadastrarNovoFinanciamento(Scanner scanner, InterfaceUsuario ui, List<Financiamento> lista) {
        System.out.println("\n--- Cadastro de Novo Financiamento ---");
        System.out.println("Qual tipo de financiamento deseja cadastrar?");
        System.out.println("1. Casa");
        System.out.println("2. Apartamento");
        System.out.println("3. Terreno");
        System.out.print("Digite a opção: ");

        int tipo = scanner.nextInt();
        scanner.nextLine();

        double valorImovel = ui.pedirValorImovel();
        int prazo = ui.pedirPrazoFinanciamento();
        double taxa = ui.pedirTaxaJuros();

        Financiamento novoFinanciamento = criarFinanciamento(scanner, tipo, valorImovel, prazo, taxa);

        if (novoFinanciamento != null) {
            lista.add(novoFinanciamento);
            System.out.println("\nFinanciamento cadastrado com sucesso!");
        } else {
            System.out.println("\nO cadastro do financiamento foi cancelado devido a um erro de validação.");
        }
    }
    //Exibe a lista de financiamentos já cadastrados e os totais.
    private static void verFinanciamentosSalvos(List<Financiamento> financiamentos) {
        if (financiamentos.isEmpty()) {
            System.out.println("\nNenhum financiamento cadastrado ainda.");
            return;
        }

        System.out.println("\n---------- LISTA DE FINANCIAMENTOS CADASTRADOS ----------");
        double totalImoveis = 0;
        double totalFinanciamentos = 0;

        for (Financiamento f : financiamentos) {
            f.exibirDadosFinanciamento();
            totalImoveis += f.getValorImovel();
            totalFinanciamentos += f.calcularTotalPagamento();
        }
        System.out.println("----------------------------------------------------------\n");
        System.out.println("---------- TOTAIS GERAIS ----------");
        System.out.printf("Total de todos os imóveis: R$ %.2f\n", totalImoveis);
        System.out.printf("Total de todos os financiamentos: R$ %.2f\n", totalFinanciamentos);
        System.out.println("------------------------------------");
    }

    //Chama os métodos para salvar os dados em arquivos de texto e serializados.
    private static void salvarDados(List<Financiamento> financiamentos) {
        String arquivoDeDados = "financiamentos_dados.csv";
        String arquivoDeRelatorio = "financiamentos_relatorio.txt";
        String arquivoSerializado = "financiamentos.dat";

        ManipuladorDeArquivos.gravarRelatorioDescritivo(arquivoDeRelatorio, financiamentos);
        ManipuladorDeArquivos.gravarFinanciamentosCSV(arquivoDeDados, financiamentos);
        ManipuladorDeArquivos.serializarFinanciamentos(arquivoSerializado, financiamentos);
    }

    private static Financiamento criarFinanciamento(Scanner scanner, int tipo, double valorImovel, int prazo, double taxa) {
        Financiamento financiamento = null;
        boolean dadosValidos = false;

        do {
            try {
                switch (tipo) {
                    case 1: // Casa
                        System.out.print("Digite a área construída (m²): ");
                        double areaConstruida = scanner.nextDouble();
                        System.out.print("Digite a área do terreno (m²): ");
                        double areaTerreno = scanner.nextDouble();
                        scanner.nextLine();
                        financiamento = new modelo.Casa(valorImovel, prazo, taxa, areaConstruida, areaTerreno);
                        ((modelo.Casa) financiamento).verificarAumentoFixo();
                        break;
                    case 2: // Apartamento
                        System.out.print("Digite o número de vagas na garagem: ");
                        int vagas = scanner.nextInt();
                        System.out.print("Digite o número do andar: ");
                        int andar = scanner.nextInt();
                        scanner.nextLine();
                        financiamento = new modelo.Apartamento(valorImovel, prazo, taxa, vagas, andar);
                        break;
                    case 3: // Terreno
                        System.out.print("Digite o tipo de zona (Residencial/Comercial): ");
                        String zona = scanner.nextLine();
                        financiamento = new modelo.Terreno(valorImovel, prazo, taxa, zona);
                        break;
                    default:
                        System.out.println("Tipo de imóvel inválido.");
                        break;
                }
                dadosValidos = true;
            } catch (DadosImovelInvalidosException e) {
                System.out.println("\nErro de validação: " + e.getMessage());
                System.out.println("Por favor, insira os dados específicos do imóvel novamente.\n");
            } catch (AumentoMaiorDoQueJurosException e) {
                System.out.println("\n-------------------------------------------------------------");
                System.out.println("ATENÇÃO: Erro na regra de negócio do financiamento da Casa.");
                System.out.println("Motivo: " + e.getMessage());
                System.out.println("Este erro ocorre porque a taxa fixa de R$80,00 tem um impacto percentual muito alto para as condições informadas.");
                System.out.println("Por favor, tente novamente com um valor de imóvel maior, um prazo menor ou uma taxa de juros diferente.");
                System.out.println("-------------------------------------------------------------\n");
                return null;
            } catch (InputMismatchException e) {
                System.out.println("\nErro: Entrada inválida! Digite o tipo de dado correto.\n");
                scanner.nextLine();
            }
        } while (!dadosValidos);
        return financiamento;
    }
}