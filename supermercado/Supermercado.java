import java.util.Scanner;

// Classe que representa cada item da pilha (compra ou venda)
class Compra {
    String data;
    String produto;
    double valorCompra;
    double valorVenda;
    int quantidade;        // Quanto foi adicionado (+) ou removido (-)
    int quantidadeTotal;   // Estoque total APÓS essa operação
    Compra prox;

    public Compra(String data, String produto, double valorCompra, int quantidade, int quantidadeTotal, Compra prox) {
        this.data = data;
        this.produto = produto;
        this.valorCompra = valorCompra;
        this.valorVenda = Math.abs(valorCompra); // sempre positivo para exibição
        this.quantidade = quantidade;
        this.quantidadeTotal = quantidadeTotal;
        this.prox = prox;
    }
}

// Pilha dinâmica
class Pilha {
    private Compra topo;

    // Adiciona uma nova compra (ou venda) no topo da pilha
    public void push(String data, String produto, double valorCompra, int quantidade) {
        int quantidadeTotalAnterior = 0;
        if (topo != null && topo.produto.equals(produto)) {
            quantidadeTotalAnterior = topo.quantidadeTotal;
        }
        int novaQuantidadeTotal = quantidadeTotalAnterior + quantidade;

        topo = new Compra(data, produto, valorCompra, quantidade, novaQuantidadeTotal, topo);
    }

    // Remove o topo (não usado no projeto, mas incluso por completude)
    public Compra pop() {
        if (topo == null) return null;
        Compra removida = topo;
        topo = topo.prox;
        return removida;
    }

    // Retorna a última operação (compra ou venda)
    public Compra peek() {
        return topo;
    }

    // Exibe o histórico da pilha (do mais recente ao mais antigo)
    public void exibirHistorico() {
        Compra atual = topo;
        while (atual != null) {
            String tipo = atual.quantidade > 0 ? "Compra" : "Venda";
            System.out.println(
                "\n" + tipo + ":" +
                "\nData: " + atual.data +
                " | Produto: " + atual.produto +
                " | Preço de Compra: " + atual.valorCompra +
                " | Preço de Venda: " + atual.valorVenda +
                " | Quantidade: " + atual.quantidade +
                " | Quantidade Total: " + atual.quantidadeTotal
            );
            atual = atual.prox;
        }
    }
}
// Classe principal com o menu
public class Supermercado {
    static Pilha compras = new Pilha();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1- Registrar Compra");
            System.out.println("2- Registrar Venda");
            System.out.println("3- Histórico");
            System.out.println("4- Sair\n");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    registrarCompra();
                    break;
                case 2:
                    registrarVenda();
                    break;
                case 3:
                    compras.exibirHistorico();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("\nOpçao inválida!");
            }
        }
    }

    public static void registrarCompra() {
        System.out.print("\nData: ");
        String data = scanner.nextLine();
        System.out.print("Produto: ");
        String produto = scanner.nextLine();
        System.out.print("Preço de compra: ");
        double valorCompra = scanner.nextDouble();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        compras.push(data, produto, valorCompra, quantidade);
        System.out.println("\nCompra registrada!");
    }

    public static void registrarVenda() {
        if (compras.peek() == null) {
            System.out.println("\nNenhuma compra registrada!");
            return;
        }

        System.out.print("\nProduto vendido: ");
        String produto = scanner.nextLine();
        System.out.print("Quantidade vendida: ");
        int quantidadeVendida = scanner.nextInt();
        scanner.nextLine();

        Compra ultima = compras.peek();
        if (!ultima.produto.equals(produto)) {
            System.out.println("\nProduto nao encontrado!");
            return;
        }

        int estoqueAtual = ultima.quantidadeTotal;
        if (estoqueAtual < quantidadeVendida) {
            System.out.println("\nEstoque insuficiente! Estoque atual: " + estoqueAtual);
            return;
        }

        // Insere como venda (quantidade negativa)
        compras.push(ultima.data, produto, -ultima.valorVenda, -quantidadeVendida);
        System.out.println("\nVenda registrada!");
    }
}
