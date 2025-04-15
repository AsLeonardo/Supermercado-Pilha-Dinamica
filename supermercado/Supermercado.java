import java.util.Scanner;

class Compra {
    String data;
    String produto;
    double valorCompra;
    double valorVenda;
    int quantidade;
    int quantidadeTotal;
    Compra prox;

    public Compra(String data, String produto, double valorCompra, int quantidade, int quantidadeTotal , Compra prox) {
        this.data = data;
        this.produto = produto;
        this.valorCompra = valorCompra;
        this.valorVenda = valorCompra; // Sempre assume o último valor de compra
        this.quantidade = quantidade;
        this.quantidadeTotal = quantidadeTotal;
        this.prox = prox;
    }
}

class Pilha {
    private Compra topo;

    public void push(String data, String produto, double valorCompra, int quantidade) {
        topo = new Compra(data, produto, valorCompra, quantidade, topo);
    }

    public Compra pop() {
        if (topo == null) return null;
        Compra removida = topo;
        topo = topo.prox;
        return removida;
    }

    public Compra peek() {
        return topo;
    }

    public void exibirHistorico() {
        Compra atual = topo;
        while (atual != null) {
            System.out.println("\nData: " + atual.data + " | Produto: " + atual.produto + " | Preço de Compra: " + atual.valorCompra + " | Quantidade: " + atual.quantidade + " | Quantidade Total: " + atual.quantidadeTotal);
            atual = atual.prox;
        }
    }
}

public class Supermercado {
    static Pilha compras = new Pilha();
    static Pilha vendas = new Pilha();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1- Registrar Compra\n2- Registrar Venda\n3- Histórico de Compras\n4- Histórico de Vendas\n5- Sair\n");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

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
                    vendas.exibirHistorico();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("\nOpção inválida!\n");
            }
        }
    }

    public static void registrarCompra() {
        System.out.print("\nData: ");
        String data = scanner.nextLine();
        System.out.print("Produto: ");
        String produto = scanner.nextLine();
        System.out.print("Valor de compra: ");
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
        System.out.print("\nQuantidade vendida: ");
        int quantidadeVendida = scanner.nextInt();
        scanner.nextLine();

        Compra ultimaCompra = compras.peek();
        if (ultimaCompra != null && ultimaCompra.produto.equals(produto)) {
            if (ultimaCompra.quantidade >= quantidadeVendida) {
                ultimaCompra.quantidade -= quantidadeVendida;
                vendas.push(ultimaCompra.data, produto, ultimaCompra.valorVenda, quantidadeVendida);
                System.out.println("\nVenda registrada!");
            } else {
                System.out.println("\nEstoque insuficiente!");
            }
        } else {
            System.out.println("\nProduto não encontrado!");
        }
    }
}
