# Clickbus Places

Este repositório foi criado para "responder" a um teste técnico da empresa Clickbus, onde era necessário criar uma API
CRUD relacionada a lugares.

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.3.1
- Docker e Docker Compose
- MongoDB
- TestContainers

## Getting Started

Para executar esse projeto, é necessário possui o Docker instalado.

1. Clone o repositório e abra seu terminal na pasta raiz.
2. Execute o comando "docker image build . -t clickbus:1.0".
3. Após a imagem ser montada, basta executar o comando "docker compose up -d".

### Endpoints

Todos os endpoints são baseados na URI /api/v1/places

- No método POST, é necessário enviar um JSON, com nome, cidade e estado no corpo da requisição.
- No método GET, caso se acesse a URI /api/v1/places, ele retornará todos os lugares disponíveis, podendo conter um
  parâmetro na requisição para filtrar os lugares com aquele nome, como por exemplo /api/v1/places?filter=Place .
- Ainda no método GET, é possível pegar um lugar específico, bastando ser passado seu ID como uma variável na URL, como por exemplo /api/v1/places/123.
- No método PUT, é necessário enviar um ID como variável de URL, além do JSON contendo nome, cidade e estado.
- Por último, no método DELETE, precisamos apenas passar o ID como variável de URL.


