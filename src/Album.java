import java.io.*; //Utilização das classes de leitura de arquivos
import java.util.Scanner;

public class Album {
    static int linhas; //declarando variáveis globais 
    static int colunas;
    static String[] selecoes;
    static int[][] matriz;
    static String[] figurinhasFaltantes;
    static String[] figurinhasRepetidas;

    //=======================
    // Metodo de leitura do arquivo
    //=======================
    //dasda
    static void cadastrarFigurinha(int selecao, int jogador) {
        if (matriz[selecao][jogador] == 0){
            matriz[selecao][jogador] = 1;
            System.out.println("Figurinha nova colada!");
        }else {
            matriz[selecao][jogador] ++;
            System.out.println("Figurinha repitida registrada");
        }
    }
    static void carregarAlbum(String caminhoArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {

            int indiceFigurinhasFaltantes=0;
            int indiceFigurinhasRepetidas=0;


            // 1ª linha: dimensões
            //Lê uma linha do arquivo com o metodo da classe bufferedReader br e divide strings diferentes utilizando o split
            String[] dimensoes = br.readLine().trim().split("\\s+");
            linhas  = Integer.parseInt(dimensoes[0]); //converte a string para inteiro
            colunas = Integer.parseInt(dimensoes[1]); //converte a string para inteiro

            selecoes = new String[linhas];

            matriz   = new int[linhas][colunas];

            // próximas M linhas: nomes das seleções
            for (int i = 0; i < linhas; i++) {
                selecoes[i] = br.readLine().trim(); //Lê os nomes de cada seleção.
                //o metodo trim serve para remover espaços em branco antes e depois da string lida
            }

            // próximas M linhas: valores da matriz que representam as figurinhas
            for (int i = 0; i < linhas; i++) {
                String[] valores = br.readLine().trim().split("\\s+");
                //Leitura similar à linha 14
                for (int j = 0; j < colunas; j++) {
                    matriz[i][j] = Integer.parseInt(valores[j]);//Converte valores para inteiro
                    if(matriz[i][j]==0){
                        indiceFigurinhasFaltantes++;
                    }else if(matriz[i][j]>1){
                        indiceFigurinhasRepetidas++;
                    }
                }
            }
            figurinhasFaltantes = new String[indiceFigurinhasFaltantes];
            figurinhasRepetidas = new String[indiceFigurinhasRepetidas];

            System.out.println("Álbum carregado com sucesso!");
            System.out.printf("Seleções: %d | Jogadores por seleção: %d%n", linhas, colunas);
        }
    }

    //=======================
    // Metodo para imprimir o álbum atual
    //=======================
    static void exibirMatriz() {
        if (matriz == null) {
            System.out.println("Nenhum álbum carregado.");
            return;
        }
        // cabeçalho com números dos jogadores
        System.out.println("ALBUM ATUAL");
        System.out.printf("%-15s", "Seleção");
        //Imprime a palavra e reserva 15 espaços para impressão, alinhando à esquerda
        for (int j = 1; j <= colunas; j++) {
            System.out.printf(" J%-3d", j);
        }
        System.out.println();

        // linha separadora
        //Imprime 15 hifens e depois mais 5 hifens por coluna da matriz
        System.out.println("-".repeat(15 + colunas * 5));

        // linhas da matriz
        for (int i = 0; i < linhas; i++) {
            System.out.printf("%-15s", selecoes[i]);
            for (int j = 0; j < colunas; j++) {
                System.out.printf(" %-4d", matriz[i][j]);
            }
            System.out.println();
        }
    }


    //Ainda existe erro porque não altero o arquivo
    static void listarFigurinhasFaltantes(){

        int contFigurinhas = 0;
        //Percorrer as figurinhas porém com o intuito de capturar dados de figurinhas faltantes
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if(matriz[i][j]==0){

                    figurinhasFaltantes[contFigurinhas] = selecoes[i]+" - "+" Jogador "+ (j+1);
                    contFigurinhas++;
                }
            }
        }

        for (int i =0;i<figurinhasFaltantes.length;i++){
            System.out.println(figurinhasFaltantes[i]);
        }
    }

    static void listarFigurinhasRepetidas(){
        int contFigurinhasRepetidas = 0;
        //Percorrer as figurinhas porém com o intuito de capturar dados de figurinhas faltantes
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if(matriz[i][j]>1){
                    int quantidadeRepetidas = matriz[i][j]-1;
                    figurinhasRepetidas[contFigurinhasRepetidas] = selecoes[i]+" - "+ quantidadeRepetidas+" repetições do jogador "+(j+1) ;
                    contFigurinhasRepetidas++;
                }
            }
        }

        for (int i =0;i<figurinhasRepetidas.length;i++){
            System.out.println(figurinhasRepetidas[i]);
        }
    }

    //=======================
    // Metodo main
    //=======================
    public static void main(String[] args) {

            // Menu principal

            int codigo;
            Scanner in = new Scanner(System.in);
            System.out.println("1. Carregar álbum a partir de arquivo (fornecerei o método de leitura de arquivos) ");
            System.out.println("2. Registrar nova figurinha");
            System.out.println("3. Listar figurinhas faltantes");
            System.out.println("4. Listar figurinhas repetidas");
            System.out.println("5. Comparar dois álbuns (verificar trocas possíveis)");
            System.out.println("6. Sair");

            codigo = in.nextInt();
            in.nextLine();

            //Validação de erro para o código

            while(codigo<1 || codigo>6){
                System.out.println("Digite um código válido: ");
                System.out.println("1. Carregar álbum a partir de arquivo (fornecerei o método de leitura de arquivos) ");
                System.out.println("2. Registrar nova figurinha");
                System.out.println("3. Listar figurinhas faltantes");
                System.out.println("4. Listar figurinhas repetidas");
                System.out.println("5. Comparar dois álbuns (verificar trocas possíveis)");
                System.out.println("6. Sair");
                codigo = in.nextInt();
                in.nextLine();
            }

            //Loop de execuções que pode chamar todos os métodos

            while (codigo!= 6){
                if (codigo ==1){
                    String caminho;
                    System.out.println("Informe o caminho que está o arquivo em seu computador: ");
                    caminho = in.nextLine();

                    try{
                        carregarAlbum(caminho);
                        exibirMatriz();
                    } catch (FileNotFoundException e) {
                    System.out.println("Arquivo não encontrado: " + e.getMessage());
                    } catch (NumberFormatException e) {
                    System.out.println("Formato inválido no arquivo: " + e.getMessage());
                    } catch (IOException e) {
                    System.out.println("Erro de leitura: " + e.getMessage());
                    }

                    // caminho no meu pc == "Z:\\AlbumTxt\\album.txt"
                    // caminho no meu notebook = "C:\Users\ferna\Downloads\album.txt"

                } else if (codigo ==2) {
                    if (selecoes==null){
                        System.out.println("O albúm não foi carregado. Carregue o álbum primeiro para depois registrar a figurinha.");
                    }else{
                        int selecao,jogador;
                        System.out.println("Informe o índice da seleção: ");
                        selecao = in.nextInt();
                        in.nextLine();
                        System.out.println("Informe o índice da jogador: ");
                        jogador = in.nextInt();
                        in.nextLine();
                        while((selecao>selecoes.length|| selecao<0)||(jogador>colunas||jogador<0)){
                            System.out.println("Índices inválidos!");
                            System.out.println("Informe o índice da seleção: ");
                            selecao = in.nextInt();
                            in.nextLine();
                            System.out.println("Informe o índice da jogador: ");
                            jogador = in.nextInt();
                            in.nextLine();
                        }

                        cadastrarFigurinha(selecao,jogador);
                        exibirMatriz();
                    }


                } else if (codigo==3) {
                    if (selecoes==null){
                        System.out.println("O albúm não foi carregado. Carregue o álbum primeiro para depois listar as figurinhas faltantes.");
                    }else{
                        listarFigurinhasFaltantes();
                    }
                } else if (codigo ==4) {
                    if (selecoes==null){
                        System.out.println("O albúm não foi carregado. Carregue o álbum primeiro para depois listar as figurinhas repetidas.");
                    }else{
                        listarFigurinhasRepetidas();
                    }
                } else if (codigo == 5){

                }else{
                    in.close();
                }
                System.out.println("\n");
                System.out.println("1. Carregar álbum a partir de arquivo (fornecerei o método de leitura de arquivos) ");
                System.out.println("2. Registrar nova figurinha");
                System.out.println("3. Listar figurinhas faltantes");
                System.out.println("4. Listar figurinhas repetidas");
                System.out.println("5. Comparar dois álbuns (verificar trocas possíveis)");
                System.out.println("6. Sair");

                codigo = in.nextInt();
                in.nextLine();
            }




    }
}