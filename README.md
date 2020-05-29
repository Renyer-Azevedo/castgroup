# castgroup
Um microsserviço que oferece uma API de cadastro de férias dos funcionários. 
A API deve possui:

• signin
  o email
  o password
• Cadastro de uma nova equipe:
  o Nome
• Cadastro de um novo funcionário:
  o Nome
  o Data de Nascimento
  o Endereço
     Rua
     Numero
     Complemento
     Bairro
     Cidade
     Estado
  o Data de contratação
  o Foto do Funcionário
  o Equipe
  o Matricula (gerada automaticamente pelo sistema)
• Cadastro de férias do funcionário:
  o Funcionário
  o Período de férias
• Listagem das Equipes;
• Listagem dos funcionários;
• Listagem das férias;
• Listagem de funcionários que devem solicitar férias (funcionários que irão completar 2 anos sem
solicitar férias em no máximo X meses) ;
• Remover férias;
• Visualização de detalhes de um funcionário (Criar uma tela);
• Busca de férias por matrícula do funcionário.

Requisitos do negócio:
• Para realizar o cadastro de férias:
o O funcionário não pode solicitar férias antes de 1 ano de contratação
o Caso a equipe tenha até 4 pessoas, não é permitido duas pessoas tirarem férias em
períodos que tenha ao menos um dia coincidente.
o Após o cadastro deve ser exibida na tela uma imagem com um QR-Code cujos dados são
a representação JSON dos dados cadastrados para o funcionário (exceto a foto).
o Este QR-Code também deve ser enviado por email automaticamente após o cadastro
de um novo equipamento. O email de destino não pode estar hardcoded na aplicação;
• Na lista de funcionários que devem solicitar férias X deve ser um parâmetro e não pode estar
hardcodeed na aplicação;
• Ao realizar o signin deve ser retornado um token JWT e este token deve ser passado e validado
nas urls de cadastro. O usuário pode ser cadastro previamente no banco de dados
o Email: administrador.ferias@castgroup.com.br
o Senha: 123a
