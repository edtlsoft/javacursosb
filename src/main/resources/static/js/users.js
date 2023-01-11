// Call the dataTables jQuery plugin
$(document).ready(function() {
    loadUsers();
    $('#users').DataTable();
});

const headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Authorization': localStorage.token
}

async function loadUsers() {
    const response = await fetch('api/users', {
        method: 'GET',
        headers
    })
    const users = await response.json()

    if (! Array.isArray(users)) {
        alert('Error')
        return
    }

    let usersTable = '';

    users.forEach(user => {
        usersTable += `
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>
                    <a onclick="deleteUser(${user.id})" href="#" class="btn btn-danger btn-circle btn-sm">
                        <i class="fas fa-trash"></i>
                    </a>
                </td>
            </tr>
        `
    })

    console.log({
        usersTable,
        users,
    })

    document.querySelector('#users tbody').outerHTML = usersTable
}

async function deleteUser(id) {
    if (! confirm(`Desea eliminar el usuario ${id}?`)) {
        return;
    }

    const response = await fetch(`api/users/${id}`, {
        method: 'DELETE',
        headers,
    })
    loadUsers();
    alert(`El usuario ${id} se elimino correctamente.`);
}
