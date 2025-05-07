# Aurora Pix

Este projeto é uma integração com o **Stark Bank** para gerenciar transações Pix de forma eficiente e segura.

## Funcionalidades

- Criação e gerenciamento de cobranças Pix.
- Consulta de transações Pix recebidas.
- Integração com a API do Stark Bank.
- Logs detalhados para auditoria.

## Tecnologias Utilizadas

- **Linguagem:** [Especifique aqui, ex.: Python, Node.js]
- **Framework:** [Especifique aqui, ex.: Flask, Express]
- **Bibliotecas:** [Especifique aqui, ex.: starkbank, dotenv]

## Configuração do Ambiente

1. Clone o repositório:
    ```bash
    git clone https://github.com/seu-usuario/aurora-pix.git
    ```
2. Instale as dependências:
    ```bash
    [Comando para instalar dependências, ex.: npm install ou pip install -r requirements.txt]
    ```
3. Configure as variáveis de ambiente no arquivo `.env`:
    ```env
    STARKBANK_SECRET=seu-segredo
    STARKBANK_PROJECT_ID=seu-id
    ```

## Como Usar

1. Inicie o servidor:
    ```bash
    [Comando para iniciar o servidor, ex.: npm start ou python app.py]
    ```
2. Acesse a aplicação em: `http://localhost:3000` (ou outra porta configurada).

## Subir para hub.docker.com

```bash
    docker login
```

```bash
    docker build -t aurora-pix:latest .
```

```bash
    docker tag aurora-pix:latest alexsouzasilvax/aurora-pix:latest
```

```bash
    docker push alexsouzasilvax/aurora-pix:latest
```

## Deploy no Render

 - Acessar painel dashboard do [Render](https://dashboard.render.com/web/srv-cr28kp3tq21c73fmf3ag/logs)
 - Manual Deploy --> Deploy latest reference

## Contribuição

Contribuições são bem-vindas! Siga os passos abaixo:

1. Faça um fork do projeto.
2. Crie uma branch para sua feature:
    ```bash
    git checkout -b minha-feature
    ```
3. Faça o commit das suas alterações:
    ```bash
    git commit -m "Minha nova feature"
    ```
4. Envie para o repositório remoto:
    ```bash
    git push origin minha-feature
    ```
5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

## Contato

Para dúvidas ou sugestões, entre em contato: [alexsouzasilvax@gmail.com]