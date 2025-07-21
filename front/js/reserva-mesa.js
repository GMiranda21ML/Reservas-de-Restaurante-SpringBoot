// js/reserva-mesa.js

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('reservaMesaForm');
    const mesaIdInput = document.getElementById('reserva-mesa-id');
    const dataInput = document.getElementById('reserva-data');
    const pessoasInput = document.getElementById('reserva-numero-pessoas');

    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        const mesaId = parseInt(mesaIdInput.value, 10);
        const numeroPessoas = parseInt(pessoasInput.value, 10);
        let dataReserva = dataInput.value;
        // Converter para formato ISO local (YYYY-MM-DDTHH:mm:ss)
        if (dataReserva && dataReserva.length === 16) {
            dataReserva = dataReserva + ':00';
        }
        const reservaData = { mesaId, dataReserva, numeroPessoas };
        const token = localStorage.getItem('jwtToken');
        try {
            const response = await fetch('http://localhost:8080/reservas', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    ...(token ? { 'Authorization': 'Bearer ' + token } : {})
                },
                body: JSON.stringify(reservaData)
            });
            if (response.ok) {
                alert('Reserva realizada com sucesso!');
                form.reset();
            } else {
                let errorMsg = response.statusText;
                try {
                    const text = await response.text();
                    if (text) {
                        const errorData = JSON.parse(text);
                        if (errorData.message) errorMsg = errorData.message;
                    }
                } catch (e) {}
                alert('Erro ao reservar: ' + errorMsg);
            }
        } catch (error) {
            alert('Erro de conexão com o servidor: ' + error.message);
        }
    });
}); 