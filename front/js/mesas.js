requireAuth();

function renderMesas(mesas) {
    const container = document.getElementById('mesas-list');
    container.innerHTML = '';
    if (!mesas || mesas.length === 0) {
        container.innerHTML = '<p>Nenhuma mesa encontrada.</p>';
        return;
    }
    mesas.forEach(mesa => {
        const card = document.createElement('div');
        card.className = 'mesa-card';
        card.setAttribute('data-status', mesa.status);
        card.innerHTML = `
            <span class="mesa-icon">🍽️</span>
            <h3>${mesa.nome}</h3>
            <p>Capacidade: ${mesa.capacidade} pessoas</p>
            <p>Status: ${mesa.status === 'DISPONIVEL' ? 'Disponível' : 'Ocupada'}</p>
        `;
        container.appendChild(card);
    });
}

async function carregarMesas() {
    try {
        const response = await authFetch('http://localhost:8080/mesas');
        if (!response) return;
        if (response.ok) {
            const mesas = await response.json();
            renderMesas(mesas);
        } else {
            renderMesas([]);
        }
    } catch (e) {
        renderMesas([
            { nome: 'Mesa 1', capacidade: 4, status: 'DISPONIVEL' },
            { nome: 'Mesa 2', capacidade: 4, status: 'DISPONIVEL' },
            { nome: 'Mesa 3', capacidade: 4, status: 'DISPONIVEL' },
            { nome: 'Mesa 4', capacidade: 8, status: 'DISPONIVEL' },
            { nome: 'Mesa 5', capacidade: 8, status: 'DISPONIVEL' }
        ]);
    }
}

carregarMesas(); 