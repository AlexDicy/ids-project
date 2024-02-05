const BASE_URL = 'https://ids-project-backend.dicy.dev:8443';

export const get = async (path: string) => {
    const response = await fetch(`${BASE_URL}${path}`);
    await throwIfNotOk(response);
    return parseResponse(response);
};

export const post = async (path: string, body: any) => {
    const response = await fetch(`${BASE_URL}${path}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
    });
    await throwIfNotOk(response);
    return parseResponse(response);
};

export const put = async (path: string, body: any) => {
    const response = await fetch(`${BASE_URL}${path}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
    });
    await throwIfNotOk(response);
    return parseResponse(response);
};

export const del = async (path: string) => {
    const response = await fetch(`${BASE_URL}${path}`, {
        method: 'DELETE',
    });
    await throwIfNotOk(response);
    return parseResponse(response);
};

async function throwIfNotOk(response: Response) {
    if (!response.ok) {
        throw response;
    }
}

export function parseResponse(response: Response): Promise<any> | Promise<string> {
    if (response.headers.get('content-type') !== 'application/json') {
        return response.text();
    }
    return response.json();
}

export default {
    get,
    post,
    put,
    del
};
