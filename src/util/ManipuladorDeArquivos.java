package util;

import modelo.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ManipuladorDeArquivos {

    //Grava o relatório descritivo em um arquivo .txt.
    public static void gravarRelatorioDescritivo(String nomeArquivo, List<Financiamento> financiamentos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println("========== RELATÓRIO DE FINANCIAMENTOS ==========\n");
            double totalImoveis = 0;
            double totalFinanciamentos = 0;

            for (Financiamento f : financiamentos) {
                writer.print(f.paraRelatorioDescritivo());
                totalImoveis += f.getValorImovel();
                totalFinanciamentos += f.calcularTotalPagamento();
            }

            writer.println("================================================");
            writer.println("\n========== TOTAIS GERAIS ==========");
            writer.println(String.format(Locale.US, "Valor total de todos os imóveis: R$ %.2f", totalImoveis));
            writer.println(String.format(Locale.US, "Valor total de todos os financiamentos: R$ %.2f", totalFinanciamentos));
            writer.println("===================================");

        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao gravar o arquivo de relatório: " + e.getMessage());
        }
    }

    //Grava os dados dos financiamentos em formato CSV (para leitura do programa).
    public static void gravarFinanciamentosCSV(String nomeArquivo, List<Financiamento> financiamentos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (Financiamento f : financiamentos) {
                writer.println(f.paraFormatoCSV());
            }
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao gravar o arquivo CSV: " + e.getMessage());
        }
    }

    //Lê os dados de um arquivo CSV e retorna uma lista de objetos de Financiamento.
    public static List<Financiamento> lerFinanciamentosCSV(String nomeArquivo) {
        List<Financiamento> financiamentosLidos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");

                String tipo = dados[0];
                double valorImovel = Double.parseDouble(dados[1]);
                double taxaJuros = Double.parseDouble(dados[3]);
                int prazo = Integer.parseInt(dados[4]);

                Financiamento financiamento = null;
                switch (tipo) {
                    case "Casa":
                        double areaConstruida = Double.parseDouble(dados[5]);
                        double areaTerreno = Double.parseDouble(dados[6]);
                        financiamento = new Casa(valorImovel, prazo, taxaJuros, areaConstruida, areaTerreno);
                        break;
                    case "Apartamento":
                        int vagas = Integer.parseInt(dados[5]);
                        int andar = Integer.parseInt(dados[6]);
                        financiamento = new Apartamento(valorImovel, prazo, taxaJuros, vagas, andar);
                        break;
                    case "Terreno":
                        String zona = dados[5];
                        financiamento = new Terreno(valorImovel, prazo, taxaJuros, zona);
                        break;
                }

                if (financiamento != null) {
                    financiamentosLidos.add(financiamento);
                }
            }
        } catch (IOException | DadosImovelInvalidosException e) {
            System.err.println("Ocorreu um erro ao ler o arquivo CSV: " + e.getMessage());
        }
        return financiamentosLidos;
    }

    public static void serializarFinanciamentos(String nomeArquivo, List<Financiamento> financiamentos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(financiamentos);
        } catch (IOException e) {
            System.err.println("Erro ao serializar os dados: " + e.getMessage());
        }
    }

    // Metodo para ler a lista de objetos (deserializar)
    public static List<Financiamento> deserializarFinanciamentos(String nomeArquivo) {
        List<Financiamento> financiamentosLidos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            financiamentosLidos = (List<Financiamento>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao deserializar os dados: " + e.getMessage());
        }
        return financiamentosLidos;
    }
}