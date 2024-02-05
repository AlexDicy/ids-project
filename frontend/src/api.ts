const BASE_URL = 'https://ids-project-backend.dicy.dev:8443';

export const get = async (path: string) => {
    const response = await fetch(`${BASE_URL}${path}`);
    return response.json();
};

export const post = async (path: string, body: any) => {
    const response = await fetch(`${BASE_URL}${path}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
    });
    return response.json();
};

export const put = async (path: string, body: any) => {
    const response = await fetch(`${BASE_URL}${path}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
    });
    return response.json();
};

export const del = async (path: string) => {
    const response = await fetch(`${BASE_URL}${path}`, {
        method: 'DELETE',
    });
    return response.json();
};

export default {
    get,
    post,
    put,
    del
};
