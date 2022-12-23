package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre o caminho do arquivo: ");
        String path = sc.nextLine();
        System.out.println();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            List<Sale> lstSale = new ArrayList<Sale>();

            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                lstSale.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3]),Double.parseDouble(fields[4])));
                line = br.readLine();

            }

            Set<String> UniqueNames = new HashSet<>();
            UniqueNames.addAll(lstSale.stream().map(x -> x.getSeller()).collect(Collectors.toList()));

            System.out.println("Total de vendas por vendedor: ");

            for (String name: UniqueNames) {
                Double sumTotal = lstSale.stream().filter(s -> s.getSeller().equals(name)).map(Sale::getTotal).reduce(0.0, Double::sum);
                System.out.println(name + " - R$ " + String.format("%.2f", sumTotal));
            }

        } catch (FileNotFoundException e){
            System.out.println("Erro: "+ path + " (O Sistema não pode encontrar o arquivo especificado)");
        } catch (IOException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
