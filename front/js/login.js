document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('loginForm');
    if (!form) return;

    form.addEventListener('submit', async function (e) {
        e.preventDefault();

        const email = document.getElementById('login-email').value.trim();
        const senha = document.getElementById('login-password').value;

        const payload = {
            email,
            senha
        };

        try {
            const response = await fetch('http://localhost:8080/usuarios/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                const data = await response.json();
                if (data.token) {
                    localStorage.setItem('jwtToken', data.token);
                    window.location.href = 'index.html';
                } else {
                    alert('Token não recebido.');
                }
            } else {
                alert('E-mail ou senha inválidos.');
            }
        } catch (err) {
            alert('Erro de conexão com o servidor.');
        }
    });
});

function getJwtToken() {
    return localStorage.getItem('jwtToken');
}

function isTokenExpired(token) {
    if (!token) return true;
    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        if (!payload.exp) return true;
        const now = Math.floor(Date.now() / 1000);
        return payload.exp < now;
    } catch (e) {
        return true;
    }
}

function requireAuth() {
    const token = getJwtToken();
    if (!token || isTokenExpired(token)) {
        localStorage.removeItem('jwtToken');
        window.location.href = 'login.html';
    }
}

async function authFetch(url, options = {}) {
    const token = getJwtToken();
    if (!options.headers) options.headers = {};
    if (token) {
        options.headers['Authorization'] = 'Bearer ' + token;
    }
    const response = await fetch(url, options);
    if (response.status === 401 || response.status === 403) {
        localStorage.removeItem('jwtToken');
        window.location.href = 'login.html';
        return null;
    }
    return response;
} 