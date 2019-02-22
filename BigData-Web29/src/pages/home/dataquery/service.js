import fetch from '@/config/fetch'
export const queryData = (data) => fetch('/configure/byAll', data, "GET");