const brandsURL = 'http://localhost:8080/api/v1/brands';

let brands = [];

function preFetch(url, fetchData, callback, awaitBody = true) {
    fetchData.headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    };
    fetchData.body = JSON.stringify(fetchData.body);
    fetch(url, fetchData)
        .then(res => {
            if (awaitBody)
                return res.json()
            else
                callback();
        })
        .then(
            (response) => {
                callback(response);
            },
            (error) => {
                //catch err
            }
        )
}

function nullsToConstant(brand) {
    brand.description = brand.description ?? '';
}

function renderFuelList() {
    var $list = $('#brand-list');
    $list.empty();
    brands.forEach(brand => {
        nullsToConstant(brand);
        const $template = getTemplate(brand);
        $list.append($template);
    })
}

function refreshData() {
    preFetch(
        brandsURL + '/all',
        {'method': 'GET'},
        (result) => {
            brands = result;
            renderFuelList();
        })
}

function handleCreate(item) {
    preFetch(
        brandsURL,
        {
            'method': 'POST',
            'body': item
        },
        () => {
            refreshData();
        })
}

function handleEdit(item) {
    preFetch(
        brandsURL,
        {
            'method': 'PUT',
            'body': item
        },
        () => {
            refreshData();
        })
}

function handleDelete(id) {
    preFetch(
        brandsURL + `/${id}`,
        {'method': 'DELETE'},
        () => {
            refreshData();
        },
        false)
}

function getTemplate(item) {
    return `<tr>
                        <td class="brand-id" scope="row">${item.id}</th>
                        <td class="brand-name">${item.name}</td>
                        <td class="brand-description">${item.description}</td>
                        <td>
                            <button type="button" 
                                class="btn btn-primary" 
                                data-toggle="modal" 
                                data-target="#editModal" 
                                data-id=${item.id}
                                data-name=${item.name}
                                data-description=${item.description}>Edit</button>
                            <button type="button" onClick="handleDelete(value)" value=${item.id} class="btn btn-danger">Delete</button>
                        </td>
                        </tr>`;
}

function editModal() {
    $('#editModal').on('show.bs.modal', function (e) {
        let btn = $(e.relatedTarget);
        let id = btn.data('id');
        let name = btn.data('name');
        let description = btn.data('description');
        $('.editNameInput').val(name);
        $('.editDescriptionInput').val(description);

        $('.edit').data('id', id);
    })

    $('.edit').on('click', function () {
        let id = $(this).data('id');

        let name = $('.editNameInput').val();
        let description = $('.editDescriptionInput').val();

        handleEdit({id, name, description});

        $('#editModal').modal('toggle');
    })
}

function createModal() {
    $('#createModal').on('show.bs.modal', function (e) {
        $('.createNameInput').val('');
        $('.createDescriptionInput').val('');
    })
    $('.create').on('click', function () {
        let name = $('.createNameInput').val();
        let description = $('.createDescriptionInput').val();

        handleCreate({name, description});
        $('#createModal').modal('toggle');
    })
}

refreshData();
setTimeout(editModal, 1);
setTimeout(createModal, 1);
