# Desafio-Itau-Vaga-Eng-de-Software
Desafio Itau Vaga Eng de Software Itau

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

   
