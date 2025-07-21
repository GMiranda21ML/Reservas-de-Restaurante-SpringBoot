// js/editar-mesa.js

document.addEventListener('DOMContentLoaded', function () {
    const buscarForm = document.getElementById('buscarMesaForm');
    const editarForm = document.getElementById('editarMesaForm');
    const buscarIdInput = document.getElementById('buscar-id');
    const nomeInput = document.getElementById('edit-nome');
    const capacidadeInput = document.getElementById('edit-capacidade');
    const statusInput = document.getElementById('edit-status');
    const mesasList = document.getElementById('mesas-list');
    let mesaId = null;
    let idBadge = null;

    function showIdBadge(id) {
        if (!idBadge) {
            idBadge = document.createElement('div');
            idBadge.id = 'mesa-id-badge';
            idBadge.style.fontSize = '1.2em';
            idBadge.style.fontWeight = 'bold';
            idBadge.style.color = '#1761a0';
            idBadge.style.background = '#f0f6ff';
            idBadge.style.padding = '8px 18px';
            idBadge.style.borderRadius = '8px';
            idBadge.style.display = 'inline-block';
            idBadge.style.marginBottom = '18px';
            idBadge.style.marginTop = '2px';
            idBadge.style.letterSpacing = '1px';
            idBadge.style.boxShadow = '0 2px 8px rgba(23,97,160,0.07)';
            const form = document.getElementById('editarMesaForm');
            form.insertBefore(idBadge, form.firstChild);
        }
        idBadge.textContent = `ID da Mesa: ${id}`;
        idBadge.style.display = '';
    }
    function hideIdBadge() {
        if (idBadge) idBadge.style.display = 'none';
    }

    buscarForm.addEventListener('submit', async function (e) {
        e.preventDefault();
        mesaId = buscarIdInput.value;
        if (!mesaId) return;
        const token = localStorage.getItem('jwtToken');
        if (!token) {
            alert('Usuário não autenticado.');
            window.location.href = 'login.html';
            return;
        }
        try {
            const response = await fetch(`http://localhost:8080/mesas/${mesaId}`, {
                headers: {
                    'Authorization': 'Bearer ' + token
                }
            });
            if (response.ok) {
                const mesa = await response.json();
                nomeInput.value = mesa.nome || '';
                capacidadeInput.value = mesa.capacidade || '';
                statusInput.value = mesa.status || '';
                editarForm.style.display = '';
                showIdBadge(mesa.id);
            } else {
                editarForm.style.display = 'none';
                hideIdBadge();
                alert('Mesa não encontrada.');
            }
        } catch (e) {
            editarForm.style.display = 'none';
            alert('Erro ao buscar mesa.');
        }
    });

    editarForm.addEventListener('submit', async function (e) {
        e.preventDefault();
        if (!mesaId) return;
        const token = localStorage.getItem('jwtToken');
        if (!token) {
            alert('Usuário não autenticado.');
            window.location.href = 'login.html';
            return;
        }
        const mesaData = {};
        if (nomeInput.value.trim()) mesaData.nome = nomeInput.value.trim();
        if (capacidadeInput.value) mesaData.capacidade = parseInt(capacidadeInput.value, 10);
        if (statusInput.value) mesaData.status = statusInput.value;
        try {
            const response = await fetch(`http://localhost:8080/mesas/${mesaId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                },
                body: JSON.stringify(mesaData)
            });
            if (response.ok) {
                alert('Mesa atualizada com sucesso!');
            } else {
                let errorMsg = response.statusText;
                try {
                    const text = await response.text();
                    if (text) {
                        const errorData = JSON.parse(text);
                        if (errorData.message) errorMsg = errorData.message;
                    }
                } catch (e) {}
                alert('Erro ao atualizar mesa: ' + errorMsg);
            }
        } catch (e) {
            alert('Erro de conexão ao atualizar mesa.');
        }
    });
}); 