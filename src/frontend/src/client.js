import "unfetch/polyfill";

const JSON_HEADERS = {
    "Content-Type": "application/json"
};

const trimTrailingSlash = value => value?.replace(/\/+$/, "");

const getApiBase = () => {
    const configuredBase = trimTrailingSlash(import.meta.env.VITE_API_BASE);

    if (configuredBase) {
        return configuredBase;
    }

    if (typeof window === "undefined") {
        return "";
    }

    const {protocol, hostname, port} = window.location;
    const isLocalhost = hostname === "localhost" || hostname === "127.0.0.1";

    if (isLocalhost && port && port !== "8080") {
        return `${protocol}//${hostname}:8080`;
    }

    return "";
};

const API_BASE = getApiBase();

const hasJsonBody = response => response.headers.get("content-type")?.includes("application/json");

const parseResponse = async response => {
    if (!response.ok) {
        const payload = hasJsonBody(response) ? await response.json() : null;
        const error = new Error(payload?.message || response.statusText);
        error.response = response;
        error.payload = payload;
        throw error;
    }

    if (!hasJsonBody(response)) {
        const bodyText = await response.text();

        if (!bodyText.trim()) {
            return null;
        }

        const error = new Error(`Expected JSON response from ${response.url}`);
        error.payload = {message: "The frontend did not receive JSON from the backend API."};
        throw error;
    }

    if (response.status === 204) {
        return null;
    }

    return response.json();
};

const toApiUrl = url => `${API_BASE}${url}`;

const request = (url, options) => fetch(toApiUrl(url), options).then(parseResponse);

export const getAllBuildings = () => request("/buildings");

export const getBuildingFloorMaps = buildingNumber =>
    request(`/buildings/${buildingNumber}/maps`);

export const addNewBuilding = building =>
    request("/buildings", {
        method: "POST",
        headers: JSON_HEADERS,
        body: JSON.stringify(building)
    });

export const updateBuilding = (buildingNumber, building) =>
    request(`/buildings/number/${buildingNumber}`, {
        method: "PUT",
        headers: JSON_HEADERS,
        body: JSON.stringify(building)
    });

export const deleteBuilding = buildingNumber =>
    request(`/buildings/number/${buildingNumber}`, {
        method: "DELETE"
    });

export const getAllRooms = () => request("/rooms");

export const addNewRoom = (buildingNumber, room) =>
    request(`/buildings/${buildingNumber}/rooms`, {
        method: "POST",
        headers: JSON_HEADERS,
        body: JSON.stringify(room)
    });

export const updateRoom = (buildingNumber, roomNumber, room) =>
    request(`/buildings/${buildingNumber}/rooms/${roomNumber}`, {
        method: "PUT",
        headers: JSON_HEADERS,
        body: JSON.stringify(room)
    });

export const deleteRoom = (buildingNumber, roomNumber) =>
    request(`/buildings/${buildingNumber}/rooms/${roomNumber}`, {
        method: "DELETE"
    });

export const getAllMeetings = filters => {
    const search = new URLSearchParams();

    if (filters?.start) {
        search.set("start", filters.start);
    }
    if (filters?.end) {
        search.set("end", filters.end);
    }
    if (filters?.buildingNumber) {
        search.set("building-number", filters.buildingNumber);
    }
    if (filters?.roomNumber) {
        search.set("room-number", filters.roomNumber);
    }

    const queryString = search.toString();
    return request(queryString ? `/meetings?${queryString}` : "/meetings");
};

export const addNewMeeting = meeting =>
    request("/meetings", {
        method: "POST",
        headers: JSON_HEADERS,
        body: JSON.stringify(meeting)
    });

export const updateMeeting = (meetingId, meeting) =>
    request(`/meetings/${meetingId}`, {
        method: "PUT",
        headers: JSON_HEADERS,
        body: JSON.stringify(meeting)
    });

export const deleteMeeting = meetingId =>
    request(`/meetings/${meetingId}`, {
        method: "DELETE"
    });
