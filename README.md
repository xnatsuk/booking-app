# Booking App API

Projeto para o Bootcamp da IBM.


# API Endpoints

**Todas as respostas seguem esse padrão:**

    {
	    "id": 1,
	    "nomeHospede": "Fulaninho",
	    "dataInicio": "2023-08-10",
        "dataFim": "2023-08-15",
        "quantidadePessoas": 6,
        "status": "PENDENTE"
    }




### [POST] /reservas
Cria uma nova reserva.

**Body:**

    {
	    "nomeHospede": "Fulano de Tal",
        "dataInicio": "2023-08-10",
        "dataFim": "2023-08-15",
        "quantidadePessoas": 4
    }

**Response:**

    {
        "id": 1,
		"nomeHospede": "Fulano de Tal",
        "dataInicio": "2023-08-10",
        "dataFim": "2023-08-15",
        "quantidadePessoas": 4,
        "status": "CONFIRMADA"
	}
	
### [DELETE] /reservas/{id}/cancelar

Muda o status da reserva para `CANCELADA`.

**Response:**

    {
        "id": 1,
		"nomeHospede": "Fulano de Tal",
        "dataInicio": "2023-08-10",
        "dataFim": "2023-08-15",
        "quantidadePessoas": 4,
        "status": "CANCELADA"
	}

### [GET] /reservas 

Retorna a lista com todas as reservas.

**Response:**

    {
        "id": 1,
        "nomeHospede": "Fulano de Tal",
        "dataInicio": "2023-08-10",
        "dataFim": "2023-08-15",
        "quantidadePessoas": 4,
        "status": "CONFIRMADA"
	},
    {
	    "id": 2,
	    "nomeHospede": "Fulaninho",
	    "dataInicio": "2023-08-10",
        "dataFim": "2023-08-15",
        "quantidadePessoas": 6,
        "status": "PENDENTE"
    }

### [GET ] /reservas/{id}
Retorna a reserva com o `id` correspondente.

**Response:**

    {
	    "id": 2,
	    "nomeHospede": "Fulaninho",
	    "dataInicio": "2023-08-10",
        "dataFim": "2023-08-15",
        "quantidadePessoas": 6,
        "status": "CONFIRMADA"
    }


### [PUT] /reservas/{id}

Atualiza a reserva com o `id` correspondente.

**Body:**

    {
	    "nomeHospede": "Fulaninho",
	    "dataInicio": "2023-08-10",
        "dataFim": "2023-08-15",
        "quantidadePessoas": 6,
        "status": "PENDENTE"
    }

**Response:**

    {
	    "id": 2,
	    "nomeHospede": "Fulaninho",
	    "dataInicio": "2023-08-10",
        "dataFim": "2023-08-15",
        "quantidadePessoas": 6,
        "status": "PENDENTE"
    }

	

