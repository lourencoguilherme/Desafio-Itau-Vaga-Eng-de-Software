import subprocess
import json

def list_buckets():
    # Execute o comando para listar os buckets no contêiner do LocalStack
    command = "docker-compose -f ../docker-compose.yml exec -T localstack aws --endpoint-url=http://localstack:4566 s3api list-buckets"
    result = subprocess.run(command, shell=True, capture_output=True, text=True)

    # Verifica se houve algum erro
    if result.returncode == 0:
        # Se o comando foi bem-sucedido, parseie a saída JSON e liste os buckets
        buckets_info = json.loads(result.stdout)
        buckets = [bucket["Name"] for bucket in buckets_info["Buckets"]]
        print("Buckets disponíveis:")
        for bucket in buckets:
            print(bucket)
    else:
        # Se houve um erro, exiba a mensagem de erro
        print("Erro ao listar os buckets:", result.stderr)

if __name__ == "__main__":
    list_buckets()
