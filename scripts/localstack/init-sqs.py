import boto3

def create_sqs_queue():
    sqs = boto3.client('sqs', endpoint_url='http://localhost:4566', region_name='us-east-1')
    response = sqs.create_queue(
        QueueName='customers-queue'
    )
    print("Fila criada com sucesso:", response)

create_sqs_queue()
