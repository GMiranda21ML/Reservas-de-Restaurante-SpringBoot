// Proteger a página
requireAuth();

// Função para renderizar mesas
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
        card.innerHTML = `
            <h3>${mesa.nome}</h3>
            <p>Capacidade: ${mesa.capacidade} pessoas</p>
            <p>Status: ${mesa.status === 'DISPONIVEL' ? 'Disponível' : 'Ocupada'}</p>
        `;
        container.appendChild(card);
    });
}

// Buscar mesas da API (ajuste a URL conforme sua API)
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
        // Se a API não estiver pronta, usar mock
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