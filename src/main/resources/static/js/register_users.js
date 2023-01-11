const $ = (selector) => document.querySelector(selector);

async function createUser() {
    const data = {
        name: $('#name').value,
        lastName: $('#lastname').value,
        email: $('#email').value,
        password: $('#password').value,
        phone: $('#phone').value,
    }
    const response = await fetch('api/users', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    const user = await response.json()

    alert(`The user ${user.email} was created successfully`)
}