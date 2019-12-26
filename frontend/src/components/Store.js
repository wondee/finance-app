const store = { }

export function setValue(key, value) { store[key] = value; }

export const getValue = (key) => store[key]; 