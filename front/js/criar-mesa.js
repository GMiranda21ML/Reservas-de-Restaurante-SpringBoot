document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('criarMesaForm');
    form.addEventListener('submit', async function (e) {
        e.preventDefault();

        const nome = document.getElementById('mesa-nome').value.trim();
        const capacidade = parseInt(document.getElementById('mesa-capacidade').value, 10);
        const status = document.getElementById('mesa-status').value;

        const mesaData = { nome, capacidade, status };

        const token = localStorage.getItem('jwtToken');
        if (!token) {
            alert('Usuário não autenticado.');
            window.location.href = 'login.html';
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/mesas', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token 
                },
                body: JSON.stringify(mesaData)
            });

            if (response.ok) {
                alert('Mesa criada com sucesso!');
                form.reset();
            } else {
                let errorMsg = response.statusText;
                try {
                    const text = await response.text();
                    if (text) {
                        const errorData = JSON.parse(text);
                        if (errorData.message) errorMsg = errorData.message;
                    }
                } catch (e) {

                }
                alert('Erro ao criar mesa: ' + errorMsg);
            }
        } catch (error) {
            alert('Erro de conexão com o servidor: ' + error.message);
        }
    });
});
