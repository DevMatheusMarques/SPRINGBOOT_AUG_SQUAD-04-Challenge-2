# SPRINGBOOT_AUG_SQUAD-04-Challenge-2

# Projeto Sistema de Gestão de Estacionamento (Atividade 3 e Desafio Semana 04) -  SPRINGBOOT_AWS_AGO24

Este projeto tem como objetivo atender as exigências da ativiadade da semana 03 e do desafio da semana 04 do programa de bolsas da Compass UOL.

### Observação


## Requisitos
Certifique-se de ter as seguintes ferramentas instaladas:

- Java Development Kit (JDK) 21 ou superior.
- IDE para trabalhar com Java.
- MYSQL versão LTS 8.4.0.
- Conector do Java versão 8.4.0 (mysql.connector.j) - Utilizado gerenciador de pacotes Maven para instalação do conector ao projeto.
- Ferramenta Postman para requisições HTTP.

## Configuração do Projeto e Banco de Dados
Siga os passos abaixo para configurar o projeto em seu ambiente de testes:

1. **Clone o repositório**

   ```bash
   git clone https://github.com/DevMatheusMarques/https://github.com/DevMatheusMarques/SPRINGBOOT_AUG_SQUAD-04-Challenge-2.git
   cd nome-do-diretorio
   ```

2. **Configuração do projeto**:

   Observe a estrutura de pastas do projeto e encontre a pasta resources. Ela vai estar no seguinte caminho:
    ```bash
   cd SPRINGBOOT_AUG_SQUAD_04_Challenge_2/main/resources
   ```
   Nesta pasta você vai encontrar um arquivo db.properties. Este arquivo contem a configuração das propriedades de acesso ao banco de dados. Como neste exemplo da figura a seguir:

   Você deve alterar as seguintes variáveis:
    - spring.datasource.username
    - spring.datasource.password

   Essas variaveis representam o respectivo nome e senha da sua conexão do banco de dados mysql. Você deve inserir os dados conforme o nome e senha que estiver em sua máquina.


3. **Executar o projeto (Classe SpringbootAugSquad04Challenge2Application)**

   Acesse o projeto e procure pela classe SpringbootAugSquad04Challenge2Application. Ela vai estar no seguinte caminho:
   ```bash
   cd SPRINGBOOT_AUG_SQUAD_04_Challenge_2/src/main/java/com.compass/SPRINGBOOT_AUG_SQUAD_04_Challenge_2
   ```
   Selecione a classe main e execute a opção de Run 'SpringbootAugSquad04Challenge2Application.java' em sua IDE.


4. **Requisições através do Postman**
   
   Acesse a ferramenta Postman e em sua máquina. Nela você pode utilizar endpoints de GET, POST, PATCH e DELETE:


   ![img.png](img/img.png)


   Para saber sobre como utilizar cada um dos endpoints, com a aplicação em execução, acesse a seguinte URL:

   ```bash
   http://localhost:8080/swagger-ui/index.html#/
   ```

   Nesta URL você vai encontrar como deve utilizar cada endpoint, quais URL'S e parâmetros utilizar e quais resposta você pode obter. Inclusiva você pode testar o sistema pela própria documentação.

   Imagem geral da documentação:

   ![img.png](img/img2.png)


5. **Geração de Teste de Cobertura**

   Para gerar o teste de cobertura do sistema e verificar a porcentagem que os testes cobriram do sistema, basta utilizar este comando no terminal:

   ```bash
   ./mvnw clean test jacoco:report
   ```
   
   Para abrir o arquivo gerado e visualizar o teste de cobertura, basta abrir este arquivo no navegador:


   ![img.png](img/img3.png)


6. **Observação**

   Ao tentar executar o teste da classe TicketsTestIT por favor executar os testes um por vez de cada método para garantir a integridade dos testes.

### Contato
Em caso de dúvidas ou problemas, entre em contato com:

* Nome: Matheus Marques
* Email: matheus.marques.pb@compasso.com.br
* GitHub: https://github.com/DevMatheusMarques


* Nome: Lucas Generoso
* Email: lucas.generoso.pb@compasso.com.br
* GitHub: https://github.com/LucasGeneroso15


* Nome: Lívia Portela
* Email: livia.ferreira.pb@compasso.com.br
* GitHub: https://github.com/liviaportela


* Nome: Jorge Massaru
* Email: jorge.hashiguchi.pb@compasso.com.br
* GitHub: https://github.com/JorgeMassaru


* Nome: Gustavo Bonfim
* Email: gustavo.souza.pb@compasso.com.br
* GitHub: https://github.com/Bonfim-Gusta


* Nome: Rafael Garcia
* Email: rafael.garcia.pb@compasso.com.br
* GitHub: https://github.com/rafaelbg-001
