document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('registerForm');
    if (!form) return;

    form.addEventListener('submit', async function (e) {
        e.preventDefault();

        const nome = document.getElementById('register-name').value.trim();
        const email = document.getElementById('register-email').value.trim();
        const senha = document.getElementById('register-password').value;
        const isAdmin = document.getElementById('register-admin').checked;
        const role = isAdmin ? 'ROLE_ADMIN' : 'ROLE_USER';

        const payload = {
            nome,
            email,
            senha,
            role
        };

        try {
            const response = await fetch('http://localhost:8080/usuarios/registrar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                alert('Cadastro realizado com sucesso!');
                window.location.href = 'login.html';
            } else {
                const error = await response.json();
                alert('Erro ao cadastrar: ' + (error.message || response.status));
            }
        } catch (err) {
            alert('Erro de conexão com o servidor.');
        }
    });
}); 