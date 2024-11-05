Gestão Fácil
Equipe
Enzo Oliveira - RM 551356
João Vitor - RM 550381
Pedro - RM 551446
Matheus Colossal - RM 99572
Igor - RM 550415
Como Rodar a Aplicação
Clone o repositório:

bash
Copiar código
git clone https://github.com/colossalmatheus/Sprint4.git
cd Sprint4
Configure o projeto:

Verifique o arquivo application-homologacao.yml para ajustar as credenciais do banco de dados.
Crie e execute a imagem Docker:

bash
Copiar código
docker build -t gestao-facil .
docker run -p 8080:8080 gestao-facil
Deploy no Azure:

Use a imagem Docker no Azure WebApp para container.
