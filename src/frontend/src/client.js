import 'unfetch/polyfill';

const checkStatus = response => {
    if (response.ok) {
        return response;
    }
    // convert non-2xx HTTP responses into errors:
    const error = new Error(response.statusText);
    error.response = response;
    return Promise.reject(error);
}

export const getAllBuildings = () =>
    fetch("/buildings")
        .then(checkStatus);

export const addNewBuilding = building =>
    fetch("/buildings", {
            headers: {
                'Content-Type': 'application/json'
            },
            method: 'POST',
            body: JSON.stringify(building)
        }
    ).then(checkStatus)

export const deleteBuilding = buildingId =>
    fetch(`buildings/${buildingId}`, {
        method: 'DELETE'
    }).then(checkStatus);