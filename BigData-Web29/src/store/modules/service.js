import fetch from '@/config/fetch'
export const login = (data) => fetch('/login/userLogin', data, "POST", true);
