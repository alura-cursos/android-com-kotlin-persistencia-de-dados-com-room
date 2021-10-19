![Thumbnail](https://user-images.githubusercontent.com/8989346/132551158-1dcbc5a8-f3e7-4022-80e0-30f16935f7a8.png)
  
# Orgs

App de simulação um e-commerce de produtos naturais

## 🔨 Funcionalidades do projeto

O projeto permite cadastrar, alterar e remover produtos com imagem, nome, descrição e valor. Os produtos salvos são apresentados em uma lista e podem ser visualizado ao realizar o clique.

- Inserindo

![inserindo produto](https://user-images.githubusercontent.com/8989346/133252744-e6201160-1d51-47c2-8192-d602be1bfd80.gif)


- Alterando

![alterando produto](https://user-images.githubusercontent.com/8989346/133252749-eecdb640-1a11-422b-99e2-57347b765918.gif)

- Removendo

![removendo produto](https://user-images.githubusercontent.com/8989346/133252742-90509b74-e6df-4a47-bc2f-0208a0977d92.gif)

## 🎯 Desafio

Ordenação de produtos:

![demonstração do desafio de ordenação de produtos](https://user-images.githubusercontent.com/8989346/137913949-2bed58cc-c9c5-4444-a4a3-6e1fcf94938d.gif)

[Você pode conferir o resultado do desafio neste commit](https://github.com/alura-cursos/android-com-kotlin-persistencia-de-dados-com-room/commit/58fa63e2bb004d9144a0727a2dadd9265f0182ba).

## ✔️ Técnicas e tecnologias utilizadas

- `Jetpack Room`: lib para persistência de dados em banco de dados interno com SQLite
- `Entidade`: definição da tabela que será criada no banco de dados
- `DAO`: definição dos comportamentos com o banco de dados
  - **comportamentos definidos**: inserção, alteração, remoção e consultas de todos os registros e com filtro
- `Database`: configuração para criar a conexão com o banco de dados
- `conversor de tipo`: converter um tipo complexo para um tipo compatível com o SQLite
- `Menu de opções`: menu para editar e remover
- `Extras`: técnica para enviar e receber informações entre Activities
- `inicialização lateinit e lazy`: técnicas para criar propriedades em Activities que não podem ser inicializadas na construção da Activity

## 📁 Acesso ao projeto

Você pode acessar o [código fonte do projeto inicial](https://github.com/alura-cursos/android-com-kotlin-persistencia-de-dados-com-room/tree/projeto-inicial) ou [baixá-lo](https://github.com/alura-cursos/android-com-kotlin-persistencia-de-dados-com-room/archive/refs/heads/projeto-inicial.zip).

## 🛠️ Abrir e rodar o projeto

Após baixar o projeto, você pode abrir com o Android Studio. Para isso, na tela de launcher clique em:

Open an Existing Project (ou alguma opção similar)
Procure o local onde o projeto está e o selecione (Caso o projeto seja baixado via zip, é necessário extraí-lo antes de procurá-lo)
Por fim clique em OK
O Android Studio deve executar algumas tasks do Gradle para configurar o projeto, aguarde até finalizar. Ao finalizar as tasks, você pode executar o App 🏆

<!-- ## 📚 Mais informações do curso

**Faça um CTA para o curso do projeto**

-->
