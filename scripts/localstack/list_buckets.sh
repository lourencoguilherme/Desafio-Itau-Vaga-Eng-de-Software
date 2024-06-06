#!/bin/bash

# Execute o comando para listar os buckets no contêiner do LocalStack
aws --endpoint-url=http://localstack:4566 s3api list-buckets
