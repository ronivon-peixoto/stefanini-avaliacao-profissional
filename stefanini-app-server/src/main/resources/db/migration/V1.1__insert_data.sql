-- EMPRESA
insert into empresa (id, cnpj, razao_social, nome_fantasia) values (1, '58069360000120', 'Stefanini Consultoria e Assessoria em Informática S.A.', 'Stefanini IT Solutions');

-- VALORES DA EMPRESA
insert into valor_empresa (id, descricao, peso, empresa_id) values (1, 'Fazer a diferença: Busca atuar com iniciativa para apresentar novas propostas e soluções que além de auxiliar nas atividades desempenhadas, fazem a diferença para o todo;', 15, 1);
insert into valor_empresa (id, descricao, peso, empresa_id) values (2, 'Agir como empreendedor: Está sempre atento à mudanças e novidades para implementar novas maneiras de se fazer as coisas por aqui;', 15, 1);
insert into valor_empresa (id, descricao, peso, empresa_id) values (3, 'Acreditar e respeitar as pessoas: Apresenta boa interação com pessoas que trabalham aqui, respeita as diferenças e acredita que cada um tem papel fundamental na construção da empresa;', 15, 1);
insert into valor_empresa (id, descricao, peso, empresa_id) values (4, 'Ter humildade em aprender: Está sempre em busca de novos conhecimentos e acredita que feedbacks são necessários para o crescimento e desenvolvimento profissional;', 10, 1);
insert into valor_empresa (id, descricao, peso, empresa_id) values (5, 'Inovar com o cliente, ser ético e agir com o que fala;', 15, 1);
insert into valor_empresa (id, descricao, peso, empresa_id) values (6, 'Ser ético e agir de acordo com o que fala: Evita se envolver em discussões superficiais para o ambiente de trabalho e estabelece padrão de comportamento para cumprir direitos e deveres como estagiário (disciplina e assiduidade);', 15, 1);
insert into valor_empresa (id, descricao, peso, empresa_id) values (7, 'Geral com a seguinte descrição: Apresenta habilidade para compreender e executar com qualidade, eficiência, rapidez e precisão as tarefas atribuídas;', 15, 1);

-- CENTROS DE CUSTO
insert into centro_custo (id, nome, endereco, empresa_id, gerente_id) values (1, 'Stefanini - Salvador-Ba', 'Av. Antônio Carlos Magalhães S/N, Centro', 1, NULL);

-- ROLES
insert into role(id, name) values (1, 'ROLE_GERENTE');
insert into role(id, name) values (2, 'ROLE_BP');
insert into role(id, name) values (3, 'ROLE_MENTOR');
insert into role(id, name) values (4, 'ROLE_FUNCIONARIO');
insert into role(id, name) values (5, 'ROLE_USER'); -- CONVIDADO/CANDIDATO

-- USERS
insert into user(id, name, username, email, password) values (1, 'Pedro dos Santos', 'gerente.pedro', 'gerente.pedro@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (2, 'Alícia Lorena Nascimento', 'bp.alicia', 'bp.alicia@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (3, 'Antonella Daiane Porto', 'mentor.antonella', 'mentor.antonella@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (4, 'Elaine Cláudia Antônia Campos', 'mentor.elaine', 'mentor.elaine@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (5, 'Luana Larissa Viana', 'funcionario.luana', 'funcionario.luana@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (6, 'Clara Lívia Sophia Gonçalves', 'funcionario.clara', 'funcionario.clara@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (7, 'Matheus Anthony Ramos', 'funcionario.matheus', 'funcionario.matheus@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (8, 'Antonella Raquel Olivia Carvalho', 'funcionario.antonella', 'funcionario.antonella@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (9, 'Thales Henry Igor Gonçalves', 'funcionario.thales', 'funcionario.thales@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (10, 'Anderson Diogo Galvão', 'funcionario.anderson', 'funcionario.anderson@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (11, 'Miguel Osvaldo Tomás Gonçalves', 'funcionario.miguel', 'funcionario.miguel@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (12, 'Lucca Benjamin Theo Moura', 'funcionario.lucca', 'funcionario.lucca@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (13, 'Marli Ayla Daniela Moura', 'funcionario.marli', 'funcionario.marli@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (14, 'Carolina Analu Galvão', 'funcionario.carolina', 'funcionario.carolina@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (15, 'Silvana Sandra Larissa Figueiredo', 'funcionario.silvana', 'funcionario.silvana@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (16, 'Mário Nelson Ribeiro', 'funcionario.mario', 'funcionario.mario@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (17, 'Rosa Sabrina Liz Moreira', 'funcionario.rosa', 'funcionario.rosa@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (18, 'Hugo Thales Aragão', 'funcionario.hugo', 'funcionario.hugo@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (19, 'Luciana Malu Tatiane Gonçalves', 'funcionario.luciana', 'funcionario.luciana@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (20, 'Enrico Vinicius Carvalho', 'funcionario.enrico', 'funcionario.enrico@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (21, 'Filipe Henry Cavalcanti', 'funcionario.filipe', 'funcionario.filipe@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.');
insert into user(id, name, username, email, password) values (22, 'Convidado', 'convidado', 'convidado@stefanini.com', '$2a$10$1Rqm5oCoEwFlH4M.arpJCeYO3vjfhJVorVUDd1lJBnQ8xCqDWBNB.'); -- CONVIDADO/CANDIDATO

-- USER X ROLES
insert into user_role(user_id, role_id) values (1, 1);
insert into user_role(user_id, role_id) values (2, 2);
insert into user_role(user_id, role_id) values (3, 3);
insert into user_role(user_id, role_id) values (4, 3);
insert into user_role(user_id, role_id) values (5, 4);
insert into user_role(user_id, role_id) values (6, 4);
insert into user_role(user_id, role_id) values (7, 4);
insert into user_role(user_id, role_id) values (8, 4);
insert into user_role(user_id, role_id) values (9, 4);
insert into user_role(user_id, role_id) values (10, 4);
insert into user_role(user_id, role_id) values (11, 4);
insert into user_role(user_id, role_id) values (12, 4);
insert into user_role(user_id, role_id) values (13, 4);
insert into user_role(user_id, role_id) values (14, 4);
insert into user_role(user_id, role_id) values (15, 4);
insert into user_role(user_id, role_id) values (16, 4);
insert into user_role(user_id, role_id) values (17, 4);
insert into user_role(user_id, role_id) values (18, 4);
insert into user_role(user_id, role_id) values (19, 4);
insert into user_role(user_id, role_id) values (20, 4);
insert into user_role(user_id, role_id) values (21, 4);
insert into user_role(user_id, role_id) values (22, 5);

-- FUNCIONÁRIOS
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (1, 'Pedro dos Santos', '17632287205', 					CURRENT_TIMESTAMP(), 1, 5, 'Gerente', NULL, 1, 1, NULL);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (2, 'Alícia Lorena Nascimento', '20647340828', 			CURRENT_TIMESTAMP(), 2, 5, 'Business Partner', NULL, 1, 1, NULL);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (3, 'Antonella Daiane Porto', '36637257150', 			CURRENT_TIMESTAMP(), 3, 5, 'Mentor', NULL, 1, 1, NULL);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (4, 'Elaine Cláudia Antônia Campos', '20078051622', 		CURRENT_TIMESTAMP(), 4, 5, 'Mentor', NULL, 1, 1, NULL);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (5, 'Luana Larissa Viana', '51807563839', 				CURRENT_TIMESTAMP(), 5, 3, 'Desenvolvedor Pleno', NULL, 1, 1, 3);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (6, 'Clara Lívia Sophia Gonçalves', '62124316125', 		CURRENT_TIMESTAMP(), 6, 1, 'Desenvolvedor Junior', NULL, 1, 1, 3);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (7, 'Matheus Anthony Ramos', '36641764486', 				CURRENT_TIMESTAMP(), 7, 5, 'Desenvolvedor Senior', NULL, 1, 1, NULL);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (8, 'Antonella Raquel Olivia Carvalho', '45507668400', 	CURRENT_TIMESTAMP(), 8, 1, 'Desenvolvedor Junior', NULL, 1, 1, 3);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (9, 'Thales Henry Igor Gonçalves', '62160185230', 		CURRENT_TIMESTAMP(), 9, 2, 'Desenvolvedor Junior', NULL, 1, 1, 3);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (10, 'Anderson Diogo Galvão', '84745285850', 			CURRENT_TIMESTAMP(), 10, 2, 'Desenvolvedor Junior', NULL, 1, 1, 3);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (11, 'Miguel Osvaldo Tomás Gonçalves', '78821687341', 	CURRENT_TIMESTAMP(), 11, 3, 'Desenvolvedor Pleno', NULL, 1, 1, 3);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (12, 'Lucca Benjamin Theo Moura', '81172141215', 		CURRENT_TIMESTAMP(), 12, 3, 'Desenvolvedor Pleno', NULL, 1, 1, 3);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (13, 'Marli Ayla Daniela Moura', '46683206090', 			CURRENT_TIMESTAMP(), 13, 3, 'Desenvolvedor Junior', NULL, 1, 1, 4);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (14, 'Carolina Analu Galvão', '33847661051', 			CURRENT_TIMESTAMP(), 14, 1, 'Desenvolvedor Junior', NULL, 1, 1, 4);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (15, 'Silvana Sandra Larissa Figueiredo', '20275524213', CURRENT_TIMESTAMP(), 15, 3, 'Desenvolvedor Pleno', NULL, 1, 1, 4);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (16, 'Mário Nelson Ribeiro', '58436344502', 				CURRENT_TIMESTAMP(), 16, 4, 'Desenvolvedor Pleno', NULL, 1, 1, 4);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (17, 'Rosa Sabrina Liz Moreira', '73437608584', 			CURRENT_TIMESTAMP(), 17, 3, 'Desenvolvedor Pleno', NULL, 1, 1, 4);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (18, 'Hugo Thales Aragão', '75311312009', 				CURRENT_TIMESTAMP(), 18, 4, 'Desenvolvedor Pleno', NULL, 1, 1, 4);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (19, 'Luciana Malu Tatiane Gonçalves', '88146621775', 	CURRENT_TIMESTAMP(), 19, 5, 'Desenvolvedor Senior', NULL, 1, 1, NULL);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (20, 'Enrico Vinicius Carvalho', '87157772191', 			CURRENT_TIMESTAMP(), 20, 2, 'Desenvolvedor Junior', NULL, 1, 1, 4);
insert into funcionario (id, nome, cpf, data_admissao, usuario_id, nivel, cargo, data_promocao, empresa_id, centro_custo_id, mentor_id) values (21, 'Filipe Henry Cavalcanti', '32630647030', 			CURRENT_TIMESTAMP(), 21, 1, 'Desenvolvedor Junior', NULL, 1, 1, 4);

-- GERENTE DO CENTRO DE CUSTO (1)
update centro_custo set gerente_id = 1 where id = 1;
