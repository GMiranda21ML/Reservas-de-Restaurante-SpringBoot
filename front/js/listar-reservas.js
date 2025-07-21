// js/listar-reservas.js

document.addEventListener('DOMContentLoaded', function () {
    const reservasList = document.getElementById('reservas-list');

    async function carregarReservas() {
        reservasList.innerHTML = '<p>Carregando reservas...</p>';
        const token = localStorage.getItem('jwtToken');
        try {
            const response = await fetch('http://localhost:8080/reservas', {
                headers: token ? { 'Authorization': 'Bearer ' + token } : {}
            });
            if (response.ok) {
                const reservas = await response.json();
                renderReservas(reservas);
            } else {
                reservasList.innerHTML = '<p>Erro ao carregar reservas.</p>';
            }
        } catch (e) {
            reservasList.innerHTML = '<p>Erro de conexão ao carregar reservas.</p>';
        }
    }

    function renderReservas(reservas) {
        reservasList.innerHTML = '';
        if (!reservas || reservas.length === 0) {
            reservasList.innerHTML = '<p>Nenhuma reserva encontrada.</p>';
            return;
        }
        reservas.forEach(reserva => {
            const card = document.createElement('div');
            card.className = 'reserva-card';
            card.innerHTML = `
                <h3>Reserva na ${reserva.mesa.nome}</h3>
                <p><strong>Mesa:</strong> ${reserva.mesa.nome}</p>
                <p><strong>Data:</strong> ${formatarData(reserva.dataReserva)}</p>
                <p><strong>Status:</strong> <span class="reserva-status ${reserva.statusReserva === 'ATIVO' ? 'ativo' : 'cancelado'}">${reserva.statusReserva}</span></p>
                ${reserva.statusReserva === 'ATIVO' ? `<button class="btn-cancelar" data-id="${reserva.id}">Cancelar Reserva</button>` : ''}
            `;
            reservasList.appendChild(card);
        });
        document.querySelectorAll('.btn-cancelar').forEach(btn => {
            btn.addEventListener('click', () => cancelarReserva(btn.dataset.id));
        });
    }

    function formatarData(dataIso) {
        if (!dataIso) return '';
        const d = new Date(dataIso);
        return d.toLocaleString('pt-BR', { dateStyle: 'short', timeStyle: 'short' });
    }

    async function cancelarReserva(id) {
        if (!confirm('Tem certeza que deseja cancelar esta reserva?')) return;
        const token = localStorage.getItem('jwtToken');
        try {
            const response = await fetch(`http://localhost:8080/reservas/${id}/cancelar`, {
                method: 'PUT',
                headers: {
                    'Authorization': 'Bearer ' + token
                }
            });
            if (response.ok) {
                alert('Reserva cancelada com sucesso!');
                carregarReservas();
            } else {
                let errorMsg = response.statusText;
                try {
                    const text = await response.text();
                    if (text) {
                        const errorData = JSON.parse(text);
                        if (errorData.message) errorMsg = errorData.message;
                    }
                } catch (e) {}
                alert('Erro ao cancelar reserva: ' + errorMsg);
            }
        } catch (e) {
            alert('Erro de conexão ao cancelar reserva.');
        }
    }

    carregarReservas();
}); 