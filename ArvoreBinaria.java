public class ArvoreBinaria {
    private No raiz; //nó raiz

    public ArvoreBinaria() { //construtor que inicializa a raiz como nula
        raiz = null;
    }

    public No buscar(int valor) {
        No noatual = raiz; //comecamos pela raiz
        while (noatual.valor != valor) { //se o valor for diferente verifica
            if (noatual.valor < raiz.valor) { //se o valor do nó for menor que a raiz vai para a esquerda
                noatual = noatual.esquerda; //acessa o valor da esquerda
            } else noatual = noatual.direita; //acessa o valor da direita
            if (noatual == null) { //se o nó for null retorna null
                return null;
            }
        }
        return noatual; //retorna a raiz, já que é igual ao valor da busca
    }

    public void inserir(int valor) {
        No novoNo = new No(); //cria um objeto nó
        novoNo.valor = valor;  // coloca o valor ao novo nó

        if (raiz == null) {
            raiz = novoNo;  // se a árvore estiver vazia, o novo nó se torna a raiz
        } else {
            No noatual = raiz; //começa pela raiz
            No pai; //"cria" um nó pai

            while (true) { //loop para encaixar o novo nó no lugar certo
                pai = noatual;  //mantém referência ao nó pai

                if (valor < noatual.valor) { //se o valor for menor que o valor do nó atual, move para a esquerda
                    noatual = noatual.esquerda;

                    if (noatual == null) {  //se o filho da esquerda for null, insere o nó novo aqui
                        pai.esquerda = novoNo; //coloca a referencia do novo nó no pai
                        return; //encerra
                    }
                } else {
                    noatual = noatual.direita; //se o valor for maior, vai para a direita

                    if (noatual == null) {  //se o filho da direita estiver vazio (null), insere o nó aqui
                        pai.direita = novoNo; //coloca a referencia do novo nó no pai
                        return; //encerra
                    }
                }
            }
        }
    }

    public int remover(int valor) { //função de remover nós
        No noAtual = raiz; //vai começar a percorrer a árvore pela raiz
        No pai = raiz; //o nó pai é iniciado na raíz
        boolean eFilhoEsquerda = true; //bool que diz se o nó é filho da esquerda

        //loop que percorre a árvore em busca do nó a ser removido
        while (noAtual.valor != valor) { //enquanto o nó for diferente do valor solicitado
            pai = noAtual; //o pai é o nó atual
            if (valor < noAtual.valor) {  //se o valor inserido for menor que o valor do nó atual vai pra esquerda
                eFilhoEsquerda = true; //diz que é filho da esquerda
                noAtual = noAtual.esquerda; //o novo nó atual é o nó da esquerda do antigo nó
            } else {  //se o valor for maior que o valor do nó atual vai para a direita
                eFilhoEsquerda = false; //não é filho da esquerda
                noAtual = noAtual.direita; //o nó atual agora é o nó da direita do antigo nó
            }
            if (noAtual == null) {  // Se o nó não for encontrado
                return -1;  // valor não encontrado
            }
        }

        // caso 1: Nó não tem filhos (folha)
        if (noAtual.esquerda == null && noAtual.direita == null) { //se nao tem filhos (nenhum nó na direita ou esquerda)
            if (noAtual == raiz) {
                raiz = null; //se for raiz a raiz vira null
            } else if (eFilhoEsquerda) { //se for filho da esquerda
                pai.esquerda = null; //remove a referencia do filho no pai
            } else {
                pai.direita = null; //remove a referencia do filho no pai
            }
        }
        // caso 2: Nó tem um filho (direita)
        else if (noAtual.esquerda == null) { //se tiver filho na direita
            if (noAtual == raiz) {
                raiz = noAtual.direita; //se for raiz, a nova raiz vai ser o filho da direita
            } else if (eFilhoEsquerda) {
                pai.esquerda = noAtual.direita; //muda a referência do pai para o filho da direita do nó que está sendo removido
            } else {
                pai.direita = noAtual.direita; //muda a referência do pai para o filho da direita do nó que está sendo removido
            }
        }
        // caso 2.1: Nó tem um filho (esquerda)
        else if (noAtual.direita == null) { //se n tiver filho na direita mas tiver na esquerda
            if (noAtual == raiz) {
                raiz = noAtual.esquerda; //se for raiz, a nova raiz é o filho da esquerda do nó atual
            } else if (eFilhoEsquerda) { //se o nó a ser removido for filho da direita
                pai.esquerda = noAtual.esquerda; //muda a referência do pai para o filho da esquerda do nó que está sendo removido
            } else {
                pai.direita = noAtual.esquerda; // se o nó a ser removido for filho da direita  muda a ref do pai para o filho da esquerda
            }
        }
        // caso 3: nó tem dois filhos (pior caso, eca)
        else {
            No sucessor = getSucessor(noAtual); //função de achar sucessor

            if (noAtual == raiz) { //se o nó a ser removido é a raiz
                raiz = sucessor; //muda a raiz para o nó sucessor
            } else if (eFilhoEsquerda) { //se for filho da esquerda
                pai.esquerda = sucessor; //muda a referencia esquerda do pai para o sucessor
            } else { //se o nó a ser removido é filho da direita
                pai.direita = sucessor; //muda a referencia direita do pai para o nó sucessor
            }

            sucessor.esquerda = noAtual.esquerda; //muda a referencia da esquerda do novo nó (sucessor) para a ref da esquerda do nó removido
        }

        return noAtual.valor;  // Retorna o valor removido
    }

    private No getSucessor(No noRemovido) { //função que acha o nó sucessor (após a remoção) que recebe o nó a ser removido
        No sucessorPai = noRemovido; // O pai do sucessor é inicialmente o nó a ser removido
        No sucessor = noRemovido;    // O sucessor começa como o próprio nó a ser removido
        No atual = noRemovido.direita;  // Começa à direita do nó a ser removido

        while (atual != null) { //enquanto o nó não é nulo (vai percorrer a árvore em busca do menor nó)
            sucessorPai = sucessor; //atualiza o pai do sucessor
            sucessor = atual; //atualiza o sucessor para o nó atual
            atual = atual.esquerda; //vai para o próximo nó a esquerda
        }

        if (sucessor != noRemovido.direita) { //se o sucessor for diferente do filho direito do nó a ser removido
            //o filho esquerdo do pai do sucessor será o filho direito do sucessor
            sucessorPai.esquerda = sucessor.direita;
            //o sucessor herda o filho direito do nó removido
            sucessor.direita = noRemovido.direita;}

        return sucessor; //retorna o nó sucessor
    }
}