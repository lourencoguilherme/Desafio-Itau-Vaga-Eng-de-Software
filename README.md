# Desafio-Itau-Vaga-Eng-de-Software
Desafio Itau Vaga Eng de Software Itau

### O Desafio

 O tema do desafio é livre, use a criatividade e desenvolva uma aplicação que atenda os critérios abaixo:

1. Faça um diagrama de sequência e um desenho de solução para explicação da sua aplicação;
2. Sua aplicação deverá prover a capacidade de realizar as operações de leitura, cadastro, edição e exclusão;
3. Implemente uma feature que identifique uma mudança no banco de dados e faça uma extração dos dados modificados para um arquivo;
4. Sua aplicação deverá utilizar os conceitos básicos de SOLID;
5. Você pode optar em desenvolver apenas o frontend da sua aplicação ou backend, mas se sentir confortável desenvolva ambos;
6. Seu repositório deverá ser exposto publicamente no Git. 

#### Desenho da solução
![Screenshot from 2024-06-06 10-57-28](https://github.com/lourencoguilherme/desafio-itau-vaga-eng-de-software/assets/49289593/08969ca2-a3dd-45fe-91da-08394cc054e5)

#### Diagrama de sequência
![Screenshot from 2024-06-06 11-08-45](https://github.com/lourencoguilherme/desafio-itau-vaga-eng-de-software/assets/49289593/2f89f73a-e698-42ec-96f5-0bb412fbe35d)


## Para rodar o projeto
1. Execute o comando `docker-compose up`:

   ```bash
   docker-compose up

2. A documentação do projeto vai subir em http://localhost:8080/swagger-ui/index.html

## Criando uma Migração com o Flyway

Para criar uma migração usando o Flyway:

1. Certifique-se de ter o script `create_migration.sh` localizado na raiz do seu projeto.

2. Abra um terminal na raiz do projeto.

3. Execute o script `create_migration.sh`, passando o nome da migração como argumento. Por exemplo:

   ```bash
   ./create_migration.sh create_table_customers

## Listar e configurar bucket s3

Para instalar o awslocal:

1. Certifique-se de ter o script `awslocal-install.sh` localizado na pasta scripts/localstack.

2. Abra um terminal na pasta.

3. Execute o script `awslocal-install.sh`. Por exemplo:

   ```bash
   ./scripts/localstack/awslocal-install.sh

4. para listar os buckets e conferir se foi criado `awslocal s3 ls`:

   ```bash
   awslocal s3 ls

5. para listar os buckets e conferir se foi criado `awslocal s3 ls`:

   ```bash
   awslocal s3 ls s3://customers-changes --recursive


## Teste da aplicação

1. Para criar um novo recurso

   ```bash
      curl -X 'POST' \
      'http://localhost:8080/customers' \
      -H 'accept: */*' \
      -H 'Content-Type: application/json' \
      -d '{
      "name": "string"
      }'
   

2. Para list os customers

   ```bash
      curl -X 'GET' \
      'http://localhost:8080/customers' \
      -H 'accept: */*'

3. Para listar um customers

   ```bash
      curl -X 'GET' \
      'http://localhost:8080/customers/{customerId}' \
      -H 'accept: */*'


4. Para editar um customers

   ```bash
      curl -X 'PUT' \
      'http://localhost:8080/customers/{customerId}' \
      -H 'accept: */*' \
      -H 'Content-Type: application/json' \
      -d '{
      "name": "teste"
      }'

5. Para deletar um customers

   ```bash
         curl -X 'DELETE' \
      'http://localhost:8080/customers/{customerId}' \
      -H 'accept: */*'

6. Para sincronizar a tabela de auditoria

   ```bash
      curl -X 'GET' \
      'http://localhost:8080/audits/create' \
      -H 'accept: */*'

7. Para conferir se os recursos foram criados no s3:

   ```bash
   awslocal s3 ls s3://customers-changes --recursive

8. Para ver os arquivos temporarios:

   ```bash
   docker exec  desafio-itau-vaga-eng-de-software_backend_1 ls /app/temp