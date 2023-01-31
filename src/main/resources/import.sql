insert into cozinha (id, nome) values(1, 'Tailandesa');
insert into cozinha (id, nome) values(2, 'Indiana');
insert into cozinha (id, nome) values(3, 'Italiana');

insert into forma_pagamento (descricao) values('Débito');
insert into forma_pagamento (descricao) values('Crédito');
insert into forma_pagamento (descricao) values('Pix');
insert into forma_pagamento (descricao) values('Dinheiro');

insert into restaurante (nome, taxa_frete, cozinha_id, forma_pagamento_id) values('Bobs', 5.99, 1, 4);
insert into restaurante (nome, taxa_frete, cozinha_id, forma_pagamento_id) values('Mc', 9.99, 1, 2);

insert into estado (nome) values('Tocantins');
insert into estado (nome) values('Maranhão');

insert into cidade (nome, estado_id) values('Balsas', 2);
insert into cidade (nome, estado_id) values('Palmas', 1);




