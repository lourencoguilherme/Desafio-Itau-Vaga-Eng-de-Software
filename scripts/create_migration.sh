#!/bin/bash

# Verifica se o nome da migração foi passado como parâmetro
if [ -z "$1" ]; then
  echo "Uso: $0 <nome_da_migracao>"
  exit 1
fi

# Obtém o timestamp atual
timestamp=$(date +%Y%m%d%H%M%S)

# Nome do arquivo de migração
migration_filename="V${timestamp}__$1.sql"

# Caminho onde o arquivo será criado
migration_path="../backend/src/main/resources/db/migration"

# Cria o diretório de migração se não existir
mkdir -p "$migration_path"

# Conteúdo do arquivo de migração
cat <<EOL > "${migration_path}/${migration_filename}"
-- Migration script: $1
-- Add your SQL here

EOL

# Mensagem de sucesso
echo "Migration file created: ${migration_path}/${migration_filename}"
