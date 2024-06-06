#!/bin/bash

# Nome do bucket a ser criado
BUCKET_NAME="customers_changes"

# Arquivo temporário para armazenar a saída do comando
TEMP_FILE=$(mktemp)

# Execute o comando para criar o bucket no contêiner do LocalStack e redirecione a saída para o arquivo temporário
docker-compose -f ../docker-compose.yml exec -T localstack aws --endpoint-url=http://localstack:4566 s3api create-bucket --bucket "$BUCKET_NAME" > "$TEMP_FILE" 2>&1

# Verifica se houve algum erro
if [ $? -eq 0 ]; then
    echo "Bucket '$BUCKET_NAME' criado com sucesso."
else
    echo "Erro ao criar o bucket '$BUCKET_NAME': $(cat "$TEMP_FILE")"
fi

# Remove o arquivo temporário
rm "$TEMP_FILE"
