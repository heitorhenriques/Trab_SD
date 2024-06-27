<h1 align="center" style="font-weight: bold;">Trabalho Prático - SD - 2024/1 
<br>Heitor, Bernardo e Matheus
</h1>

<p align="center">
  <a href="#bully">BullyAlgorithm</a> • 
  <a href="#sequencerJS">SequencerAlgorithm</a> •
  <a href="#sequencerJava">SequencerAlgorithmJava</a>
</p>

<h2 id="bully">BullyAlgorithm</h2>

<h2 id="sequencerJS">SequencerAlgorithm</h2>

<h2 id="sequencerJava">SequencerAlgorithmJava</h2>

### Sobre

SequencerAlgorithmJava é um projeto que simula um algoritmo sequenciador móvel em Java. Para tal, foram considerados 3 processos clientes, 3 processos sequenciadores e 3 processos servidores.

O programa primeiramente sorteia um processo sequenciador para ser o lider. Após isso, os processos clientes enviam cada uma mensagem (M1, M2, M3) para o líder, que por sua vez, sequencia as mensagens aleatoriamente e envia para os servidores.

Os servidores recebem as mensagens sequenciadas e processam a requisição. O primeiro processo servidor a finalizar a requisição (definido aleatoriamente pelo programa) envia uma mensagem OK para os demais servidores e envia a resposta da requisição ao sequenciador lider, que então repassa a resposta aos clientes.

Ao final desta execução, troca-se o líder pelo próximo sequenciador disponível e o processo se repete (Como há 3 processos sequenciadores, então há 3 rodadas de execução do programa).

O programa imprime no console e em um arquivo pdf todas as etapas, além das mensagens enviadas e recebidas por cada processo.

Após isso, o programa finaliza.

### Como executar localmente

#### Pré requisitos

- Java 17+
- Maven 3.9.3+

#### Passo a passo

Acessar a pasta do projeto SequencerAlgorithmJava

```bash
cd SequencerAlgorithmJava
```

Rodar o comando abaixo na raiz do projeto:

```bash
mvn clean install -DskipTests
```

Após isso, rodar o comando abaixo para executar o projeto:

```bash
java -jar ./target/sequenceralgorithmjava-1.jar
```

O programa vai printar no console a sequência de execução dos processos e também vai gerar um relatório em pdf na pasta raiz do projeto, com o nome de `"Relatorio.pdf"`.
