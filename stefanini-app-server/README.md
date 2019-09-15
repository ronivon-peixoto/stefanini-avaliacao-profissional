# Stefanini Application Server (REST API)
Esta aplicação foi desenvolvida em Java 8 e Spring Boot 2. Foi adicionado um recurso que permite testar a API sem a utilização da aplicação (cliente) - o Swagger UI.
Apesar de existir configuração relacionada a conexão com uma base de dados MySQL, foi adicionado uma base (em memória), visando agilizar o processo de deploy da aplicação.
Foi implementado um controle de acesso com Spring Security + JWT.


## Instalação

OS X & Linux & Windows:

```sh
mvn install
java -jar target/stefanini-app-server-0.0.1-SNAPSHOT.jar
```


## Configuração

Esta aplicação utiliza Maven como gerenciador de dependências, ao carregar o projeto na sua IDE será realizado o download de todas as dependências de forma automática. Para executar os testes da aplicação com o comando:

```sh
mvn clean test
```


## Acesso à Aplicação
No pacote (resources/db/migration) contém alguns arquivos (SQL) que são responsáveis pela carga inicial dos dados na base. Para cada registro do tipo 'funcionário' presente no arquivo (V1.1__insert_data.sql) existe um usuário com permissões e credenciais de acesso - os mesmos forma configurados com senha (123456) e podem ser utilizados para testes.


## Fluxo de Testes Realizado
- Ajustar a chave (intervalo.meses.avaliar.funcionario=0) existente no arquivo (application.properties) para garantir que as novas avaliações possam ser processadas;
- Ajustar o agendamento do serviço (ScheduledTasks.processarNovasAvaliacoes()) para a linha comentada com ("every minute");
- Acessando a aplicação com o usuário (bp.alicia) que possui o perfil (Business Partner), é possível visualizar todos os funcionários relacionados ao Centro de Custo que ele pertence - também é possível avaliar estes profissionais.
- Acessando a aplicação com o usuário (mentor.antonella) que possui o perfil (Mentor), é possível visualizar e avaliar os profissionais a ele relacionados.
- Após as avaliações serem realizadas pelo (B.P.) e Mentor para o funcionário, estas ficarão disponibilizadas para serem aprovadas pelo Gerente. No momento da aprovação poderá ser observado os (%) recebidos de cada avaliador, bem como ressalvas registradas em avaliação anterior.   
- Após o gerente (gerente.pedro) realizar a aprovação, esta avaliação será disponibilizada no cadastro do profissional em forma de (histórico);
- Cada avaliação com (média >= 80%) que não possua ressalvas do Gerente, incrementará o nível do profissional até atingir o limite (5);
- Quando o nível do profissional atingir o nível correspondente ao próximo cargo, será disponibilizada uma ação de promoção no cadastro do funcionário;
- Cada profissional poderá acessar a aplicação e visualizar seus dados cadastrais, bem como o histórico de avaliações recebidas;
- É possível cadastrar um novo usuário a partir da aplicação - ele receberá um perfil básico e não terá acesso a nenhuma das funcionalidades disponibilizadas nesta versão, exceto a possibilidade de visualizar os seus dados cadastrais. 


## Outros Recursos

- Podemos consultar o modelo de dados (em memória) com o usuário/senha 'sa' em: http://localhost:8080/h2-console
- Podemos testar a API com a interface (Swagger) em: http://localhost:8080/swagger-ui.html

