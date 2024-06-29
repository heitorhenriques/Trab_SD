<h1 align="center" style="font-weight: bold;">Trabalho Prático - SD - 2024/1 
<br>Heitor, Bernardo e Matheus
</h1>

<p align="center">
  <a href="#bully">BullyAlgorithm</a> • 
  <a href="#visualSequencer">SequencerAlgorithm</a> •
  <a href="#sequencerJava">SequencerAlgorithmJava</a>
</p>

<h2 id="bully">BullyAlgorithm</h2>

### Sobre

O BullyAlgorithm é um algoritimo que simula o algoritimo de bully, o algoritimo é feito em sistemas multiagents com base em um framework chamado JaCaMo que em suma é uma junção de frameworks o Jason que seria a parte dos agentes que tem como base o ASL (agentspeak language), Cartago uma framework para trabalhar com o Jason, um framework baseado em artefatos, e por fim o Moise um framework para a estruturação dos agentes.

A ideia do codigo é simular o algoritimo de Bully, o sistema inicia com uma interface grafica e o sistema espera que o botão iniciar seja apertado primeiramente. Após o botão de iniciar o sistema o usuario poderá apertar qualquer botão na interface, são eles o botão de matar um processo que espera que você tenha digitado um numero valido de processo no campo de texto, esse botão ira matar o processo escolhido, o proximo botão é o botão de reviver um processo que vai ressucitar um processo previamente morto, e por fim o botão de qual processo vai iniciar a percepção de que o processo líder está morto.

### Como executar localmente

#### Pré requisitos

- Java 17+
- Gradle 8.7+

#### Passo a Passo

Acessar a pasta do projeto SequencerAlgorithmJava

```bash
cd BullyAlgorithm
```

Rodar o comando abaixo na raiz do projeto:

```bash
gradle run
```

Depois disso ele vai pegar todas as dependencias necessarias e vai rodar o programa abrindo a interface grafica.

Qualquer dificuldade em rodar o programa mais informações podem ser adquiridas no link a seguir [Instalação JaCaMo](https://github.com/jacamo-lang/jacamo/blob/main/doc/install.adoc)

<h2 id="visualSequencer">SequencerAlgorithm</h2>

### Sobre

O VisualSequencer é um projeto criado em HTML e JavaScript para simular um sequenciador móvel. No exemplo, 3 clientes podem enviar mensagens a 6 processos sequenciadores que enviam a 3 receptores. 

O usuário tem liberdade para personalizar mensagens que são enviadas pelos clientes C1, C2 e C3 e também pode escolher que algum desses não envie nada. Ao pressionar o botão de envio, os clientes selecionados enviarão suas respectivas mensages ao processo sequenciador líder.

O processo sequenciador líder é representado por um processo <span style="color: #34db98;">**verde**</span>. Ao receber as mensagens dos clientes, o processo líder reorganiza a ordem das mensagens (para simular, a lista de mensagens é apenas invertida) e envia para os 3 receptores. Simulando latências, os tempos de envio, recebimento e processamento dos processos e receptores são aleatórios, com um mínimo de 3 segundos de espera para fins de visualização.

Quando os receptores recebem as mensagens do sequenciador líder, os três iniciam uma simulação de processamento (com tempo aleatório) e, quando o primeiro receptor finaliza o processamento, ele avisa aos outros receptores que finalizou e que retornará uma mensagem de OK ao processo líder. Ao receber o OK, o processo líder sequencia e passa ao próximo processo no sequenciador. 

Ao fim de todo o ciclo de mensagens, o programa passa a esperar um novo envio de mensagens do usuário, com um novo líder dos processos sequenciadores.

### Como executar localmente

#### Pré requisitos
- Python
- Flask

#### Passo a passo
Acesse o diretório do projeto VisualSequencerAlgorithm

```bash
cd VisualSequencerAlgorithm
```

Use o comando na raiz do projeto:

```bash
python app.py
```

Isso iniciará um servidor em [127.0.0.1:5000](http://127.0.0.1:5000), com a página web rodando.

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
