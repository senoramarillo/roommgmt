import 'unfetch/polyfill';

/**
 * Checks the HTTP response status and converts non-2xx responses to errors.
 *
 * @param response the HTTP response object
 * @returns the response if status is OK (2xx)
 * @throws Error if the response status is not OK
 */
const checkStatus = response => {
    if (response.ok) {
        return response;
    }
    // Convert non-2xx HTTP responses into errors:
    const error = new Error(response.statusText);
    error.response = response;
    return Promise.reject(error);
}

/**
 * Retrieves all buildings from the backend API.
 *
 * @returns Promise<Response> the HTTP response containing all buildings
 */
export const getAllBuildings = () =>
    fetch("/buildings")
        .then(checkStatus);

/**
 * Creates a new building in the backend API.
 *
 * @param building the building object to create
 * @returns Promise<Response> the HTTP response containing the created building
 */
export const addNewBuilding = building =>
    fetch("/buildings", {
            headers: {
                'Content-Type': 'application/json'
            },
            method: 'POST',
            body: JSON.stringify(building)
        }
    ).then(checkStatus)

/**
 * Deletes a building by its ID from the backend API.
 *
 * @param buildingId the ID of the building to delete
 * @returns Promise<Response> the HTTP response
 */
export const deleteBuilding = buildingId =>
    fetch(`/buildings/${buildingId}`, {
        method: 'DELETE'
    }).then(checkStatus);