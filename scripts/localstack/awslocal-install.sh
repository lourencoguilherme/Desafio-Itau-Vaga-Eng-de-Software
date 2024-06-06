# Passo 1: Instalar LocalStack e awslocal
pip install localstack awscli-local

# Passo 2: Iniciar o LocalStack
localstack start -d

# Passo 3: Configurar o AWS CLI para usar o LocalStack
aws configure --profile localstack

# Passo 4: Listar os buckets S3 usando awslocal
awslocal s3 ls
