
const $ = (selector) => document.querySelector(selector);

const doLogin = async () => {
    const data = {
        email: $('#email').value,
        password: $('#password').value,
    }
    const response = await fetch('api/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    console.log(response)
    const user = await response.text()
    console.log(user)

    if (user != 'FAIL') {
        localStorage.token = user
        window.location = '/users.html'
    }
    else {
        alert('Unauthorized')
    }
}