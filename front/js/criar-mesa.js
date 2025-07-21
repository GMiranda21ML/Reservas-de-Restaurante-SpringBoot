document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('criarMesaForm');
    const mesasList = document.getElementById('mesas-list');
    const nomeInput = document.getElementById('mesa-nome');
    const capacidadeInput = document.getElementById('mesa-capacidade');
    const statusInput = document.getElementById('mesa-status');

    async function carregarMesas() {
        mesasList.innerHTML = '<p>Carregando...</p>';
        try {
            const response = await authFetch('http://localhost:8080/mesas');
            if (!response) return;
            if (response.ok) {
                const mesas = await response.json();
                renderMesas(mesas);
            } else {
                mesasList.innerHTML = '<p>Erro ao carregar mesas.</p>';
            }
        } catch (e) {
            mesasList.innerHTML = '<p>Erro de conexão.</p>';
        }
    }

    function renderMesas(mesas) {
        mesasList.innerHTML = '';
        if (!mesas || mesas.length === 0) {
            mesasList.innerHTML = '<p>Nenhuma mesa encontrada.</p>';
            return;
        }
        mesas.forEach(mesa => {
            const card = document.createElement('div');
            card.className = 'mesa-card';
            card.innerHTML = `
                <span class="mesa-icon">🍽️</span>
                <h3>${mesa.nome}</h3>
                <p><strong>ID:</strong> ${mesa.id}</p>
                <p>Capacidade: ${mesa.capacidade} pessoas</p>
                <p>Status: ${mesa.status}</p>
                <button class="btn-delete" data-id="${mesa.id}">Deletar</button>
            `;
            mesasList.appendChild(card);
        });
        document.querySelectorAll('.btn-delete').forEach(btn => {
            btn.addEventListener('click', () => deletarMesa(btn.dataset.id));
        });
    }

    async function deletarMesa(id) {
        if (!confirm('Tem certeza que deseja deletar esta mesa?')) return;
        try {
            const response = await authFetch(`http://localhost:8080/mesas/${id}`, { method: 'DELETE' });
            if (response && response.ok) {
                alert('Mesa deletada com sucesso!');
                carregarMesas();
            } else {
                alert('Erro ao deletar mesa.');
            }
        } catch (e) {
            alert('Erro de conexão ao deletar mesa.');
        }
    }

    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        const nome = nomeInput.value.trim();
        const capacidade = capacidadeInput.value ? parseInt(capacidadeInput.value, 10) : undefined;
        const status = statusInput.value;
        const mesaData = {};
        if (nome) mesaData.nome = nome;
        if (capacidade) mesaData.capacidade = capacidade;
        if (status) mesaData.status = status;
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
                carregarMesas();
            } else {
                let errorMsg = response.statusText;
                try {
                    const text = await response.text();
                    if (text) {
                        const errorData = JSON.parse(text);
                        if (errorData.message) errorMsg = errorData.message;
                    }
                } catch (e) {}
                alert('Erro ao criar mesa: ' + errorMsg);
            }
        } catch (error) {
            alert('Erro de conexão com o servidor: ' + error.message);
        }
    });

    carregarMesas();
});
