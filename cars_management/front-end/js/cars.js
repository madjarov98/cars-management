const brandsURL = 'http://localhost:8080/api/v1/brands';
const fuelsURL = 'http://localhost:8080/api/v1/fuels';
const carsURL = 'http://localhost:8080/api/v1/cars';

let brands = [];
let fuels = [];
let cars = [];
let filters = []

let stateCount = 0;
const isStateLoaded = () => stateCount > 2;


function preFetch(url, fetchData, callback, awaitBody = true) {
    fetchData.headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    };
    fetchData.body = JSON.stringify(fetchData.body);
    fetch(url, fetchData)
        .then(res => {
            if (awaitBody) {
                return res.json()
            } else {
                callback();
            }
        })
        .then(
            (response) => {
                callback(response);
            },
            (error) => {
                console.log(error);
            }
        )
}

function renderCarList() {
    var $list = $('#car-list');
    $list.empty();

    if (!cars.length)
        return;

    cars.forEach(car => {
        const $template = getTemplate(car);
        $list.append($template);
    })
}

function refreshData() {
    preFetch(
        carsURL + '/all',
        {
            'method': 'POST',
            'body': filters
        },
        (result) => {
            cars = result;

            if (!isStateLoaded()) {
                stateCount++;
                createModal();
                editModal();
            }

            renderCarList();
        });

    preFetch(
        brandsURL + '/all',
        {
            'method': 'GET'
        },
        (result) => {
            brands = result;

            if (!isStateLoaded()) {
                stateCount++;
                brandSelect();
            }
        });
    preFetch(
        fuelsURL + '/all',
        {
            'method': 'GET'
        },
        (result) => {
            fuels = result;

            if (!isStateLoaded()) {
                stateCount++;
                fuelSelect();
            }
        });
}

function getFilterTemplate(name, filter) {
    return {
        'name': name,
        'values': filter
    };
}

function getSplitFilter(val) {
    if (val.indexOf(',') >= 0) {
        return val.split(',');
    }

    return [val];
}

function handleFilter() {
    let fuelId = $('#filterFuelSelect').val();
    let brandId = $('#filterBrandSelect').val();
    let modelName = $('#filterModelInput').val();

    filters = [];

    if (fuelId)
        filters.push(getFilterTemplate('fuel', [fuelId]))
    if (brandId)
        filters.push(getFilterTemplate('brand', [brandId]))
    if (modelName)
        filters.push(getFilterTemplate('model', getSplitFilter(modelName)));

    refreshData();
}

function handleCreate(item) {
    item.brand = brands.find(brand => brand.id === parseInt(item.brand));
    item.fuel = fuels.find(fuel => fuel.id === parseInt(item.fuel));

    preFetch(
        carsURL,
        {
            'method': 'POST',
            'body': item
        },
        () => {
            refreshData();
        })
}

function handleEdit(item) {
    item.brand = brands.find(brand => brand.id === parseInt(item.brand));
    item.fuel = fuels.find(fuel => fuel.id === parseInt(item.fuel));

    preFetch(
        carsURL,
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
        carsURL + `/${id}`,
        {
            'method': 'DELETE'
        },
        () => {
            refreshData();
        },
        false)
}

function constant(val) {
    return val ?? '';
}

function getTemplate(item) {
    return `<tr>
                        <td class="car-id" scope="row">${item.id}</th>
                        <td class="car-brand">${item.brand.name}</td>
                        <td class="car-fuel">${item.fuel.name}</td>
                        <td class="car-model">${item.model}</td>
                        <td class="car-releaseyear">${constant(item.releaseYear)}</td>
                        <td class="car-horsepower">${constant(item.horsePower)}</td>
                        <td>
                            <button type="button" 
                                class="btn btn-primary" 
                                data-toggle="modal" 
                                data-target="#editModal" 
                                data-id=${item.id}
                                data-brand=${item.brand.id}
                                data-fuel=${item.fuel.id}
                                data-model=${item.model}
                                data-releaseyear=${item.releaseYear}
                                data-horsepower=${item.horsePower}>Edit</button>
                            <button type="button" onClick="handleDelete(value)" value=${item.id} class="btn btn-danger">Delete</button>
                        </td>
                        </tr>`;
}

function editModal() {
    $('#editModal').on('show.bs.modal', function (e) {
        let btn = $(e.relatedTarget);
        let id = btn.data('id');
        let brand = btn.data('brand');
        let fuel = btn.data('fuel');
        let model = btn.data('model');
        let releaseYear = btn.data('releaseyear');
        let horsePower = btn.data('horsepower');

        $('#editBrandSelect').val(brand);
        $('#editFuelSelect').val(fuel);
        $('.editModelInput').val(model);
        $('.editReleaseYearInput').val(releaseYear);
        $('.editHorsePowerInput').val(horsePower);

        $('.edit').data('id', id);
    })

    $('.edit').on('click', function () {
        let id = $(this).data('id');

        let brand = $('#editBrandSelect').val();
        let fuel = $('#editFuelSelect').val();
        let model = $('.editModelInput').val();
        let releaseYear = $('.editReleaseYearInput').val();
        let horsePower = $('.editHorsePowerInput').val();

        handleEdit({id, brand, fuel, model, releaseYear, horsePower});

        $('#editModal').modal('toggle');
    })
}

function createModal() {
    $('#createModal').on('show.bs.modal', function (e) {
        $('#createBrandSelect').val('');
        $('#createFuelSelect').val('');
        $('.createModelInput').val('');
        $('.createReleaseYearInput').val('');
        $('.createHorsePowerInput').val('');
    })
    $('.create').on('click', function () {
        let brand = $('#createBrandSelect').val();
        let fuel = $('#createFuelSelect').val();
        let model = $('.createModelInput').val();
        let releaseYear = $('.createReleaseYearInput').val();
        let horsePower = $('.createHorsePowerInput').val();

        handleCreate({brand, fuel, model, releaseYear, horsePower});
        $('#createModal').modal('toggle');
    })
}

function brandSelect() {
    var select = $('.select-brand');
    select.empty();
    select.append($('<option>', {
        value: null,
        text: ' '
    }))
    brands.forEach(brand => {
        select.append($('<option>', {
            value: brand.id,
            text: brand.name
        }));
    });
}

function fuelSelect() {
    var select = $('.select-fuel');
    select.empty();
    select.append($('<option>', {
        value: null,
        text: ' '
    }))
    fuels.forEach(fuel => {
        select.append($('<option>', {
            value: fuel.id,
            text: fuel.name
        }));
    });
}

refreshData();